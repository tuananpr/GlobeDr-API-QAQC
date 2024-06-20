package com.apis.globedr.model.request.voucher;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class AddCardsRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String voucherSig;
    private String lotSig;
    private String receiptCode;
    private String description;
    private List<Card> cards;


}


