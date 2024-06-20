package com.apis.globedr.enums;

import com.apis.globedr.enums.metadata.AbsMetadata;
import com.apis.globedr.enums.metadata.MetadataEnums;

import java.util.List;

public enum OrgFeatureAttributes {
    None,
    OrgInfo,
    Deparment,
    Member,
    Specialty,
    QRCode,
    SettingInterface,
    Rating,
    Post,
    Voucher,
    ChatInbox,
    ChatCampaign,
    ProductService,
    Order,
    OrderMedicalTest,
    Appointment,
    AppointmentSetting,
    Branch,
    OrderPrescription,
    Consult,
    ConsultConfig,
    MedicalTestConfig,
    MedicalTestTakenSample,
    ALL;


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
