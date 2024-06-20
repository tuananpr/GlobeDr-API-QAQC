package com.apis.globedr.helper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;
import com.rest.core.debug.Logger;

public class JsonHelper {
    private static final String DOT = ".";

    /*
     * Author: Duy Ngo
     * Function Name: isJSONValid
     * Description: check whether string is json type ?
     * Param1 : any string ( type : String )
     * Returns Value:
     * 	Return true  if the input string is json object
     * 	Return false if the input string is not json object
     * Example:
     * 		String a = "test 1 23 "
     * 		String b = {"name":"John", "age":31, "city":"New York"};
     * 		isJSONValid(a) : return false
     * 		isJSONValid(b) : return true
     */
    public static boolean isJSONValid(String test) {
        if (test != null && !test.isEmpty()) {
            try {
                new JSONObject(test);
            } catch (JSONException ex) {
                // edited, to include @Arthur's comment
                // e.g. in case JSONArray is valid as well...
                try {
                    new JSONArray(test);
                } catch (JSONException ex1) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /*
     * Author: Duy Ngo
     * Function Name: JSONObject
     * Description: convert json string to json object
     * Param1 : must be json string ( type : String )
     * Returns Value: Json object
     * Example:
     * 		String b = {"name":"John", "age":31, "city":"New York"};
     * 		JSONObject json = isJSONValid(b)
     */
    private static JSONObject parse(String json) {
        if (isJSONValid(json)) {
            return new JSONObject(json);
        } else {
            System.out.println("Invalid json string: " + json);
        }
        return null;
    }


    public static org.json.JSONObject encode(Object json) {
        return new JSONObject(json.toString());

    }


    private static String getString(InputStream inputStream) {
        return (inputStream != null) ? null : new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
    }


    public static JsonObject getJson(String filePath) {
        FileResourcesUtils fileResourcesUtils = new FileResourcesUtils();

        //InputStream inputStream = JsonHelper.class.getClassLoader().getResourceAsStream(filePath);
        InputStream inputStream = fileResourcesUtils.getFileFromResourceAsStream(filePath);

        if (inputStream == null) Logger.getInstance().info("Can't read file from path " + filePath);
        return new Gson().fromJson(FileResourcesUtils.getString(inputStream), JsonObject.class);
    }

    public static com.google.gson.JsonObject convertStringToGson(String jsonString) {
        JsonParser parser = new JsonParser();
        return parser.parse(jsonString).getAsJsonObject();
    }

    private static JsonNode convertStringToJsonNode(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = null;
        try {
            actualObj = mapper.readTree(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return actualObj;
    }

    public static JsonObject updateJson(String fileJsonTemplate, Map<String, Object> dataTable) {
        updateJson(getJson(fileJsonTemplate), dataTable);
        return updateJson(getJson(fileJsonTemplate), dataTable);
    }


    public static JsonObject update(JsonObject jsonObject, String jsonPath, Object value) {
        Configuration configuration = Configuration.builder()
                .jsonProvider(new JacksonJsonNodeJsonProvider())
                .mappingProvider(new JacksonMappingProvider())
                .build();

        // Use Jackson to update value by json path
        JsonNode jackson = convertStringToJsonNode(jsonObject.toString());


        jackson = JsonPath.using(configuration)
                .parse(jackson.toString())
                .set("$." + jsonPath, value)
                .json();

        // parser JackSon to Gson
        return convertStringToGson(jackson.toString());
    }

    private static JsonNode setInputKeyAndValue(JsonNode jackson, Map.Entry<String, Object> field) {
        Configuration configuration = Configuration.builder()
                .jsonProvider(new JacksonJsonNodeJsonProvider())
                .mappingProvider(new JacksonMappingProvider())
                .build();

        if (!field.getKey().isEmpty()) {
            switch (String.valueOf(field.getValue()).toLowerCase()) {
                case "true":
                case "false":
                    jackson = JsonPath.using(configuration)
                            .parse(jackson.toString())
                            .set("$." + field.getKey(), Boolean.parseBoolean(String.valueOf(field.getValue())))
                            .json();
                    break;
                case "null":
                    jackson = JsonPath.using(configuration)
                            .parse(jackson.toString())
                            .set("$." + field.getKey(), null)
                            .json();
                case "":
                    jackson = JsonPath.using(configuration)
                            .parse(jackson.toString())
                            .set("$." + field.getKey(), "")
                            .json();
                default:

                    // if value is json
                    if (String.valueOf(field.getValue()).toLowerCase().startsWith("{") && String.valueOf(field.getValue()).toLowerCase().endsWith("}")) {
                        jackson = JsonPath.using(configuration)
                                .parse(jackson.toString())
                                .set("$." + field.getKey(), JsonPath.using(configuration)
                                        .parse(String.valueOf(field.getValue())).json())
                                .json();
                    } else {
                        jackson = JsonPath.using(configuration)
                                .parse(jackson.toString())
                                .set("$." + field.getKey(), field.getValue())
                                .json();
                    }

                    break;
            }
        }
        return jackson;
    }

    private static JsonNode addInputKeyAndValue(JsonNode jackson, Map.Entry<String, Object> field) {
        if (!field.getKey().isEmpty()) {
            if (field.getValue() != null) {
                switch (String.valueOf(field.getValue()).toLowerCase()) {
                    case "true":
                    case "false":
                        jackson = ((ObjectNode) jackson).put(field.getKey(), Boolean.parseBoolean(String.valueOf(field.getValue())));
                        break;
                    case "null":
                        jackson = ((ObjectNode) jackson).putNull(field.getKey());
                    case "":
                        jackson = ((ObjectNode) jackson).put(field.getKey(), "");
                    default:
                        // if value is json
                        if (isNumeric(field.getValue().toString())) {
                            if (field.getValue().toString().contains(".")) {
                                jackson = ((ObjectNode) jackson).put(field.getKey(), Double.parseDouble((field.getValue().toString())));
                            } else {
                                jackson = ((ObjectNode) jackson).put(field.getKey(), Long.parseLong((field.getValue().toString())));
                            }
                        } else {
                            jackson = ((ObjectNode) jackson).put(field.getKey(), field.getValue().toString());
                        }

                        break;
                }
            } else {
                jackson = ((ObjectNode) jackson).putNull(field.getKey());
            }

        }
        return jackson;
    }


    public static JsonObject update(JsonObject jsonObject, Map<String, Object> dataTable) {

        // Use Jackson to update value by json path
        JsonNode jackson = convertStringToJsonNode(jsonObject.toString());
        if (dataTable != null) {
            for (Map.Entry<String, Object> field : dataTable.entrySet()) {
                if (field.getKey() != null && !field.getKey().isEmpty()) {
                    if (isHasPath(jsonObject.toString(), field.getKey())) {
                        // input key match with key into json file. Then we update it
                        jackson = setInputKeyAndValue(jackson, field);
                    } else {
                        // We not found the input key into json file. then we add input key anh value to json
                        jackson = addInputKeyAndValue(jackson, field);
                    }

                }
            }
        }


        // parser JackSon to Gson
        return convertStringToGson(jackson.toString());
    }

    public static JsonObject updateJson(JsonObject jsonObject, Map<String, Object> dataTable) {

        // Use Jackson to update value by json path
        JsonNode jackson = convertStringToJsonNode(jsonObject.toString());
        if (dataTable != null) {
            for (Map.Entry<String, Object> field : dataTable.entrySet()) {
                if (field.getKey() != null && !field.getKey().isEmpty()) {
                    if (isHasPath(jsonObject.toString(), field.getKey())) {
                        // input key match with key into json file. Then we update it
                        jackson = setInputKeyAndValue(jackson, field);
                    } else {
                        // We not found the input key into json file. then we add input key anh value to json
                        jackson = addInputKeyAndValue(jackson, field);
                    }

                }
            }
        }
        // parser JackSon to Gson
        return convertStringToGson(jackson.toString());
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



}
