package com.apis.globedr.model.step.voucher;

import com.apis.globedr.model.general.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UseVoucherMS {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private String cardSig;
    private String name;
    private String shipAddress;
    private Boolean isAddMember;
    private Country country;
    private City city;
    private District district;
    private Ward ward;
}
