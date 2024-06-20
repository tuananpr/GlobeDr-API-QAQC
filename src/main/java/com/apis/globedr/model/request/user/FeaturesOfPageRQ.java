package com.apis.globedr.model.request.user;

import com.apis.globedr.enums.PageDashboard;
import com.apis.globedr.enums.UserPlatform;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class FeaturesOfPageRQ {
    @JsonAlias({"orgSig", "orgSignature"})
    private String orgSig;
    private Integer platform;
    private Integer pageDashboard;

    public void setPlatform(Object info) {
        this.platform = UserPlatform.value(info);
    }

    public void setPageDashboard(Object info) {
        this.pageDashboard = PageDashboard.value(info);
    }
}
