package com.apis.globedr.model.request.review;

import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UseVoucherRQ {
    private String cardSig;
    private String shipAddress;
    private Boolean isAddMember;
    private Country country;
    private City city;
    private District district;
    private Ward ward;
}
