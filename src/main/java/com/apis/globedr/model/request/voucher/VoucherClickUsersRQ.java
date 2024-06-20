package com.apis.globedr.model.request.voucher;

import com.apis.globedr.model.general.FilterDate;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoucherClickUsersRQ {
    private String orgSig;
    private String voucherSig;
    @JsonUnwrapped
    FilterDate filterDate;

}
