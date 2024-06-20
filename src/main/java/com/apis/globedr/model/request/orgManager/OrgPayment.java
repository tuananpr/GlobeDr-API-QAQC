package com.apis.globedr.model.request.orgManager;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrgPayment {
    private String orgSig;
    private Integer orgId;
    private String checkoutUrl;
    private String merchantShareKey;
    private String merchantSecretKey;
    private String checksumKey;
    private String businessUsername;
    private String paymentText;
    private String shopTitle;
    private String shopDomain;
    private String shopBackUrl;
    private String urlApi;
    private String notifyUrl;
    private String apiSignature;
    private String apiUsername;
    private String apiPassword;
    private String pwd;
    private String tokenCaptcha;
    private Integer paymentType;
    private Integer shopId;
    private Integer shippingDays;
    private Integer validityTime;
    private Integer itServiceFee;
    private Boolean available;


}
