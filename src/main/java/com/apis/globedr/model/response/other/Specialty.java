package com.apis.globedr.model.response.other;

import lombok.Data;

import java.util.List;

@Data
public class Specialty {
    private String code;
    private String name;
    private List<String> listSub;
}
