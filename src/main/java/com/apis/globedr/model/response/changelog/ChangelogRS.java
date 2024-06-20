package com.apis.globedr.model.response.changelog;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangelogRS {

    private String id;
    private String name;
    private String logSig;
    private String country;

    private Integer language;
    private Integer status;

}
