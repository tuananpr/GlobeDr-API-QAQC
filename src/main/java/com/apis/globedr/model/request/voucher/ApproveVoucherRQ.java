package com.apis.globedr.model.request.voucher;

import com.apis.globedr.model.general.CategorySig;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.VoucherSig;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class ApproveVoucherRQ {

    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String voucherSig;

    private Integer weightHot;
    private Integer weightPopular;

    private boolean isSendNoti;
    @JsonUnwrapped(suffix = "Popular")
    FilterDate date;

    @JsonUnwrapped(suffix = "Hot")
    FilterDate hot;


}
