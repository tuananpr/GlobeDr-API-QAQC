package com.apis.globedr.model.request.order;

import com.apis.globedr.enums.DeliveryType;
import com.apis.globedr.model.general.*;
import com.apis.globedr.model.request.product.ProductServiceSig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"serviceItems"})
public class NewProductOrderRQ {
    private Integer paymentType;
    private Integer deliveryType;
    private Integer examinationID;
    private Integer timeType;
    private String orgSig;
    private String address;
    private String notes;
    private String receiverName;
    private String phone;
    private String orderSig;
    private String onDate;
    private String deviceId;
    private String cardSig;
    private List<ProductServiceSig> productServices;

    public void setDeliveryType(Object value) {
        this.deliveryType = DeliveryType.value(value);
    }

    private Country country;
    private City city;
    private District district;
    private Ward ward;
}
