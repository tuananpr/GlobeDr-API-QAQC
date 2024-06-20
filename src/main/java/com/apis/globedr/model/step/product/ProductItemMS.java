package com.apis.globedr.model.step.product;

import com.apis.globedr.enums.Currency;
import com.apis.globedr.enums.ProductServiceType;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProductItemMS {
    private String orgSig;
    private String productSig;
    private String productName;
    private String itemSig;
    private String name;
    private String description;
    private String currencyName;
    private Integer itemId;
    private Integer price;
    private Integer currency;
    private Integer feesDoctorConsult;
    private Integer feesGlobeDrConsult;
    private Integer feesGlobeDr;

    public void setCurrency(Object currency) {
        this.currency = Currency.value(currency);
    }


}
