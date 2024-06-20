package com.apis.globedr.model.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Country {
    private String name;
    private String country;
    private String postCode;

    public Country(String json) throws JsonProcessingException {
        if(!json.isEmpty()){
            var cls = new ObjectMapper().readValue(json, Country.class);
            setName(cls.getName());
            setCountry(cls.getCountry());
            setPostCode(cls.getPostCode());
        }
    }

    public Country(String country, String name, String postCode){
        this.name = name;
        this.country = country;
        this.postCode = postCode;
    }
}