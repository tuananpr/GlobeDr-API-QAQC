package com.apis.globedr.model.response.voucher;

import com.apis.globedr.model.general.Geo;
import com.apis.globedr.model.general.Phone;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Org {
    private List<Phone> phones;
    private Integer id;
    private Integer orgType;
    @JsonAlias({"sig", "signature"})
    private String signature;
    private String name;
    private String address;
    private String coverUrl;
    private Double kmDistance;
    @JsonUnwrapped
    private Geo geo;
    private boolean allowOrder;
    private boolean allowRequestAppt;
}


