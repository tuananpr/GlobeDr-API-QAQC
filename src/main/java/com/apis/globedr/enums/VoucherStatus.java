package com.apis.globedr.enums;

import com.apis.globedr.enums.metadata.AbsMetadata;
import com.apis.globedr.enums.metadata.MetadataEnums;

import java.util.List;

public enum VoucherStatus {
    None,
    New,
    Active,
    InActive,
    Draft,
    All;

    private static final AbsMetadata metadata = new MetadataEnums();

    public static Integer convert(List<String> types) {
        return metadata.convert(types, values());
    }

    public static Integer value(Object type) {
        return type.toString().equalsIgnoreCase("All")  ? 0 : metadata.value(type, values());
    }

    public Integer value() {
        return metadata.value(this);
    }
}




