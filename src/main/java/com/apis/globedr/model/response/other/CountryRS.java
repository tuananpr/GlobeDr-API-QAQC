package com.apis.globedr.model.response.other;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryRS {
    private String country;
    private String name;
    private String postCode;
    private String search;

    public boolean hasCountry(String any) {

        if (getCountry() != null && getCountry().equalsIgnoreCase(any)) return true;
        if (getName() != null && getName().equalsIgnoreCase(any)) return true;
        if (getPostCode() != null && getPostCode().equalsIgnoreCase(any)) return true;
        if (getSearch() != null && getSearch().equalsIgnoreCase(any)) return true;
        return false;
    }

}
