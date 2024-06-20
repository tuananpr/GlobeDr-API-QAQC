package com.apis.globedr.model.response.voucher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BuyVoucherRS {
    private Integer cardId;
    private String cardSig;
    private String refundDate;
    private String qrCodeVoucher;
}