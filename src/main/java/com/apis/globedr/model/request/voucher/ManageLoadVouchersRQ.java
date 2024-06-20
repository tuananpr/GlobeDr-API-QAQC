package com.apis.globedr.model.request.voucher;

import com.apis.globedr.model.general.CategorySig;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.VoucherSig;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


public class ManageLoadVouchersRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private List<String> voucherSigs;
    private String name;
    private String country;

    @JsonAlias({"voucherStatus"})
    private Integer status;
    private Integer language;
    private Integer weightHot;
    private Integer weightPopular;
    private boolean isLoadMyVoucher;
    private boolean isAdminLoad;
    @JsonUnwrapped
    FilterDate date;

    @JsonUnwrapped(suffix = "Hot")
    FilterDate hot;
    @JsonUnwrapped
    Page page;
}


