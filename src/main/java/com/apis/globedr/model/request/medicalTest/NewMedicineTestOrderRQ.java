package com.apis.globedr.model.request.medicalTest;

import com.apis.globedr.enums.DeliveryType;
import com.apis.globedr.model.general.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewMedicineTestOrderRQ {
    private List<ProductServicesItem> productServices;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String postSig;
    private String orderSig;
    private String patientSig;
    private boolean isDoctorIndicated;
    private Integer deliveryType;
    private String deviceId;
    private String phone;
    private String notes;
    private String address;
    public void setDeliveryType(Object deliveryType) {
        this.deliveryType = DeliveryType.value(deliveryType);
    }
    private Country country;
    private City city;
    private Ward ward;
    private District district;
}
