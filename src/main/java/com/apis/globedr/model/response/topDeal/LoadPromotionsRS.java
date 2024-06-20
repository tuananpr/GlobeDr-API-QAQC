package com.apis.globedr.model.response.topDeal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadPromotionsRS {
    private String promotionSig;
    private String topDealSig;
    private String name;
    private String price;
    private String notes;
    private String orgSig;
}
