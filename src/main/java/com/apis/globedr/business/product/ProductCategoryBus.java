package com.apis.globedr.business.product;

import com.apis.globedr.apis.ProductServiceApi;
import com.apis.globedr.model.response.product.ProductCategoryRS;
import com.apis.globedr.model.step.product.ProductCategoryMS;
import com.rest.core.response.Response;

import java.util.ArrayList;
import java.util.List;

public class ProductCategoryBus {


    public ProductCategoryRS createProductCategory(ProductCategoryMS body){
        return ProductServiceApi.getInstant().createProductCategory(body);
    }


    public List<ProductCategoryRS> loadProductCategories(String categoryName){
        List<ProductCategoryRS> result = new ArrayList<>();
        result.addAll(ProductServiceApi.getInstant().loadProductCategories(ProductCategoryMS.builder().nameEN(categoryName).build()));
        result.addAll(ProductServiceApi.getInstant().loadProductCategories(ProductCategoryMS.builder().nameVI(categoryName).build()));
        return result;
    }

    public List<ProductCategoryRS> loadProductCategories(ProductCategoryMS body){
        return ProductServiceApi.getInstant().loadProductCategories(body);
    }

    public ProductCategoryRS updateProductCategory(String oldCategoryName, ProductCategoryMS newData){
        String oldCategorySig = loadProductCategories(oldCategoryName).get(0).getCategorySig();
        newData.setCategorySig(oldCategorySig);
        return ProductServiceApi.getInstant().updateProductCategory(newData);
    }



}


