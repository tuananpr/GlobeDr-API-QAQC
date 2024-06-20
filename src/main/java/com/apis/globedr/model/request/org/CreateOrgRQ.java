package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.*;

import java.util.List;

public class CreateOrgRQ {

    private List<Phone> phones;
    private Integer type;
    private Integer status;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private String region;
    private String zipCode;
    private String email;
    private String fax;
    private String website;
    private String workHours;

    District district;
    Ward ward;
    Country country;
    City city;
}
