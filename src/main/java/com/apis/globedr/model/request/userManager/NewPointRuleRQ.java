package com.apis.globedr.model.request.userManager;

import com.apis.globedr.enums.PointActivity;
import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Name;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class NewPointRuleRQ {
    private String descriptionHtml;
    private Integer amount;
    private Integer id;
    private Integer pointActivity;
    private Name ruleName;

    @JsonUnwrapped
    private FilterDate filterDate;

    public void setPointActivity(Object pointActivity) {
        this.pointActivity = PointActivity.value(pointActivity);
    }

}
