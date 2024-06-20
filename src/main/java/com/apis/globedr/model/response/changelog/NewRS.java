package com.apis.globedr.model.response.changelog;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewRS {
    private Integer id;
    private String name;
    private String country;
    private Integer language;
    private String startDate;
    private String endDate;

    private Integer linkId ; // default = 0
    private Integer linkType; // default = 0, enums.ChangeLogLinkType
    private String linkSig;
    private Integer status = 2 ; // default = 2
    private String logSig;
    private boolean enableRedirect;

}
