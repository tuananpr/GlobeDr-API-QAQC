package com.apis.globedr.model.request.orgManager;

import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.Ward;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;

@Data
public class LoadOrgsRQ {
    private Integer type;
    private Integer status;
    @JsonAlias({"name", "orgName"})
    private String name;
    private String address;
    private String city;
    private String region;
    private String country;
    private String email;
    private String fromDate;
    private String toDate;
    private Boolean isVerified;
    private Boolean azureDB;
    District district;
    Ward ward;
    @JsonUnwrapped
    Page page;

}
