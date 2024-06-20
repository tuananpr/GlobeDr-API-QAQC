package com.apis.globedr.model.request.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayooOrderRQ {
    private Object orderId;
    private String tokenCaptcha;
}
