package com.apis.globedr.model.request.subAccount;

import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubAccountRQ {
    private String displayName;
    private String dob;
    private Country country;
    private District district;
    private Ward ward;
    private City city;
    private String phone;
    private String address;
    private String email;
    private Integer carerType;
    private Integer gender;
    private Double height;
    private Double weight;
}
