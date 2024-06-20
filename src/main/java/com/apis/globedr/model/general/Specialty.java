package com.apis.globedr.model.general;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Specialty {
    private String language;
    private String category;
    private String sysCode1;
    private String inGroup1;
    private String inGroup2;
    private String description1;
    private String description2;
    private String description3;
    private Boolean isVisible;
    private Boolean isSystem;
    private String weight;
    private String notes;
    private Double nTag1;
    private String sTag1;
    private String sTag2;
    private String categoryNavigation;

}
