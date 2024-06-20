package stepdefinition;

import com.apis.globedr.business.product.ProductCategoryBus;
import com.apis.globedr.helper.Data;
import com.apis.globedr.model.response.product.ProductCategoryRS;
import com.apis.globedr.model.step.product.ProductCategoryMS;
import io.cucumber.java.en.And;

import java.util.List;

public class ProductCategoryStep extends Data {

    ProductCategoryBus productCategoryBus = new ProductCategoryBus();

    @And("^I create product category with below info$")
    public void iCreateNewProductCategory(List<ProductCategoryMS> list) {
        list.forEach(info->{
            info.setOrgSig(orgSig);
            ProductCategoryRS rs = productCategoryBus.createProductCategory(info);
            if (response.isSuccess()) categorySig = rs.getCategorySig();
        });
    }

    @And("I update product category name {string}")
    public void iUpdateBelowInfoForProductCategoryName(String orgCategoryName, List<ProductCategoryMS> list) {
        ProductCategoryMS newData = list.get(0).setOrgSig(orgSig);
        productCategoryBus.updateProductCategory(orgCategoryName, newData);
    }

    @And("^I load product categories$")
    public void loadProductCategories(List<ProductCategoryMS> list) {
        list.forEach(info->{
            info.setOrgSig(orgSig);
            productCategoryBus.loadProductCategories(info);
        });

    }
}
