package com.apis.globedr.model.request.voucher;

import com.apis.globedr.model.general.Geo;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadVouchersRQ {
    private Integer language;
    @JsonUnwrapped
    private Geo geo;

    private String orgSig;
    private String country;
    private String city;
    private String categorySig;
    private String voucherName;

    private boolean isHot;
    private boolean isNew;
    private boolean isPopular;
    private boolean isAdminLoad;

    @JsonUnwrapped
    Page page;

}
