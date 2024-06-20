package com.apis.globedr.model.request.voucher;

import com.apis.globedr.model.general.CategorySig;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.VoucherSig;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class LoadGiftCardsRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private List<String> voucherSigs;
    private String voucherLink;

    @JsonAlias({"status", "giftCardStatus"})
    private Integer status;
    private Integer code;
    private Integer description;
    private Integer receiptCode;

    @JsonUnwrapped
    FilterDate date;

    @JsonUnwrapped(suffix = "Used")
    FilterDate hot;
    @JsonUnwrapped
    Page page;

}

