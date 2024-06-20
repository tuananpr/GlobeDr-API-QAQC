package stepdefinition;


import com.apis.globedr.business.ConnectionBus;
import com.apis.globedr.business.consult.ConsultDoctor;
import com.apis.globedr.business.consult.ConsultRecipient;
import com.apis.globedr.business.health.HealthBus;
import com.apis.globedr.business.medicalTest.OrderBus;
import com.apis.globedr.business.org.OrgBus;
import com.apis.globedr.business.product.ProductBus;
import com.apis.globedr.business.search.UserSearchBus;
import com.apis.globedr.constant.API;
import com.apis.globedr.enums.OrderType;
import com.apis.globedr.enums.OrgFeatureAttributes;
import com.apis.globedr.model.general.ProductServicesItem;
import com.apis.globedr.model.request.order.OrderAssignTakenSampleRQ;
import com.apis.globedr.model.step.health.HealthDocMS;
import com.apis.globedr.model.step.order.MedicalOrderMS;
import com.apis.globedr.model.step.medicalTest.MedicalTestMS;
import com.apis.globedr.model.step.order.OrderAssignTakenSampleMS;
import com.apis.globedr.model.step.order.OrderMS;
import com.apis.globedr.model.step.org.StaffMS;
import io.cucumber.java.en.Given;
import com.apis.globedr.enums.ProductServiceType;
import com.apis.globedr.helper.Data;
import io.cucumber.java.en.And;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class OrderStep extends Data {


    ProductBus productBus = new ProductBus();
    ConnectionBus connectionBus = new ConnectionBus();
    UserSearchBus userSearchBus = new UserSearchBus();
    HealthBus healthBus = new HealthBus();
    OrderBus orderBus = new OrderBus();
    ConsultRecipient consultDoctor = new ConsultDoctor();
    OrgBus orgBus = new OrgBus();

    @And("^I order prescription to followed org name \"([^\"]*)\"$")
    public void iOrderPrescriptionToFollowedOrg(String name, List<MedicalOrderMS> list) {
        orgSig = connectionBus.loadFollowOrgsByName(name).get(0).getOrgSig();
        list.forEach(info -> {
            info.setUserSig(userSig);
            info.setOrgSig(orgSig);
            orderBus.medicineOrder(info);
        });
    }

    @And("^I order prescription to org name \"([^\"]*)\"$")
    public void iOrderPrescriptionToOrg(String name, List<MedicalOrderMS> list) {
        orgSig = userSearchBus.loadOrgsByName(name).get(0).getSig();
        list.forEach(info -> {
            info.setUserSig(userSig);
            info.setOrgSig(orgSig);
            orderBus.medicineOrder(info);
        });
    }

    @And("^I order (ImmunizationRecord|Prescription|LabResult|Insurance|IdCard|Others|ClinicVisit) with description \"([^\"]*)\" to org name \"([^\"]*)\"$")
    public void iOrderPrescriptionToOrg(String medicalType, String description, String name, List<MedicalOrderMS> list) {
        HealthDocMS doc = HealthDocMS.builder().userSig(userSig).description(description).build();
        doc.setMedicalType(medicalType);
        docSig = healthBus.loadHealthDocs(doc).get(0).getDocSig();
        orgSig = userSearchBus.loadOrgsByName(name).get(0).getSig();
        list.forEach(info -> {
            info.setOrgSig(orgSig);
            info.setUserSig(userSig);
            info.setDocSigs(docSig);
            orderBus.medicineOrder(info);
        });
    }

    // predicate to filter the duplicates by the given key extractor.
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> uniqueMap = new ConcurrentHashMap<>();
        return t -> uniqueMap.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    @Given("^As user, I load orders$")
    public void userLoadOrders(OrderMS body) {
        orderBus.orders(body);
    }

    @Given("^As (staff|manager), I load orders$")
    public void orgLoadOrders(String role, OrderMS body) {
        body.setOrgSig(orgSig);
        orderBus.orders(body);
    }

    @Given("^As org, I view orders process$")
    public void orgLoadOrdersProcess(OrderMS body) {
        body.setOrgSig(orgSig);
        orderBus.listOrderProcess(body);
    }


    @Given("^As user, I load order detail$")
    public void userLoadOrderDetail(OrderMS body) {
        orderBus.orderDetail(body);
    }

    @And("As user, I add message into below order with content {string}")
    public void asUserIAddMessageIntoOrderWithContent(String msg, OrderMS body) {
        orderBus.orderDetail(body);
    }


    @Given("^As manager, I load order detail$")
    public void orgLoadOrderDetail(OrderMS body) {
        body.setOrgSig(orgSig);
        orderBus.orderDetail(body);
    }

    @Given("^As org, I update medical test for name \"([^\"]*)\"$")
    public void orgUpdateOrders(String displayName, List<OrderMS> list) {
        list.forEach(info -> {
            OrderMS oldOrder = OrderMS.builder()
                    .orgSig(orgSig)
                    //.displayName(displayName)
                    .orderType(OrderType.MedicalTest.value())
                    .build();
            orderBus.orderProcess(oldOrder, info);
        });
    }

    @Given("^As user, I update medical test for name \"([^\"]*)\"$")
    public void userUpdateOrders(String displayName, List<OrderMS> list) {
        list.forEach(info -> {
            OrderMS oldOrder = OrderMS.builder()
                    //.displayName(displayName)
                    .orderType(OrderType.MedicalTest.value())
                    .isDoctorIndicated(true)
                    .build();
            orderBus.orderProcess(oldOrder, info);
        });
    }

    @Given("^As org, I upload medical test for name \"([^\"]*)\"$")
    public void orgUploadOrders(String displayName, List<OrderMS> list) {
        list.forEach(info -> {
            OrderMS oldOrder = OrderMS.builder()
                    .orgSig(orgSig)
                    //.displayName(displayName)
                    .orderType(OrderType.MedicalTest.value())
                    .build();
            orderBus.orderAttachFile(oldOrder, info);
        });

    }


    @Given("^I get totals doctor indicated$")
    public void Igettotalsdoctorindicated() {
        orderBus.countDoctorIndicated();
    }


    @And("I load org that has product")
    public void iLoadOrgThatHasProduct(MedicalTestMS body) {
        orderBus.loadOrgHasProduct(body);
    }

    private void newMedicineTest(MedicalTestMS info) {
        info.setPatientSig(userSig);
        info.setProductServiceType(ProductServiceType.MedicalTest.value());
        orgSig = orderBus.loadOrgHasProduct(info).get(0).getSig();
        if (orgSig != null) {
            List<ProductServicesItem> productServices = productBus.loadProductMedicalTest(orgSig)
                    .getProductServices(info.getProductAndService());

            info.setOrgSig(orgSig);
            info.setProductServices(productServices);
        }
        orderBus.newMedicineTestOrder(info);
    }

    @And("As user, I register medical test")
    public void iRegisterForMedicalTestOfOrg(List<MedicalTestMS> list) {
        list.forEach(info -> {
            info.setDoctorIndicated(false);
            newMedicineTest(info);
        });
    }


    @And("^As user, I update suggested medical test$")
    public void iUpdateForSuggestedMedicalTest(List<MedicalTestMS> list) {
        String orderSig = orderBus.orders(OrderMS.builder().isDoctorIndicated(true).build()).get(0).getOrderSig();
        list.forEach(info -> {
            info.setDoctorIndicated(false);
            info.setOrderSig(orderSig);
            newMedicineTest(info);
        });
    }


    @And("^As doctor, I suggest medical test for question \"([^\"]*)\"$")
    public void doctorSuggestForMedicalTestOfOrg(String content, List<MedicalTestMS> list) {
        list.forEach(info -> {
            info.setPostSig(consultDoctor.getPostSigOfQuestionContent(content));
            info.setDoctorIndicated(true);
            newMedicineTest(info);
        });
    }


    @And("I assign staff name {string} to take sample of patient name {string}")
    public void iAssignStaffToTakeSample(String staffName, String patientName) {
        StaffMS staffMS = new StaffMS();
        staffMS.setOrgSig(orgSig);
        staffMS.setDisplayName(staffName);
        staffMS.setIsAdminLoad(true);
        staffMS.setFeatureAttributes(OrgFeatureAttributes.MedicalTestTakenSample);
        String userSig = orgBus.loadStaffs(staffMS).get(0).getUserSignature();

        String orderSig = orderBus.orders(orgSig).stream()
                .filter(o -> o.getPatientInfo().getPatientName().equalsIgnoreCase(patientName))
                .map(o->o.getOrderSig())
                .findFirst().orElse(null);

        OrderAssignTakenSampleRQ body = new OrderAssignTakenSampleRQ();
        body.setOrgSig(orgSig);
        body.setUserSig(userSig);
        body.setOrderSig(orderSig);
        orderBus.orderAssignTakenSample(body);
    }
}
