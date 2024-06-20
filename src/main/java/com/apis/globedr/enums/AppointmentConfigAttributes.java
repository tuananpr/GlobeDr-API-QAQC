package com.apis.globedr.enums;

import com.apis.globedr.enums.metadata.AbsMetadata;
import com.apis.globedr.enums.metadata.MetadataEnums;

import java.util.List;

public enum AppointmentConfigAttributes {
    None,
    IsAllowDoctorUpdateStatus,
    IsShowConfirmPatientInfo,
    IsRequireHealthInfo;

    private static final AbsMetadata metadata = new MetadataEnums();

    public static Integer convert(List<String> types) {
        return metadata.convert(types, values());
    }

    public static Integer value(Object type) {
        return metadata.value(type, values());
    }

    public Integer value() {
        return metadata.value(this);
    }
    }
