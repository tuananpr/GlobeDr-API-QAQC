package com.apis.globedr.model.step.order;

import com.apis.globedr.enums.DeliveryType;
import com.apis.globedr.helper.FileHelper;
import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.File;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MedicalOrderMS {
    private String userSig;
    private String orgSig;
    private String docSigs;
    private String cardSig;
    private String phoneNumber;
    private String address;
    private String additionalItems;
    private Integer deliveryType;
    private File files;
    private boolean isAttached;

    private void setFiles(String path) {
        this.files = FileHelper.getFileFromResource(path);
    }

    private void setDeliveryType(String type) {
        this.deliveryType = DeliveryType.value(type);
    }
    private Country country;
    private City city;
    private Ward ward;
    private District district;



}
