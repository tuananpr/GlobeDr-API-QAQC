package com.apis.globedr.enums;

import com.apis.globedr.enums.metadata.AbsMetadata;
import com.apis.globedr.enums.metadata.MetadataEnums;

import java.util.List;

public enum PostType {
    Public,
    Private,
    ToAll,
    System,
    AboutUs,
    TermOfService,
    AboutSetting,
    Immunization,
    AboutImmunization,
    ImmuByAge,
    ImmuByVac,
    ImmuBook,
    ImmuPlan4Pregnant,
    AboutConsult,
    AboutHealth,
    AboutHealthDoc,
    AboutHealthHist,
    ChildDevelopMilestones,
    AboutBMI,
    AboutGrowthChart,
    AboutBloodPressure,
    AboutBloodGlucose,
    AboutBMIChild,
    SupportVoucher,
    AboutBestFunction,
    ComprehensiveHealthCare,
    ChangeLog,
    NormalConsultant,
    TelemedicineConsultant,
    AppointmentConsultant;

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
