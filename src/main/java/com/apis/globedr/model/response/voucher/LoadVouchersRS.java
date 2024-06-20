package com.apis.globedr.model.response.voucher;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Geo;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoadVouchersRS {
    private Voucher voucher;
    private Category category;
    private Org org;

}
