package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.Ward;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class MedicalOrgsRQ {
    private String name;

    private String country;
    private String address;
    private String region;
    private String city;
    private Integer profession;
    private Integer orgType;
    private Integer language;
    private Double longitude;
    private Double latitude;
    private Boolean allowRequestAppt;
    private Boolean allowOrder;

    @JsonUnwrapped
    Page page;
}
