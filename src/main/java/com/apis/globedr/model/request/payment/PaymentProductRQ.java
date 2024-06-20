package com.apis.globedr.model.request.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentProductRQ {
    private String orderSig;
    private boolean forWeb;
}
