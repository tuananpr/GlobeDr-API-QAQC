package com.apis.globedr.model.general;

public class Page {
    private Integer page = 1;
    private Integer pageSize = 10;

    public Integer getPage() {
        return page == null ? 1 : page;
    }

    public Integer getPageSize() {
        return pageSize == null ? 10 : pageSize;
    }
}
