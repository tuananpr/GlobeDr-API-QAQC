package com.apis.globedr.enums;

import com.apis.globedr.enums.metadata.AbsMetadata;
import com.apis.globedr.enums.metadata.MetadataEnums;

import java.util.List;

public enum MedicalDocType {
    Prescription,
    LabResult,
    ImmunizationRecord,
    Medical,
    MedicalSupplement1,
    MedicalSupplement2,
    Dental,
    Vision,
    InsurancePrescription,
    DriverLicense,
    PassPort,
    Others,
    ClinicVisit,
    VisitObservation,
    VisitInstruction,
    VisitFollowUp,
    ImmuAppt,
    PresAppt,
    CapillaryAppt,
    InsuranceAppt,
    IDAppt,
    ;

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




