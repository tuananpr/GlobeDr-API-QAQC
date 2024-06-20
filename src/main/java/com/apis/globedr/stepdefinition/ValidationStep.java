package com.apis.globedr.stepdefinition;

import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rest.core.debug.CucumberReport;
import com.testautomationguru.utility.PDFUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;

public class ValidationStep extends Data {


    @Then("^The status code should be '(.*?)'$")
    public void verifyResponseStatusCode(String expectedCode) throws Exception {
        if (response.statusCode() != Integer.parseInt(expectedCode)) {
            Assert.fail(String.format("\nStatus code %d\n%s", response.getStatusCode(), response.getBody().asString()));
        } else {
            Assert.assertEquals(response.statusCode(), Integer.parseInt(expectedCode));
        }
    }

    @And("^The request should be (succeed|fail)$")
    public void verifyRequestSucceed(String status) {
        if (response != null) {
            Boolean value = response.extract("success");
            if (status.equals("succeed")) {
                Assert.assertTrue(value);
            } else {
                Assert.assertFalse(value);
            }
        } else {
            Assert.fail("response is null");
        }

    }


    private Map<String, Object> getMap(Map<String, Object> dataTable) {

        if (dataTable != null) {
            Map<String, Object> data = new HashMap<>();
            for (Map.Entry<String, Object> field : dataTable.entrySet()) {

                String key = Common.strToNull(field.getKey());
                Object value = Common.strToNull(field.getValue());

                if (key != null) {

                    // convert boolean to String
                    if ("true".equals(value) || "false".equals(value)) {
                        value = Boolean.valueOf(value.toString());
                    }

                    // convert String variable to variable into Data
                    Matcher m = Common.matcherVariable(value);
                    if (m != null && m.find()) {
                        value = Common.getVariableValue(Data.class, m.group(1));
                    }

                    // convert String time to time
                    if (Common.strTimes.contains(key.toLowerCase()) && !field.getValue().toString().equalsIgnoreCase("null")) {
                        value = Common.convertStrDateToDate(value);
                    }


                    data.put(key, value);
                }

            }
            return data;
        }
        return null;
    }


    @Then("^The response should be$")
    public void responseShouldBe(Map<String, Object> expectedTable) {
        for (Map.Entry<String, Object> field : getMap(expectedTable).entrySet()) {
            Object expected = null;
            Object actual = null;
            try {
                if (field.getKey() != null) {
                    if (!field.getKey().isEmpty()) {

                        Assert.assertTrue(response.isHasPath(field.getKey()),
                                String.format("Try to get value into path '%s' but not found into \nReponse:\n%s", field.getKey(), response.asString()));

                        expected = field.getValue();
                        actual = response.extract(field.getKey());

                        // compare for Numeric
                        if (expected != null && Common.isNumeric(expected.toString())) {
                            actual = Common.formatNumber(Double.parseDouble(actual.toString()));
                            expected = Common.formatNumber(Double.parseDouble(expected.toString()));
                        }

                        Assert.assertEquals(String.valueOf(actual).trim().toLowerCase(), String.valueOf(expected).trim().toLowerCase(),
                                String.format("Expected of key '%s' is '%s', but found '%s'\nReponse:\n%s", field.getKey(), expected, actual, response.asString()));
                    }
                }
            } catch (Exception e) {
                Assert.fail(String.format("Error while verify response with key '%s' ", field.getKey()));
            }
        }

    }

    @Then("^The response should contains$")
    public void responseShouldContains(Map<String, Object> expectedTable) {
        for (Map.Entry<String, Object> field : getMap(expectedTable).entrySet()) {
            if (!field.getKey().isEmpty()) {
                Object expected = field.getValue();

                Assert.assertTrue(response.isHasPath(field.getKey()),
                        String.format("Try to get value into path '%s' but not found into \nReponse:\n%s", field.getKey(), response.asString()));
                Object actual = response.extract(field.getKey());

                // compare for Numeric
                if (expected != null && Common.isNumeric(expected.toString())) {
                    actual = Common.formatNumber(Double.parseDouble(actual.toString()));
                    expected = Common.formatNumber(Double.parseDouble(expected.toString()));
                }

                Assert.assertTrue(String.valueOf(actual).toLowerCase().contains(String.valueOf(expected).toLowerCase()),
                        String.format("Expected of key '%s' should contains '%s', but found '%s'\nReponse:\n%s", field.getKey(), expected, actual, response.asString()));
            }
        }
    }


