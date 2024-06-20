package com.apis.globedr.model.general;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Tag {
    public String name;

    public Tag(){}
    public Tag(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
