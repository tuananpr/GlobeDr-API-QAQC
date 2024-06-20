package com.apis.globedr.model.request.voucher;

import com.fasterxml.jackson.annotation.JsonAlias;

public class AddCardAutoRQ {
    private String description;
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String voucherSig;
    private String lotSig;
    private String receiptCode;
    private String expiredDate;
    private Integer total;
    private boolean isGlobedr;
}
