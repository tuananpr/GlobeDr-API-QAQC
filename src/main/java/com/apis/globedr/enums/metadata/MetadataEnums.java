package com.apis.globedr.enums.metadata;

import com.apis.globedr.helper.Common;
import com.google.gson.JsonElement;
import org.testng.Assert;

public class MetadataEnums extends AbsMetadata {
    protected final int NULL_INT = -99999;

    @Override
    Integer getValueOfType(Enum<?> enumType) {
        String className = Common.capitalizeFirstWord(this.getClassName(enumType));
        JsonElement enums = this.getEnums(className);

        if (enums != null) {
            if (enums.getAsJsonObject() != null) {
                if (enums.getAsJsonObject().has(enumType.toString())) {
                    return enums.getAsJsonObject().get(enumType.toString()).getAsInt();
                }
            }
        }

        Assert.fail(String.format("Not found enums '%s.%s' into Metadata '%s'. It only has %s. " +
                        "Please check and update com.apis.globedr.enums '%s' : %s or data/default_api/other/metaData.json file",
                Common.capitalizeFirstWord(className), enumType.toString(), "data/default_api/other/metaData.json", enums, enumType.toString(), getClassPath(enumType)
        ));

        return NULL_INT;
    }
}
