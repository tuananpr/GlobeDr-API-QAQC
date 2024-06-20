package com.apis.globedr.enums.metadata;

import com.apis.globedr.helper.Common;
import com.apis.globedr.helper.Path;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import org.testng.Assert;

import java.util.List;

public abstract class AbsMetadata {

    private JsonObject getMetaData() {
        JsonObject jsonObject = Common.getJson(Path.OTHER + "metaData.json");
        return jsonObject.get("data").getAsJsonObject().get("enums").getAsJsonObject();
    }

    public Integer convert(List<String> types, Enum<?>[] values) {
        int result = 0;
        for (String type : types) {
            result += value(type, values);
        }
        return result;
    }

    public Integer value(Object type, Enum<?>[] values) {
        if (type != null && !type.toString().isEmpty()) {
            if (Common.isNumeric(type.toString())) {
                return Integer.parseInt(type.toString());
            } else {
                for (Enum<?> b : values) {
                    if (b.toString().trim().replaceAll("_|\\s+", "").equalsIgnoreCase(type.toString().trim())) {
                        return getValueOfType(b);
                    }
                }
                Assert.fail(String.format("Not found com.com.apis.globedr.apis.globedr.enums '%s' into class '%s' ", type, values[0].getDeclaringClass()));
            }

        }

        return null;
    }


    public JsonElement getEnums(String className) {
        return getMetaData().get(className);
    }

    public String getClassName(Enum<?> enumType) {
        return enumType.getDeclaringClass().getSimpleName();
    }

    public Class getClassPath(Enum<?> enumType) {
        return enumType.getDeclaringClass();
    }

    abstract Integer getValueOfType(Enum<?> enumType);

    public Integer value(Enum<?> enumType) {
        return getValueOfType(enumType);
    }


}
