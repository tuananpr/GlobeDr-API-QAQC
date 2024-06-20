package com.apis.globedr.model.response.product;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRS {
    private String orderChecksum;
    private Integer shopID;
    private String customerPhone;
    private String merchantShareKey;
    private Object orderInfo;

    public Shops getOrderInfo(){
        return (Shops) this.orderInfo;
    }
}
