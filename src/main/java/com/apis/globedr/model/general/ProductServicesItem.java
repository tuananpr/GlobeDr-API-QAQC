package com.apis.globedr.model.general;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProductServicesItem {
    private String productServiceSig;
    private List<ItemsSig> serviceItems;

    public ProductServicesItem(String productServiceSig) {
        this.productServiceSig = productServiceSig;
    }

    public void addItems(List<ItemsSig> items) {
        if(serviceItems == null) serviceItems = new ArrayList<>();
        serviceItems.addAll(items);
    }
}
