package com.apis.globedr.model.request.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTypeList {
    private String iconUrl;
    private String key;
    private String value;
    private String text;
    private Integer tag;

}

