package com.apis.globedr.model.response.topDeal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadTopDealsRS {
    private String topDealSig;
    private String name;
    private String expiredDate;
    private String orgSig;
    private String iconUrl;
    private Integer weight;
    private Boolean isUpdate;

}