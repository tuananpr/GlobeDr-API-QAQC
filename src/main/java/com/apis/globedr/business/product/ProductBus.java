package com.apis.globedr.business.product;

import com.apis.globedr.apis.ProductServiceApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.model.general.ListProductMedical;
import com.apis.globedr.model.general.ProductMedical;
import com.apis.globedr.model.request.product.LoadProductRQ;
import com.apis.globedr.model.response.product.ProductServicesRS;
import com.apis.globedr.model.step.order.OrderMS;
import com.apis.globedr.model.step.product.ProductItemMS;
import com.apis.globedr.model.response.product.ProductItemRS;
import com.apis.globedr.model.step.product.ProductMS;
import com.rest.core.response.Response;

import java.util.List;
import java.util.stream.Collectors;

public class ProductBus extends AbsBus {
    ProductServiceApi pServiceApi = ProductServiceApi.getInstant();

    public ProductServicesRS createNewProduct(ProductMS body) {
        return pServiceApi.createNewProduct(body);
    }

    public List<ProductServicesRS> loadProductServices(ProductMS body) {
        return pServiceApi.loadProductServices(body);
    }

    public ProductServicesRS getProductService(ProductMS body) {
        return pServiceApi.loadProductServices(body).get(0);
    }

    public ProductServicesRS getProductService(OrderMS info) {
        LoadProductRQ body = new LoadProductRQ();
        body.setOrgSig(info.getOrgSig());
        body.setDescription(info.getDescription());
        body.setProductServiceType(info.getProductServiceType());
        return pServiceApi.loadProductServices(body).get(0);
    }

    public ListProductMedical loadProductMedicalTest(String orgSig) {
        ProductMS body = ProductMS.builder().orgSig(orgSig).productServiceType(ProductServiceType.MedicalTest.value()).build();
        ListProductMedical list = new ListProductMedical();
        list.setList(loadProductServices(body).stream()
                .map(p -> {
                        ProductItemMS b = ProductItemMS.builder().orgSig(orgSig).productSig(p.getProductSig()).build();
                        ProductMedical result = new ProductMedical();
                        result.setProduct(p);
                        result.setServices(loadServiceItems(b));
                        return result;
                }).collect(Collectors.toList()));

        return list;
    }


    public List<ProductServicesRS> loadProductAndService(String orgSig, Integer productServiceType) {
        ProductMS body = ProductMS.builder().orgSig(orgSig).productServiceType(productServiceType).build();
        return pServiceApi.loadProductServices(body);
    }


    private ProductMS getProductMSToUpload(ProductServicesRS rs, String file) {
        return ProductMS
                .builder()
                .orgSig(rs.getOrgSig())
                .productSig(rs.getProductSig())
                .build().setFile(file);
    }

    public Response updateProductDoc(ProductMS oldProduct, String file) {
        ProductServicesRS rs = loadProductServices(oldProduct).get(0);
        ProductMS request = getProductMSToUpload(rs, file)
                .setDocSig(rs.getProductDocs().get(0).getDocSig());
        return pServiceApi.updateProductDoc(request);
    }

    public Response setProductDoc(ProductMS oldProduct, String file) {
        ProductServicesRS rs = loadProductServices(oldProduct).get(0);
        ProductMS request = getProductMSToUpload(rs, file);
        return pServiceApi.setProductDoc(request);
    }

    public Response removeProductDoc(ProductMS oldProduct) {
        ProductServicesRS rs = loadProductServices(oldProduct).get(0);
        ProductMS request = ProductMS
                .builder()
                .orgSig(rs.getOrgSig())
                .docSig(rs.getProductDocs().get(0).getDocSig()).build();
        return pServiceApi.existProductDoc(request);
    }

    public ProductServicesRS updateProduct(ProductMS oldProduct, ProductMS newData) {
        oldProduct.setAdminLoad(true);
        ProductServicesRS rs = loadProductServices(oldProduct).get(0);
        newData.setOrgSig(rs.getOrgSig())
                .setCategorySig(rs.getCategorySig())
                .setProductSig(rs.getProductSig())
                .setLinkId(rs.getLinkId());
        return pServiceApi.updateProduct(newData);
    }

    private List<ProductServicesRS> orgLoadProduct(String productName, String orgSig) {
        ProductMS rLoad = ProductMS.builder()
                .orgSig(orgSig)
                .name(productName)
                .isAdminLoad(true)
                .build();
        return loadProductServices(rLoad);
    }

    public ProductItemRS newServiceItem(ProductItemMS body) {
        ProductServicesRS rs = orgLoadProduct(body.getProductName(), body.getOrgSig()).get(0);
        body.setOrgSig(rs.getOrgSig())
                .setProductSig(rs.getProductSig());
        return pServiceApi.newServiceItem(body);
    }


    public List<ProductItemRS> loadServiceItems(ProductItemMS body) {
        List<ProductServicesRS> rs = orgLoadProduct(body.getProductName(), body.getOrgSig());
        return rs.stream()
                .flatMap(p -> pServiceApi.serviceItems(body.setOrgSig(p.getOrgSig()).setProductSig(p.getProductSig())).stream())
                .collect(Collectors.toList());
    }


    public ProductItemRS updateServiceItem(ProductItemMS old, ProductItemMS newData) {
        ProductItemRS rs = loadServiceItems(old).get(0);
        newData.setItemSig(rs.getItemSig())
                .setProductSig(rs.getProductSig());
        return pServiceApi.serviceItem(newData);
    }

    public Response deleteServiceItem(ProductItemMS body) {
        ProductItemRS rs = loadServiceItems(body).get(0);
        body.setItemSig(rs.getItemSig())
                .setProductSig(rs.getProductSig());
        return pServiceApi.existServiceItem(body);
    }


}
