package com.apis.globedr.model.response.product;

import com.apis.globedr.enums.Currency;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProductItemRS {
    private String orgSig;
    private String productSig;
    private String itemSig;
    private String name;
    private String description;
    private String currencyName;
    private Integer itemId;
    private Integer price;
    private Integer currency;

    public void setCurrency(Object currency) {
        this.currency = Currency.value(currency);
    }

}
