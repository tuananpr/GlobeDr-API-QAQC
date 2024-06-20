package stepdefinition;

import com.apis.globedr.business.medicalTest.OrderBus;
import com.apis.globedr.business.payment.PaymentBus;
import com.apis.globedr.business.product.ProductBus;
import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.model.request.payment.PaymentProductRQ;
import com.apis.globedr.model.response.product.ProductRS;
import com.apis.globedr.model.response.product.ProductServicesRS;
import com.apis.globedr.model.response.product.Shops;
import com.apis.globedr.model.step.order.OrderMS;
import com.apis.globedr.model.step.payment.PaymentConfigMS;
import com.apis.globedr.model.step.product.ProductItemMS;

import com.apis.globedr.model.step.product.ProductMS;
import com.apis.globedr.helper.Data;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.List;

public class ProductServiceStep extends Data {
    ProductBus productBus = new ProductBus();
    UserSearchBus userSearchBus = new UserSearchBus();
    OrderBus orderBus = new OrderBus();
    PaymentBus paymentBus = new PaymentBus();

    public Integer itemId;

    @And("^I create new product$")
    public void iCreateNewProduct(List<ProductMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            ProductServicesRS rs = productBus.createNewProduct(info);
            if (response.isSuccess()) productSig = rs.getProductSig();
        });
    }

    @And("^As manager, I load products$")
    public void loadAllProduct(List<ProductMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setAdminLoad(true);
            productBus.loadProductServices(info);
        });
    }

    @And("^As user, I load products$")
    public void userloadAllProduct(List<ProductMS> list) {
        list.forEach(info -> {
            productBus.loadProductServices(info);
        });
    }

    @And("^As user, I load products of org name \"([^\"]*)\"$")
    public void userloadAllProductOfOrg(String name, List<ProductMS> list) {
        list.forEach(info -> {
            String orgSig = userSearchBus.loadOrgsByName(name).stream().map(o->o.getSig()).findFirst().orElse(null);
            info.setOrgSig(orgSig);
            productBus.loadProductServices(info);
        });
    }

    @And("^As user, I order products$")
    public void userOrderProductOfOrg(List<OrderMS> list) {
        list.forEach(info -> {
            String orgSig = userSearchBus.loadOrgsByName(info.getOrgName()).stream().map(o->o.getSig()).findFirst().orElse(null);
            info.setOrgSig(orgSig);
            orderBus.newProductOrder(info, productBus.getProductService(info), Data.get(Text.CURRENT_USER));
        });
    }

    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }




    @And("^As user, I payment products$")
    public void userPaymentProductOfOrg(OrderMS info) {
        PaymentProductRQ body = new PaymentProductRQ();
        body.setOrderSig(orderBus.getOrderSig(info));
        body.setForWeb(true);

        ProductRS rs = paymentBus.product(body);
        Data.set(Text.ORDER_ID, rs.getOrderInfo().getShop().getOrder_no());
    }


    @And("^I add image \"([^\"]*)\" to product name \"([^\"]*)\"$")
    public void iAddImageToProduct(String path, String name) {
        ProductMS body = ProductMS.builder()
                .orgSig(orgSig)
                .name(name)
                .isAdminLoad(true).build();
        productBus.setProductDoc(body, path);
    }

    @When("^I update to product name \"([^\"]*)\"$")
    public void iUpdateProduct(String name, ProductMS newData) {
        ProductMS old = ProductMS.builder()
                .orgSig(orgSig)
                .name(name)
                .build();
        productBus.updateProduct(old, newData);
    }

    @When("^I update to appointment service has name \"([^\"]*)\"$")
    public void iUpdateProductNameAndType(String name, ProductMS newData) {
        ProductMS old = ProductMS.builder()
                .productServiceType(ProductServiceType.AppointmentOrg.value())
                .orgSig(orgSig)
                .name(name)
                .build();
        productBus.updateProduct(old, newData);
    }

    @And("^I update new image \"([^\"]*)\" to product name \"([^\"]*)\"$")
    public void iUpdateImageToProduct(String path, String name) {
        ProductMS body = ProductMS.builder()
                .orgSig(orgSig)
                .name(name)
                .isAdminLoad(true).build();
        productBus.updateProductDoc(body, path);
    }

    @When("^I remove above product doc$")
    public void iRemoveProductDoc(List<ProductMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            productBus.removeProductDoc(info);
        });
    }


    @And("^I create new item for product name \"([^\"]*)\"$")
    public void iCreateNewItemForAboveProduct(String productName, List<ProductItemMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setProductName(productName);
            productBus.newServiceItem(info);
        });
        if (response.isSuccess()) {
            productSig = response.extract("data.info.productSig");
            itemSig = response.extract("data.info.itemSig");
            itemId = response.extract("data.info.itemId");
        }
    }

    @And("^I update item for product name \"([^\"]*)\" with item name \"([^\"]*)\"$")
    public void iUpdateItemForAboveProduct(String productName, String itemName, List<ProductItemMS> list) {
        list.forEach(newData -> {
            newData.setOrgSig(orgSig);
            ProductItemMS old = ProductItemMS
                    .builder()
                    .productName(productName)
                    .name(itemName)
                    .orgSig(orgSig)
                    .build();
            productBus.updateServiceItem(old, newData);
        });

    }

    @And("^I delete item for above product$")
    public void iDeleteItemForAboveProduct(List<ProductItemMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            productBus.deleteServiceItem(info);
        });
    }

    @And("^I load all services into product name \"([^\"]*)\"$")
    public void iLoadAllServicesIntoAboveProduct(String productName, List<ProductItemMS> list) {
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setProductName(productName);
            productBus.loadServiceItems(info);
        });
    }



}
