package com.apis.globedr.model.step.order;

import com.apis.globedr.enums.*;
import com.apis.globedr.model.general.*;

import com.apis.globedr.model.general.file.File;
import com.apis.globedr.model.general.file.FileFactory;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;



@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class OrderMS {

    private String orgSig;
    private String sourceSig;
    private String orderSig;
    private String userSig;
    private String orgName;
    private String description;
    private String deliveryAddress;

    private Integer orderStatus;
    private Integer productServiceType;
    private Integer deliveryType;
    private Integer orderType;
    private Integer excludeOrderStatus;
    private Integer orderIds;
    private Integer orderProcessType;

    private String notes;
    private String price;
    private String displayName;
    private String deviceId;
    private Boolean isDoctorIndicated;
    private Boolean isResult;
    private Boolean isTakenSampleStaff;
    private Country country;
    private City city;
    private District district;
    private Ward ward;


    @JsonUnwrapped
    private Page page;
    @JsonUnwrapped
    private FilterDate filterDate;

    @JsonUnwrapped
    private File file;



    public OrderMS setFile(String pathFile) {
        this.file = FileFactory.getFile(pathFile);
        return this;
    }

    public void setOrderType(String type){
        orderType = OrderType.value(type);
    }

    public void setOrderType(OrderType type){
        orderType = type.value();
    }

    public void setOrderStatus(String type){
        orderStatus = OrderStatus.value(type);
    }

    public void setOrderProcessType(String type){
        this.orderProcessType = OrderProcessType.value(type);
    }

    public void setDeliveryType(Object deliveryType) {
        this.deliveryType = DeliveryType.value(deliveryType);
    }

    public void setProductServiceType(Object productServiceType) {
        this.productServiceType = ProductServiceType.value(productServiceType);
    }

}
