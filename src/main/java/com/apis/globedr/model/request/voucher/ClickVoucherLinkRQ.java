package com.apis.globedr.model.request.voucher;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClickVoucherLinkRQ {
    private String voucherSig;
    private String link;

}
