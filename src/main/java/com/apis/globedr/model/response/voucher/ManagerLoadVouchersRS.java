package com.apis.globedr.model.response.voucher;

import com.apis.globedr.model.general.FilterDate;
import com.apis.globedr.model.general.Page;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerLoadVouchersRS {
    private Integer categoryId;
    private Integer status;
    private Integer total;
    private Integer totalClickLink;
    private Integer totalSold;
    private Integer type;
    private Integer vendorId;
    private Integer leftCount;
    private Integer voucherId;
    private Integer totalUsed;
    private String categorySig;
    private String city;
    private String country;
    private String description;
    private String name;
    private String note;
    private String statusName;
    private String vendorName;
    private String vendorSig;
    private String voucherSig;
    private String iconHotUrl;
    private String iconUrl;
    private String link;
    private boolean isHot;
    private boolean isOneUse;
    private boolean isPopular;
    private boolean isShip;
    @JsonUnwrapped(suffix = "Hot")
    FilterDate hot;
    @JsonUnwrapped
    Page page;

}