    @And("^The \"([^\"]*)\" should \"([^\"]*)\"$")
    public void verifyKeyAndValue(String key, String expectedValue) {

        switch (expectedValue.toLowerCase()) {
            case "":
            case "null":
                Assert.assertNull(response.extract(key), String.format("value into path '%s' must be null", key));
                break;
            case "not null":
                Assert.assertNotNull(response.extract(key), String.format("value into path '%s' must be not null", key));
                break;
            case "not empty":
                Assert.assertFalse(response.extract(key).toString().isEmpty(), String.format("value into path '%s' must be not empty", key));
                break;
            case "not empty array":
                Assert.assertTrue(response.extractAsList(key).size() > 0, String.format("Array into path '%s' must be not empty array", key));
                break;
            case "empty array":
                Assert.assertTrue(response.extractAsList(key).size() == 0, String.format("Array into path '%s' must be empty", key));
                break;
            default:
                Assert.assertEquals(response.extract(key).toString(), expectedValue);
        }
    }


    static public void assertEmpty(Object object, String message) {

        if (object instanceof List<?>) {
            org.testng.Assert.assertTrue(((List<Object>) object).isEmpty(), message);
        } else {
            org.testng.Assert.assertTrue(((String) object).isEmpty(), message);
        }

    }

    public void verifyImage(String actualImagePath, String expectedImagePath) {
        boolean isDowloaded;
        int tryTimes = 3;
        String temp = Path.TARGET + UUID.randomUUID() + ".jpg";
        do {
            isDowloaded = FileHelper.saveFileFromURL(actualImagePath, temp);
            tryTimes--;
        } while (!isDowloaded && tryTimes > 0);

        boolean isSame = Common.compareImage(new File(temp), FileHelper.getFileFromResource(expectedImagePath));
        if(!isSame){
            CucumberReport.attach(new File(temp).getPath(), "actual image");
            CucumberReport.attach(FileHelper.getFileFromResource(expectedImagePath).getPath(), "expected image");
        }
        Assert.assertTrue(isSame, String.format("Actual file '%s' not match with expected file '%s'", temp, expectedImagePath));


    }

    @And("The document into consult should be match with {string}")
    public void theDocumentIntoConsultShouldBeMatchWith(String expectImagePath) {
        String doc = response.extractAsList("data.list[*].msgExtension.value").get(0);
        Pattern pattern = Pattern.compile("\"Url\":\"(.*?)\"");
        Matcher matcher = pattern.matcher(doc);
        if (matcher.find()) {
            verifyImage(matcher.group(1), expectImagePath);
        } else {
            Assert.fail("Not found document into consult");
        }
    }

    @And("^The image into \"([^\"]*)\" should be match \"([^\"]*)\"$")
    public void imageShoudBeMatch(String key, String expectedImagePath) {
        if (expectedImagePath.equalsIgnoreCase("[]")) {
            // expected image is empty array
            assertEmpty(response.extract(key), null);
        } else {
            verifyImage(response.extract(key), expectedImagePath);
        }
    }

