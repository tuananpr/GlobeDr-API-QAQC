package com.apis.globedr.model.step.voucher;

import com.apis.globedr.enums.GiftCardStatus;
import com.apis.globedr.enums.VoucherStatus;
import com.apis.globedr.helper.Common;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.VoucherSig;
import com.apis.globedr.model.request.voucher.Card;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class CardMS {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String voucherSig;
    @JsonAlias({"name", "voucherName"})
    private String name;
    private String receiptCode;
    private String description;
    private String expiredDate;
    private List<Card> cards;

    private Integer total;

    private List<String> voucherSigs;
    private String voucherLink;
    private Integer giftCardStatus;
    private Integer code;
    private String lotSig;
    @JsonAlias({"isGlobedr"})
    private boolean isGlobedr;

    @JsonUnwrapped
    FilterDate date;

    @JsonUnwrapped(suffix = "Used")
    FilterDate hot;
    @JsonUnwrapped
    Page page;

    @JsonAlias({"total"})
    public void setTotal(Integer info) {
        if (cards == null) cards = new ArrayList<>();
        for (int index = 1; index <= info; index++) {
            cards.add(Card.builder()
                    .expiredDate(getExpiredDate())
                    .code("4G" + randomAlphaNumeric(6)).build());
        }
        this.total = info;
    }


    public void setGiftCardStatus(Object status) {
        this.giftCardStatus = GiftCardStatus.value(status);
    }

    public void setExpiredDate(String date){
        this.expiredDate = (String) Common.convertStrDateToDate(date);
    }

    private final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}

