package com.apis.globedr.helper;

import com.apis.globedr.constant.Text;
import com.apis.globedr.services.database.other.FieldType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.rest.core.debug.CucumberReport;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.ReflectionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.rest.core.debug.Logger;

public class Common {


    public static String listToString(List<?> list) {
        StringBuilder newData = new StringBuilder();
        for (int index = 0; index < list.size(); index++) {
            newData.append(list.get(index) + ",");
        }
        return removeLastCharacter(newData.toString());
    }

    public static String removeLastCharacter(String str) {
        String result = Optional.ofNullable(str)
                .filter(sStr -> sStr.length() != 0)
                .map(sStr -> sStr.substring(0, sStr.length() - 1))
                .orElse(str);
        return result;
    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    public static String addingConditionFieldsDB(Object cls, FieldType fieldType) {
        final StringBuilder newData = new StringBuilder();
        String concatenation = null;
        if (fieldType != null) {
            switch (fieldType) {
                case UPDATE:
                    concatenation = ",";
                    break;
                case WHERE_AND:
                    concatenation = " AND";
                    break;
                case WHERE_OR:
                    concatenation = " OR";
                    break;
            }
        }

        String finalConcatenation = concatenation;

        ReflectionUtils.doWithFields(cls.getClass(), field -> {
            field.setAccessible(true);
            if (field.get(cls) != null) {

                if (Integer.class.isAssignableFrom(field.getType()) || int.class.isAssignableFrom(field.getType())) {
                    newData.append(String.format(" %s = %s%s", field.getName(), field.get(cls), finalConcatenation));
                } else {
                    newData.append(String.format(" %s like '%s'%s", field.getName(), field.get(cls), finalConcatenation));
                }
            }
        });
        return replaceLast(newData.toString(), concatenation, "");
    }

    public static boolean isExistFile(String path) {
        File f = new File(path);
        return f.exists();
    }

    public static Properties loadFileProperties(String path) {
        try {
            if (isExistFile(path)) {
                Properties prop = new Properties();
                BufferedReader ip = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
                prop.load(ip);
                return prop;
            } else {
                throw new Exception(String.format("Error : Not found properties file %s ", path));
            }

        } catch (IOException e) {
            System.out.println(String.format("Error while reading properties file %s: %s ", path, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertFullPhone(String numberPhone, String countryCode) {

        String code = "";
        countryCode = countryCode.replace("+", "");
        switch (countryCode.toUpperCase()) {
            case "VN":
                code = "84";
                break;
            case "US":
                code = "1";
                break;
            default:
                break;
        }
        if (numberPhone.trim().substring(0, 1).equalsIgnoreCase("0")) {
            return code + numberPhone.trim().substring(1);
        }
        return code + numberPhone;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static JsonObject getJson(String filePath) {
        JsonObject jsonObject = null;
        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();
        try {
            jsonObject = new Gson().fromJson(IOUtils.toString(fileResourcesUtils.getFileFromResourceAsStream(filePath)),
                    JsonObject.class);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public static String capitalizeFirstWord(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }


    public static final List<String> strTimes = Arrays.asList("startdate", "enddate", "fromdate", "todate", "tojoindate", "fromjoindate"
            , "ondate", "fromdatehot", "todatehot", "visitdate", "createdate", "dob", "visitedDate");

    public static Map<String, Object> getMap(Map<String, Object> dataTable) {

        if (dataTable != null) {
            Map<String, Object> data = new HashMap<>();
            for (Map.Entry<String, Object> field : dataTable.entrySet()) {

                String key = strToNull(field.getKey());
                Object value = strToNull(field.getValue());

                if (key != null) {


                    if(value != null){
                        switch (value.toString()){
                            case "true" :
                            case "false" :
                                value = Boolean.valueOf(value.toString());
                                break;
                            case "[]" :
                                value = Arrays.asList();
                                break;
                            default:
                        }

                        // convert String variable to variable into Data
                        Matcher m = matcherVariable(value);
                        if (m != null && m.find()) {
                            value = getVariableValue(Data.class, m.group(1));
                        }

                        // convert String time to time
                        for (String date : strTimes) {
                            if(date.equalsIgnoreCase(key) && !field.getValue().toString().equalsIgnoreCase("null")){
                                value = convertStrDateToDate(value);
                                break;
                            }
                        }
                    }

                    // convert String array to array
                    Matcher m = matcherArray(key);
                    if (m != null && m.find()) {
                        data = convertStringArrayToArray(data, key, value);
                        continue;
                    }

                    data.put(key, value);
                }

            }
            return data;
        }
        return null;
    }

    public static Object convertStrDateToDate(Object oTime) {
        Date t = null;
        if (oTime != null) {
            String time = removeAllSpace(oTime.toString());
            switch (time.toLowerCase()) {
                case "today":
                    t = today();
                    break;
                case "yesterday":
                    t = yesterday();
                    break;
                case "tomorrow":
                    t = tomorrow();
                    break;
                default:
                    if (time.toLowerCase().contains("next")) {

                        if (time.toLowerCase().contains("min")) {
                            int totalTime = Integer.parseInt(time.replaceAll("next", "").replaceAll("mins", ""));
                            return format(getNextMinutes(totalTime), Text.FTIME_FULL);
                        }
                        if (time.toLowerCase().contains("day")) {
                            int totalTime = Integer.parseInt(time.replaceAll("next", "").replaceAll("days", ""));
                            return format(getNextTime(totalTime), Text.FTIME_FULL);
                        }

                    }
                    if (time.toLowerCase().contains("prev")) {

                        if (time.toLowerCase().contains("min")) {
                            int totalTime = Integer.parseInt(time.replaceAll("prev", "").replaceAll("mins", ""));
                            return format(getPreviousTime(totalTime), Text.FTIME_FULL);
                        }
                        if (time.toLowerCase().contains("day")) {
                            int totalTime = Integer.parseInt(time.replaceAll("prev", "").replaceAll("days", ""));
                            return format(getPreviousTime(totalTime), Text.FTIME_FULL);
                        }
                    }
                    return time;
            }
            return format(t, Text.FTIME_FULL);
        }

        return oTime;
    }

    public static String convertStrDate(String oTime) {
        return (String) convertStrDateToDate(oTime);
    }

    public static String strToNull(Object content) {
        if (content == null || content.toString().equalsIgnoreCase("null")) return null;
        return content.toString();

    }

    public static Matcher matcherVariable(Object content) {
        Matcher matcher = null;
        if (content != null) {
            //Replace value to value of variable into webservice base
            if (!content.toString().isEmpty()) {
                Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}",
                        Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(content.toString());
            }
        }
        return matcher;
    }

    private static Matcher matcherArray(String content) {
        //Replace value to value of variable into webservice base
        Matcher matcher = null;
        if (content != null) {
            Pattern pattern = Pattern.compile(".*\\[[0-9]+\\].*",
                    Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(content);
        }

        return matcher;
    }

    public static void setNullAllVariable(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                f.set(Data.class, null);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static Object getVariableValue(Class<?> cls, String fieldName) {

        try {
            Field f = cls.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Map<String, Object> convertStringArrayToArray(Map<String, Object> data, String fieldName, Object value) {

        List<String> l = new ArrayList<>();

        String mainKey = null;
        String index = null;
        String item = null;

        if (fieldName != null) {
            Pattern pMainKey = Pattern.compile("(.*)\\[[0-9]+\\].*");
            Matcher mMainKey = pMainKey.matcher(fieldName);
            if (mMainKey.find()) {
                mainKey = mMainKey.group(1);
            }


            Pattern pIndex = Pattern.compile(".*\\[([0-9]+)\\].*");
            Matcher mIndex = pIndex.matcher(fieldName);
            if (mIndex.find()) {
                index = mIndex.group(1);
            }

            Pattern pItem = Pattern.compile(".*\\[[0-9]+\\]\\.(.*)");
            Matcher mItem = pItem.matcher(fieldName);
            if (mItem.find()) {
                item = mItem.group(1);
            }

            if(mainKey != null){
                if(data.get(mainKey) != null){
                    List<Map<String, Object>> list = ((List<Map<String, Object>>) data.get(mainKey));
                    if(index != null){

                        if(list.size() > Integer.parseInt(index)){
                            // update data of existed json object by index

                            Map<String, Object> oldEntry = list.get(Integer.parseInt(index));
                            oldEntry.put(item, value);

                        }else{

                            // Adding new json object into list
                            Map<String, Object> entry = new HashMap<>();
                            entry.put(item, value);
                            List<Map<String, Object>> newList = new ArrayList<>();
                            newList.addAll(list);
                            newList.add(entry);
                            data.replace(mainKey, newList);
                        }
                    }
                }else{
                    // Adding new json object
                    if (item != null) {
                        Map<String, Object> entry = new HashMap<>();
                        entry.put(item, value);
                        data.put(mainKey, Arrays.asList(entry));
                    } else {

                        data.put(mainKey, value);
                    }
                }
            }

        }

        return data;

    }


    public static Date today() {
        Calendar c = Calendar.getInstance();
        // get offSet timezone
        TimeZone tz = c.getTimeZone();
        int offsetMiliseconds = tz.getOffset(new Date().getTime());
        // convert time to 0+UTC
        c.add(Calendar.MILLISECOND, -offsetMiliseconds);
        return c.getTime();
    }

    public static String today(String formatTime) {
        return format(today(), formatTime);
    }


    public static List<Date> getAllDaysOfCurrentWeek() {
        Calendar now = Calendar.getInstance();
        List<Date> list = new ArrayList<>();


        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            list.add(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }


    public static List<String> getAllDaysOfCurrentWeekAsString(String formatTime) {
        List<String> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(formatTime);
        for (Date e : Common.getAllDaysOfCurrentWeek()) {
            list.add(format.format(e));
        }
        return list;
    }



    public static String dayOfWeek(String strDate) {
        Calendar cal = Calendar.getInstance();
        Date date = Common.format(strDate, Text.FTIME_FULL);
        cal.setTime(date);
        return (cal.get(Calendar.DAY_OF_WEEK) == 1) ? "cn" : "t" + cal.get(Calendar.DAY_OF_WEEK);
    }

    public static Date tomorrow() {
        return getNextTime(1);
    }

    public static Date yesterday() {
        return getPreviousTime(1);
    }

    public static String format(Date date, String format, String timezone) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.format(date);
    }

    public static Date format(String stringDate, String format, String timezone) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        Date date = null;
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String format(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date format(String stringDate, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getNextMinutes(int minutes) {
        Date previousTime = addMinutes(today(), -minutes);
        return previousTime;
    }

    public static Date getNextTime(int dateBack) {
        Date previousTime = addDay(today(), dateBack);
        return previousTime;
    }

    public static Date getPreviousTime(int dateBack) {
        Date previousTime = addDay(today(), -dateBack);
        return previousTime;
    }

    private static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();

    }


    private static Date addDay(Date date, int dateBack) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, dateBack);
        return cal.getTime();

    }

    public static String removeAllSpace(String input) {
        return input.replaceAll("\\s", "");
    }

    public static boolean isHasPath(String jsonString, String path) {
        try {
            extractRS(jsonString, path);
        } catch (PathNotFoundException e) {
            return false;
        }
        return true;
    }

    private static <T> T extractRS(Response response, String path) {
        return extractRS(response.asString(), path);
    }

    private static <T> T extractRS(String response, String path) {
        return JsonPath.read(response, path);
    }


    public static void writeJsonToFile(String path, String json) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String convertToBase64(String filePath) {
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public static String getTimeStamp() {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        return timeStamp;
    }


    public static void wait(int seconds) {
        try {
            Logger.getInstance().info(String.format("wait %d seconds" , seconds));
            CucumberReport.write(String.format("wait %d seconds" , seconds));
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static boolean isDouble(String value) {
        try {
            Double.valueOf(value);
            return true;
        } catch (Exception e2) {
            e2.getMessage();
            return false;
        }
    }

    /**
     * Read the object from Base64 string.
     */
    public static Object deserializeObject(String s) {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = null;
        Object o = null;
        try {
            ois = new ObjectInputStream(
                    new ByteArrayInputStream(data));
            o = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return o;
    }

    /**
     * Write the object to a Base64 string.
     */
    public static String serializeObject(Serializable o) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }


    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }


    public static <T> T convert(File json, Class<T> cls) {
        ObjectMapper objectMapper = getObjectMapper();
        try {
            return objectMapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> T mapping(Object fromValue, Class<T> toClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.convertValue(fromValue, toClass);
    }

    public static <T> T convert(String jsonString, Class<T> cls) {
        ObjectMapper objectMapper = getObjectMapper();
        try {
            return objectMapper.readValue(jsonString, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T convert(Response response, String path, TypeReference<T> cls) {
        ObjectMapper objectMapper = getObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(extractRS(response, path));
            return objectMapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDobByAge(String time) {

//      create dob from TODAY - day
        String temp = time;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(Text.FTIME_FULL);

        if (time.equalsIgnoreCase("at birth")) {
            cal.add(Calendar.HOUR, -6);
        } else {
            double month = 30;
            double age = 0;
            int number = Integer.parseInt(temp.replaceAll("\\D+", ""));
            // day
            if (time.toLowerCase().trim().contains("d") && time.toLowerCase().trim().contains("y")) {
                //age = Integer.parseInt(number);
                cal.add(Calendar.DATE, -(int) number);

            }
            // month
            if (time.toLowerCase().trim().contains("m") && time.toLowerCase().trim().contains("th")) {
                //age = Integer.parseInt(number) * month;
                cal.add(Calendar.MONTH, -(int) number);

            }
            // year
            if (time.toLowerCase().trim().contains("y") && time.toLowerCase().trim().contains("r")) {
                //age = Integer.parseInt(number) * month * 12;
                cal.add(Calendar.YEAR, -(int) number);
            }

        }
        return String.format("%s", formatter.format(cal.getTime()));

    }

    public static List<Map<String, Object>> getMapsFromTable(DataTable dataTable) {
        Map<String, Object> newMap = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, String> map : dataTable.asMaps()) {
            newMap.putAll(map);
            list.add(newMap);
        }
        return list;
    }


    public static String getLastcharacter(String string) {
        return string.substring(string.length() - 1);
    }


    public static String runCmd(String cmd) {
        StringBuilder error = new StringBuilder();
        StringBuilder out = new StringBuilder();
        Process proc = null;
        String s = null;
        try {
            Runtime rt = Runtime.getRuntime();
            proc = rt.exec(cmd);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(proc.getErrorStream()));

            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                out.append(s);
            }

            // read any errors from the attempted command
            while ((s = stdError.readLine()) != null) {
                error.append(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (error != null && !error.toString().isEmpty()) {
                Logger.getInstance().info(error.toString());
                CucumberReport.write(error.toString());
            }
            proc.destroy();
        }
        return out.toString();
    }

    public static JSONObject loopJSONObject(String response) {
        JSONObject result = new JSONObject();
        JSONObject jsonObject = new JSONObject(response);
        Iterator<String> keys = jsonObject.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (jsonObject.get(key) instanceof JSONObject) {
                // OBJECT
                result.put(key, loopJSONObject(jsonObject.get(key).toString()));
            } else {
                if (jsonObject.get(key) instanceof JSONArray) {
                    // Array
                    JSONArray array = new JSONArray();
                    for (int i = 0; i < jsonObject.getJSONArray(key).length(); i++) {
                        Object v = jsonObject.getJSONArray(key).get(i);
                        if (v instanceof JSONObject) {
                            array.put(loopJSONObject(v.toString()));
                        } else {
                            if (v instanceof String) {
                                array.put(v + "mahoa");
                            } else {
                                array.put(v);
                            }
                        }

                    }
                    result.put(key, array);
                } else {
                    if (jsonObject.get(key) instanceof String) {
                        // String
                        result.put(key, jsonObject.get(key).toString() + "mahoa");
                    } else {
                        // boolean
                        // Integer
                        result.put(key, jsonObject.get(key));
                    }
                }
            }
        }
        return result;
    }

    public static String formatNumber(Object content) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(16); //Sets the maximum number of digits after the decimal point
        df.setMinimumFractionDigits(0); //Sets the minimum number of digits after the decimal point
        df.setGroupingUsed(false); //If false thousands separator such ad 1,000 wont work so it will display 1000
        return df.format(content);
    }

    public static boolean compareImage(File actualFile, File expectedFile) {

        try {
            // take buffer data from botm image files //
            BufferedImage biA = ImageIO.read(actualFile);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(expectedFile);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            // compare data-buffer objects //
            if (sizeA == sizeB) {
                for (int i = 0; i < sizeA; i++) {
                    if (dbA.getElem(i) != dbB.getElem(i)) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Failed to compare image files ...");
        }
        return false;
    }

    public static Date getTime(Date date, int timeTotal, int calendarType) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarType, timeTotal);
        return cal.getTime();
    }


}
