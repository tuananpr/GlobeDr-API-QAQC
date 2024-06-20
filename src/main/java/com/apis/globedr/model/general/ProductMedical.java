package com.apis.globedr.model.general;

import com.apis.globedr.model.response.product.ProductItemRS;
import com.apis.globedr.model.response.product.ProductServicesRS;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductMedical {
    private ProductServicesRS product;
    List<ProductItemRS> services;


}
