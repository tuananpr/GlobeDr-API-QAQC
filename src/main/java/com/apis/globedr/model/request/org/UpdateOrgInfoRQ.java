package com.apis.globedr.model.request.org;

import com.apis.globedr.model.general.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class UpdateOrgInfoRQ {
    @JsonAlias({"name", "orgName"})
    private String orgName;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private Integer orgId;
    private List<Phone> phones;
    private Integer type;
    private Integer status;
    private String address;
    private Double latitude;
    private Double longitude;
    private City city;
    private String region;
    private Country country;
    private String zipCode;
    private String email;
    private String fax;
    private String website;
    private String workHours;


    private Integer featureAttributes;
    private Integer currency;
    private String subdomain;

    private String baseAddress;

    private String intro;

    private String apiKey;
    private String qrCode;
    private Boolean isFollowed;
    private Boolean joinedGdr;
    private Boolean allowRequestAppt;
    private Boolean allowOrder;
    private Boolean allowChat;
    private Boolean allowCall;
    private boolean hasVoucher;
    private Boolean isBranch;

    private Ward ward;
    private District district;
}
