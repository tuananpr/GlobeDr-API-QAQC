package com.apis.globedr.model.response.product;

import com.apis.globedr.model.general.Doc;
import com.apis.globedr.model.request.product.PaymentTypeList;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductServicesRS {
    private List<PaymentTypeList> paymentTypeList;
    private Integer paymentTypes;
    private Integer productId;
    private Integer orgProductType;
    private String currencyName;
    private String displayName;
    private String address;
    private String orgName;
    private String categorySig;
    private String orgSig;
    private String name;
    private String description;
    private String productSig;
    private Double price;
    private String code;

    private String nameEn;
    private List<Doc> productDocs;
    private String link;
    private String onDate;
    private Integer linkId;
    private Integer currency;
    private Integer categoryId;
    private Double fromPrice;
    private Double toPrice;
    private boolean isVisible;
    private boolean isPublic;
    private boolean vip;
    private boolean isFree;
    private boolean isInternal;

}



