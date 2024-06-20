package com.apis.globedr.model.step.payment;

import com.apis.globedr.enums.PaymentType;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class PaymentConfigMS {
    private String orgSig;
    private String orgName;
    private Integer orgId;
    private String pwd;
    private String checkoutUrl;
    private String merchantShareKey;
    private String merchantSecretKey;
    private String businessUsername;
    private String paymentText;
    private String checksumKey;
    private String shopTitle;
    private String shopDomain;
    private String shopBackUrl;
    private String urlApi;
    private String notifyUrl;
    private String apiSignature;
    private String apiUsername;
    private String apiPassword;
    private String tokenCaptcha;
    private Integer paymentType;
    private Integer shopId;
    private Integer shippingDays;
    private Integer validityTime;
    private Integer itServiceFee;
    private Boolean available;


    public PaymentConfigMS setPaymentType(Object value ){
        this.paymentType = PaymentType.value(value);
        return this;
    }
}
