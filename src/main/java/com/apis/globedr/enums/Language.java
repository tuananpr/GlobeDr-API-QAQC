package com.apis.globedr.enums;

import com.apis.globedr.enums.metadata.AbsMetadata;
import com.apis.globedr.enums.metadata.MetadataEnums;

import java.util.List;

public enum Language {
    English,
    Vietnam,
    Thailand,
    Chinese,
    French,
    Russia,
    Japanese,
    Korean
    ;


    private static final AbsMetadata metadata = new MetadataEnums();

    public static Integer convert(List<String> types) {
        return metadata.convert(types, values());
    }

    public static Integer value(Object type) {
        if (type != null) {
            if (type.toString().equalsIgnoreCase("VN") || type.toString().isEmpty()) {
                return 1;
            }

            if (type.toString().equalsIgnoreCase("US")) {
                return 0;
            }
        }

        return metadata.value(type, values());
    }

    public Integer value() {
        return metadata.value(this);
    }


}
