package com.apis.globedr.model.general;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListProductMedical {
    private List<ProductMedical> list;

    /* This method get product and service of this product base on input
     * productAndService has syntax productNameA1:serviceNameA1+serviceNameA2,productNameB2:serviceNameB1
     *
     */
    public List<ProductServicesItem> getProductServices(String productAndService){
        List<ProductServicesItem> result = new ArrayList<>();
        if ( productAndService != null && !productAndService.isEmpty()){
            for (String group :productAndService.split(",")) {
                String productName = group.split(":")[0];
                result.addAll(list.stream()
                        .filter(data->data.getProduct().getName().equalsIgnoreCase(productName))
                        .map(data ->
                        {
                            ProductServicesItem product = new ProductServicesItem();
                            product.setProductServiceSig(data.getProduct().getProductSig());
                            String groupService = group.split(":")[1];
                            for (String serviceName : groupService.split("\\+")) {
                                product.addItems(data.getServices().stream()
                                        .filter(s->s.getName().equalsIgnoreCase(serviceName))
                                        .map(s-> new ItemsSig(s.getItemSig()))
                                        .collect(Collectors.toList()));
                            }
                            return product;
                        }).collect(Collectors.toList()));
            }
        }
        return result;

    }
}
