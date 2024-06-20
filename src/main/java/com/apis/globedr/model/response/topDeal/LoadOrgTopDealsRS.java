package com.apis.globedr.model.response.topDeal;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadOrgTopDealsRS {
    List<LoadPromotionsRS> promotions;
    private String topDealSig;
    private String name;
    private String orgSig;
}