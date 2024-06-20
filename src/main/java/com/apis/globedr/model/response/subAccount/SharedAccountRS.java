package com.apis.globedr.model.response.subAccount;

import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.general.Ward;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SharedAccountRS {

    private String avatar;
    private String userSignature;
    private Boolean isMainAccount;
    private Integer userId;
    private String displayName;

    private Country country;
    private District district;
    private Ward ward;
    private City city;
}