    @And("^The pdf into \"([^\"]*)\" should be match \"([^\"]*)\"$")
    public void verifyPDF(String key, String expectedImagePath) {
        String temp = Path.TARGET + UUID.randomUUID() + ".pdf";
        PDFUtil pdfUtil = new PDFUtil();
        boolean isMatch = false;
        String actualPdf = response.extract(key);
        Assert.assertTrue(FileHelper.saveFileFromURL(actualPdf, temp),
                String.format("Cant save Actual file '%s' to '%s' on local", actualPdf, temp));
        expectedImagePath = FileHelper.getFileFromResource(expectedImagePath).toString();

        try {
            isMatch = pdfUtil.compare(temp, expectedImagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(isMatch,
                String.format("Actual file '%s' not match with expected file '%s'", temp, expectedImagePath));
    }

    @And("^The \"([^\"]*)\" greater \"([^\"]*)\"$")
    public void numberGreater(String key, String expectedValue) {
        Object actualValue = response.extract(key);
        Assert.assertTrue(Integer.parseInt(actualValue.toString()) > Integer.parseInt(expectedValue.toUpperCase()));
    }

    @And("^The \"([^\"]*)\" greater than \"([^\"]*)\"$")
    public void numberGreaterThan(String key, String expectedValue) {
        Object actualValue = response.extract(key);
        Assert.assertTrue(Integer.parseInt(actualValue.toString()) >= Integer.parseInt(expectedValue.toUpperCase()));
    }

    @And("^The \"([^\"]*)\" equal \"([^\"]*)\"$")
    public void numberEqual(String key, String expectedValue) {
        Object actualValue = response.extract(key);
        Assert.assertEquals(Integer.parseInt(actualValue.toString()), Integer.parseInt(expectedValue.toUpperCase()));
    }

    @And("^The \"([^\"]*)\" less \"([^\"]*)\"$")
    public void numberLess(String key, String expectedValue) {
        Object actualValue = response.extract(key);
        Assert.assertTrue(Integer.parseInt(actualValue.toString()) < Integer.parseInt(expectedValue.toUpperCase()));
    }


    @And("^The request should be \"([^\"]*)\"$")
    public void theRequestShouldBe(String status) {
        Boolean value = response.extract("success");
        if (status.equals("succeed")) {
            Assert.assertTrue(value, ">>>>> Request is failed! >>>>>");
        } else {
            Assert.assertFalse(value, ">>>>> Expect request to be failed but it is not! >>>>>");
        }
    }


    @And("^List \"([^\"]*)\" of response should be not contains \"([^\"]*)\"$")
    public void listNotContains(String key, String value) {
        List<String> list = response.extract(key);
        Assert.assertFalse(list.contains(value), String.format("List %s should be not contains value '%s'", list, value));
    }

    @And("^List \"([^\"]*)\" of response should be contains \"([^\"]*)\"$")
    public void listContains(String key, String value) {
        List<String> list = response.extract(key);
        Assert.assertTrue(list.contains(value), String.format("List %s should be contains value '%s'", list, value));
    }


    @Then("List {string} of response should be contains all member into {string}")
    public void listOfResponseShouldBeContainsMember(String key, String members) {
        List<String> list = response.extract(key);
        if (members.equalsIgnoreCase("[]")) {
            Assert.assertTrue(list.isEmpty(), String.format("List %s must be empty", list));
        } else {
            Collection expectedList = new ArrayList(Arrays.asList(members.split(",")));
            expectedList.removeAll(list);
            Assert.assertTrue(list.containsAll(expectedList), String.format("Not found %s into List %s", expectedList, list));
        }
    }

    @Then("List {string} of response should be number")
    public void listContainsNumber(String key, List<Integer> list) {
        List<Integer> actual = response.extract(key);
        List<Integer> expectedList = new ArrayList<>(list);
        expectedList.removeAll(actual);
        Assert.assertEquals(expectedList.size(), 0, String.format("Not found %s into list %s", expectedList, actual));
        Assert.assertEquals(actual.size(), 0, String.format("Has new member %s into list from response", actual, actual));
    }


    @Then("List {string} of response should contains")
    public void listContains(String key, List<String> list) {
        List<String> actual = response.extract(key);
        List<String> expectedList = new ArrayList<>(list);
        expectedList.removeIf(i -> actual.stream().anyMatch(s -> s.contains(i)));
        Assert.assertTrue(expectedList.size() == 0, String.format("Not found %s into list %s", expectedList, actual));
    }

    @And("List {string} of response should not contains")
    public void listNotContains(String key, List<String> list) {
        List<String> actual = response.extract(key);
        List<String> expectedList = new ArrayList<>(list);
        expectedList.removeIf(i -> actual.stream().anyMatch(s -> s.contains(i)));
        Assert.assertTrue(expectedList.size() != 0, String.format("Still found %s into list %s", expectedList, actual));
    }


    @Then("List {string} of response should be")
    public void listHas(String key, List<String> list) {
        List<String> actual = response.extract(key);
        List<String> expectedList = new ArrayList<>(list);
        expectedList.removeAll(actual);
        actual.removeAll(list);
        Assert.assertTrue(expectedList.size() == 0, String.format("Not found %s into list %s", expectedList, response.extract(key)));
        Assert.assertEquals(actual.size(), 0, String.format("Has new member %s into list from response", actual));
    }

    @Then("List {string} of response should be {string}")
    public void listHasExpectedList(String key, String members) {
        List<String> expectedList = Arrays.asList(members.split(","));
        if (members.equalsIgnoreCase("[]")) {
            List<String> actual = response.extract(key);
            Assert.assertTrue(actual.isEmpty(), String.format("List %s must be empty", actual));
        } else {
            listHas(key, expectedList);
        }

    }

    @And("List {string} of response should not has")
    public void listNotHas(String key, List<String> list) {
        List<String> actual = response.extract(key);
        List<String> expectedList = new ArrayList<>(list);
        expectedList.removeAll(actual);
        Assert.assertTrue(expectedList.size() != 0, String.format("Still found %s into list %s", expectedList, actual));
    }

    @Then("List {string} of response should be not contains any member into {string}")
    public void listOfResponseShouldBeNotContainsMember(String key, String members) {
        String[] expectedList = members.split(",");
        List<String> list = response.extract(key);
        for (String expected : expectedList) {
            if (list.contains(expected)) {
                Assert.fail(String.format("List %s must be not contains member '%s'", list, expected));
            }
        }
        Assert.assertTrue(true);
    }


    @And("^the response return should be match with file \"([^\"]*)\"$")
    public void theResponeReturnShouldBeMacthWithFile(String filePath) throws FileNotFoundException {

        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
        JsonParser jsonParser = new JsonParser();

        JsonObject expected = (JsonObject) jsonParser.parse(new FileReader(FileHelper.getFileFromResource(filePath)));
        expected.remove("utcTime0");
        expected.getAsJsonObject("data")
                .getAsJsonArray("list")
                .forEach(
                        list -> {
                            ((JsonObject) list).getAsJsonArray("questions").forEach(
                                    questions -> {
                                        ((JsonObject) questions).getAsJsonArray("questionItems").forEach(questionItems -> {
                                            ((JsonObject) questionItems).remove("questionItemSig");
                                        });
                                    }
                            );
                        }
                );
        JsonObject actual = new JsonParser().parse(response.asString()).getAsJsonObject();
        actual.remove("utcTime0");
        actual.getAsJsonObject("data")
                .getAsJsonArray("list")
                .forEach(
                        list -> {
                            ((JsonObject) list).getAsJsonArray("questions").forEach(
                                    questions -> {
                                        ((JsonObject) questions).getAsJsonArray("questionItems").forEach(questionItems -> {
                                            ((JsonObject) questionItems).remove("questionItemSig");
                                        });
                                    }
                            );
                        }
                );
        Assert.assertEquals(actual, expected);
    }

    @Then("^The response path \"([^\"]*)\" should be matched with \"([^\"]*)\"$")
    public void compareJsonResponseWithFile(String jsonpath, String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        byte[] jsonFile = Files.readAllBytes(FileHelper.getFileFromResource(filePath).toPath());
        JsonNode expectedNote = mapper.readTree(jsonFile);
        JsonNode actualNode = mapper.readTree(response.asString());
        Assert.assertEquals(actualNode.path(jsonpath), expectedNote.path(jsonpath));
    }

    @And("The response {string} has time equal {int} days")
    public void theResponseHasTimeEqualDays(String jsonPath, int days) {
        int seconds = 60 * 1000;
        Date actual = null;
        Date expected = null;
        try {
            actual = new SimpleDateFormat(Text.FTIME_FULL_v2).parse(response.extract(jsonPath));
            expected = Common.getTime(new SimpleDateFormat(Text.FTIME_FULL).parse(Instant.now().toString()), days, Calendar.DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Assert.assertTrue(Math.abs(expected.getTime() - actual.getTime()) <= seconds);
    }

    @Then("Each item in the list {string} contains {string}")
    public void eachItemInTheListContains(String jsonPath, String value) {
    }
}
