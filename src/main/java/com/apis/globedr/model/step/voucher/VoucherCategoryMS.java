package com.apis.globedr.model.step.voucher;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class VoucherCategoryMS {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String categorySig;
    private String name;
    private Integer language;
    private String country;
    private String nameVN;
    private String nameEN;
    private Integer weight;
}
