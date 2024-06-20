package com.apis.globedr.model.request.voucher;

import com.apis.globedr.model.general.FilterDate;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class SetHotVoucherRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String voucherSig;
    private Integer weightHot;
    private Integer weightPopular;
    private boolean isSendNoti;
    @JsonUnwrapped(suffix = "Hot")
    FilterDate hot;
    @JsonUnwrapped(suffix = "Popular")
    FilterDate popular;
}
