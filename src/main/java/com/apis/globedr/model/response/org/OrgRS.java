package com.apis.globedr.model.response.org;

import com.apis.globedr.model.general.Phone;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrgRS {
    private List<Phone> phones;
    private Integer orgId;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String orgName;
    private String orgAddress;
    private String domainName;
    private String logoUrl;

    private Double latitude;
    private Double longitude;
    private Integer orgType;
    private Integer orgAttributes;
    private boolean hasVoucher;
    private Integer manageType;
    private Integer apptBeforeDays;
}
