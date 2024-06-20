package com.apis.globedr.model.step.product;

import com.apis.globedr.enums.Currency;
import com.apis.globedr.enums.PaymentType;
import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.file.ImageFile;
import com.apis.globedr.model.response.product.ProductItemRS;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ProductMS {
    private String orgSig;
    private String productSig;
    private String name;
    private String nameEn;
    private String description;
    private String code;
    private String fromDate;
    private String toDate;
    private String link;
    private String categorySig;
    private String currencyName;
    private Double fromPrice;
    private Double toPrice;
    private Boolean isVisible;
    private Boolean isPublic;

    @JsonAlias({"orgProductType", "productServiceType"})
    private Integer productServiceType;
    private Integer itemSig;
    private Integer linkId;
    private Integer language;
    private Integer currency;
    private Integer paymentTypes;
    private Integer categoryId;
    private Integer monthsUsedApp;
    private Integer points;
    private Integer feesDoctorConsult;
    private Integer feesGlobeDrConsult;
    private Integer feesGlobeDr;

    private boolean isAdminLoad;
    private boolean isGlobedr;
    private Boolean vip;
    private boolean isTrial;
    private boolean isFree;
    private boolean isInternal;
    private boolean allowVideoCall;
    private List<Integer> linkIds;
    private String docSig;

    @JsonUnwrapped
    ImageFile file;

    public ProductMS setFile(String pathFile) {
        this.file = (ImageFile) FileFactory.getFile(pathFile);
        return this;
    }

    @JsonUnwrapped
    Page page;


    @Builder.Default
    private List<ProductItemRS> listItems = new ArrayList<>();

    public List<ProductItemRS> getListItems() {
        return listItems;
    }

    public void addServiceItem(List<ProductItemRS> serviceItems) {
        getListItems().addAll(serviceItems);
    }


    public ProductMS setProductServiceType(Object productServiceType) {
        this.productServiceType = ProductServiceType.value(productServiceType);
        return this;
    }


    public void setCurrency(Object currency) {
        this.currency = Currency.value(currency);
    }

    public void setPaymentTypes(Object value) {
        if(value instanceof String){
            this.paymentTypes = PaymentType.convert(Arrays.asList(value.toString().split(",")));
        }else{
            this.paymentTypes = (Integer) value;
        }

    }
}

