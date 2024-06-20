package com.apis.globedr.model.request.changelog;

import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class ListRQ {
    private List<Integer> ids;
    private List<Integer> linkIds;
    private List<Integer> byUserIds;
    private List<Integer> ownerOrgIds;
    private String name;
    private String startDate;
    private String endDate;
    private String country;
    private String createDate;
    private String orgSig;
    private Integer priority;
    private Integer linkType;
    private Integer status;
    private Integer language;
    @JsonUnwrapped
    private Page page;
}
