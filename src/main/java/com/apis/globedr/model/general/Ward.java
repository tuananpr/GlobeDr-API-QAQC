package com.apis.globedr.model.general;



import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.*;

@NoArgsConstructor
public class Ward extends CodeAndName{
    public Ward(String json) throws JsonProcessingException {
        super(json);
    }

    public Ward(String code, String name) {
        super(code, name);
    }
}
