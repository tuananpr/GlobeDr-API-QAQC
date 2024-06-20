package com.apis.globedr.enums;

import com.apis.globedr.enums.metadata.AbsMetadata;
import com.apis.globedr.enums.metadata.MetadataEnums;

import java.util.List;
import java.util.stream.Collectors;

public enum ControlTypeAppt {
    OrgExamination,
    AppointmentType,
    ExaminationDate,
    Specialty,
    Doctor,
    ExaminationReason,
    Notes,
    HealthInfo,
    LastPrescription,
    CapillaryMeasurements,
    ImmuUpload,
    ExaminationAddress,
    HomeMedicalServiceType,
    PhoneContact,
    CriteriaFirstExamination;


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

    public static List<Integer> getValueFromList(List<String> types) {
        return types.stream().map(t -> value(t)).collect(Collectors.toList());
    }

}