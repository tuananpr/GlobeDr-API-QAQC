package com.apis.globedr.model.general;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VoucherSig {
    private String voucherSig;

    public VoucherSig(String voucherSig) {
        this.voucherSig = voucherSig;
    }
}
