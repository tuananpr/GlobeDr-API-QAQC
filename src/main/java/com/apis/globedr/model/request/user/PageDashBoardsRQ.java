package com.apis.globedr.model.request.user;

import com.apis.globedr.enums.UserPlatform;

public class PageDashBoardsRQ {
    private Integer platform;

    public void setPlatform(Object info) {
        this.platform = UserPlatform.value(info);
    }
}
