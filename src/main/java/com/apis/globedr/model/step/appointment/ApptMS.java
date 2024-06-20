package com.apis.globedr.model.step.appointment;

import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.*;
import com.apis.globedr.helper.Common;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Calendar;
import java.util.Date;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ApptMS {
    private String apptSig;
    private String orgSig;
    private Integer userMode;
    private Integer status;
    private Integer type;
    private Integer language;
    private Integer pageDashboard;
    private String deviceId;
    private String name;
    private String patientName;
    private String visitedDate;
    private String doctorName;
    private String onDate;
    private String shift;
    private String contactPhone;
    private String qrCode;
    private String specialtyCode;
    private String visitedAppointmentNotiVi;
    private String visitedAppointmentNotiEN;
    private boolean unAllowDoctorUpdateStatus;
    private boolean isShowConfirmPatientInfo;
    private Integer attributes;

    private boolean isPastVisit;
    private String doctorSig;
    private Integer scheduleExaminationId;
    private Integer timeType;

    @JsonUnwrapped
    FilterDate filterDate;
    @JsonUnwrapped
    Page page;

    public void setType(Object type) {
        this.type = AppointmentType.value(type);
    }

    public void setStatus(Object type) {
        this.status = AppointmentStatus.value(type);
    }


    public void setUserMode(Object type) {
        this.userMode = UserType.value(type);
    }

    public void setPageDashboard(Object type) {
        this.pageDashboard = PageDashboard.value(type);
    }


    public void setTimeType(Object type) {
        this.timeType = TimeType.value(type);
    }
    public void setAttributes(Object type) {
        this.attributes = AppointmentConfigAttributes.value(type);
    }

}
