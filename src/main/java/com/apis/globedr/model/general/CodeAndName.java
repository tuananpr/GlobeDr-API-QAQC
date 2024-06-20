package com.apis.globedr.model.general;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeAndName {

    protected String code;
    protected String name;

    public CodeAndName(String json) throws JsonProcessingException {
        if(!json.isEmpty()){
            var cls = new ObjectMapper().readValue(json, CodeAndName.class);
            setCode(cls.getCode());
            setName(cls.getName());
        }
    }


    public boolean has(String value){
        if (getCode() != null && getCode().equalsIgnoreCase(value)) return true;
        if (getName() != null && getName().equalsIgnoreCase(value)) return true;
        return false;
    }

}
