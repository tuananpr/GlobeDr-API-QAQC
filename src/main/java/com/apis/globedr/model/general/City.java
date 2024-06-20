package com.apis.globedr.model.general;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@NoArgsConstructor
public class City extends CodeAndName{
    public City(String json) throws JsonProcessingException {
        super(json);
    }

    public City (String code, String name){
        super(code, name);
    }
}
