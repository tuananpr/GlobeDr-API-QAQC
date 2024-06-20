package com.apis.globedr.apis;

import com.apis.globedr.model.request.product.*;
import com.apis.globedr.model.response.product.ProductCategoryRS;
import com.apis.globedr.model.response.product.ProductServicesRS;
import com.apis.globedr.model.response.product.ProductItemRS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;

import java.util.List;

public class ProductServiceApi extends BaseApi {

    private ProductServiceApi() {
    }

    private static ProductServiceApi instant;

    public static ProductServiceApi getInstant() {
        if (instant == null) instant = new ProductServiceApi();
        return instant;
    }

    public ProductCategoryRS createProductCategory(Object body) {
        return RestCore.given().url(API.ProductService.NEW_CATEGORY()).auth(token)
                .body(body, ProductCategoryRQ.class).post().send()
                .extractAsModel("data.info", ProductCategoryRS.class);
    }

    public ProductCategoryRS updateProductCategory(Object body) {
        return RestCore.given().url(API.ProductService.CATEGORY()).auth(token)
                .body(body, ProductCategoryRQ.class).post().send()
                .extractAsModel("data.info", ProductCategoryRS.class);
    }

    public List<ProductCategoryRS> loadProductCategories(Object body) {
        return RestCore.given().url(API.ProductService.PRODUCT_CATEGORIES()).auth(token)
                .body(body, ProductCategoryRQ.class).post().send()
                .extractAsModels("data.list", ProductCategoryRS.class);
    }


    public ProductServicesRS createNewProduct(Object body) {
        return RestCore.given().url(API.ProductService.NEW_PRODUCT()).auth(token)
                .body(body, ProductRQ.class).post().send()
                .extractAsModel("data.info", ProductServicesRS.class);
    }

    public List<ProductServicesRS> loadProductServices(Object body) {
        return RestCore.given().url(API.ProductService.PRODUCT_SERVICES()).auth(token)
                .body(body, LoadProductRQ.class).post().send()
                .extractAsModels("data.list", ProductServicesRS.class);
    }

    public Response setProductDoc(Object body) {
        return RestCore.given().url(API.ProductService.NEW_PRODUCT_DOC()).auth(token)
                .multipart(body, UpdateProductDocRQ.class).post().send();
    }

    public ProductServicesRS updateProduct(Object body) {
        return RestCore.given().url(API.ProductService.PRODUCT()).auth(token)
                .body(body, ProductRQ.class).put().send()
                .extractAsModel("data.info", ProductServicesRS.class);

    }

    public Response updateProductDoc(Object body) {
        return RestCore.given().url(API.ProductService.PRODUCT_DOC()).auth(token)
                .multipart(body, UpdateProductDocRQ.class).put().send();
    }

    public Response existProductDoc(Object body) {
        return RestCore.given().url(API.ProductService.EXIST_PRODUCT_DOC()).auth(token)
                .body(body, ExistProductDocRQ.class).delete().send();

    }


    public ProductItemRS newServiceItem(Object body) {
        return RestCore.given().url(API.ProductService.NEW_SERVICE_ITEM()).auth(token)
                .body(body, NewServiceItemRQ.class).post().send()
                .extractAsModel("data.info", ProductItemRS.class);
    }


    public ProductItemRS serviceItem(Object body) {
        return RestCore.given().url(API.ProductService.SERVICE_ITEM()).auth(token)
                .body(body, NewServiceItemRQ.class).put().send()
                .extractAsModel("data.info", ProductItemRS.class);

    }


    public Response existServiceItem(Object body) {
        return RestCore.given().url(API.ProductService.EXIST_SERVICE_ITEM()).auth(token)
                .body(body, NewServiceItemRQ.class).delete().send();
    }


    public List<ProductItemRS> serviceItems(Object body) {
        return RestCore.given().url(API.ProductService.SERVICE_ITEMS()).auth(token).body(body,
                ServiceItemsRQ.class).post().send()
                .extractAsModels("data.list", ProductItemRS.class);
    }


}
