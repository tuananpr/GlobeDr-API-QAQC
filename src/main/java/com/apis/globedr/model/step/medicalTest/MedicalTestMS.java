package com.apis.globedr.model.step.medicalTest;

import com.apis.globedr.enums.DeliveryType;
import com.apis.globedr.model.general.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MedicalTestMS {
    private String orgName;
    private String patientSig;
    private String orgSig;
    private String orderSig;
    private String postSig;
    private String notes;
    private String phone;
    private String address;
    private String productAndService;
    private String serviceName;
    private Integer deliveryType;
    private Integer productServiceType;
    private boolean isDoctorIndicated;

    private List<ProductServicesItem> productServices;

    private void setDeliveryType(String type) {
        this.deliveryType = DeliveryType.value(type);
    }

    private Country country;
    private City city;
    private Ward ward;
    private District district;
}


