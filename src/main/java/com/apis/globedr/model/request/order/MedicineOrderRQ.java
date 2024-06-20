package com.apis.globedr.model.request.order;

import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.File;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicineOrderRQ {
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
    private Country country;
    private City city;
    private Ward ward;
    private District district;

}
