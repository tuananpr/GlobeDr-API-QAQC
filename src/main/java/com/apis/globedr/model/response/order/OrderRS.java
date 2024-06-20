package com.apis.globedr.model.response.order;

import com.apis.globedr.model.general.Status;
import com.apis.globedr.model.general.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRS {
    private String orderId;
    private String orderSig;
    private String sourceSig;
    private String address;

    private String receiverName;
    private Integer deliveryType;

    private Status status;
    private Type type;
    private PatientInfo patientInfo;

}
