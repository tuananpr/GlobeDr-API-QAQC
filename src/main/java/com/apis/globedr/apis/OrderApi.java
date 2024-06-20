package com.apis.globedr.apis;


import com.apis.globedr.model.general.City;
import com.apis.globedr.model.general.Country;
import com.apis.globedr.model.general.District;
import com.apis.globedr.model.request.medicalTest.NewMedicineTestOrderRQ;
import com.apis.globedr.model.request.order.*;
import com.apis.globedr.model.request.medicalTest.ProductServiceOrgsRQ;
import com.apis.globedr.model.response.medicalTest.OrderDetailRS;
import com.apis.globedr.model.response.medicalTest.ProductServiceOrgsRS;
import com.apis.globedr.model.response.order.OrderRS;
import com.apis.globedr.model.response.order.MedicalOrgRS;
import com.apis.globedr.model.step.order.MedicalOrderMS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import io.restassured.RestAssured;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderApi extends BaseApi {

    private OrderApi() {
    }

    private static OrderApi instant;

    public static OrderApi getInstant() {
        if (instant == null) instant = new OrderApi();
        return instant;
    }

    public Response orderProcess(Object body) {
        return RestCore.given().url(API.Order.ORDER_PROCESS()).auth(token).bodyEncrypt(body, OrderProcessRQ.class).put().send();
    }


    public Response orderAttachFile(Object body) {
        return RestCore.given().url(API.Order.ORDER_ATTACH_FILE()).auth(token).multipart(body, OrderAttachFileRQ.class).post().send();
    }


    public Response newMedicineTestOrder(Object body) {
        return RestCore.given().url(API.Order.NEW_MEDICINE_TEST_ORDER()).auth(token)
                .bodyEncrypt(body, NewMedicineTestOrderRQ.class).post().send();
    }


    public Response listOrderProcess(Object body) {
        return RestCore.given().url(API.Order.LIST_ORDER_PROCESS()).auth(token)
                .bodyEncrypt(body, ListOrderProcess.class).post().send();
    }

    public OrderDetailRS orderDetail(Object body) {
        return RestCore.given().url(API.Order.ORDER_DETAIL()).auth(token)
                .bodyEncrypt(body, OrderDetailRQ.class).post().send()
                .extractAsModel("data.info", OrderDetailRS.class);
    }

    public Response countDoctorIndicated() {
        return RestCore.given().url(API.Order.COUNT_DOCTOR_INDICATED()).auth(token).bodyEncrypt(new HashMap<>()).get().send();
    }

    public List<OrderRS> orders(Object body) {
        return RestCore.given().url(API.Order.ORDERS()).auth(token).bodyEncrypt(body, OrderRQ.class)
                .post().send().extractAsModels("data.list", OrderRS.class);
    }

    public MedicalOrgRS medicineOrder(MedicalOrderMS body) {
        Map<String, Object> request = new HashMap<>();
        if (body.getFiles() != null) request.put("files", body.getFiles());
        if (body.getCountry() != null) request.put("country[Country]", body.getCountry().getCountry());
        if (body.getCity() != null) request.put("city[Code]", body.getCity().getCode());
        if (body.getDistrict() != null) request.put("district[Code]", body.getDistrict().getCode());
        if (body.getWard() != null) request.put("ward[Code]", body.getWard().getCode());
        if (body.getPhoneNumber() != null) request.put("phoneNumber", body.getPhoneNumber());
        if (body.getAddress() != null) request.put("address", body.getAddress());
        if (body.getAdditionalItems() != null) request.put("additionalItems", body.getAdditionalItems());
        if (body.getDeliveryType() != null)  request.put("deliveryType", body.getDeliveryType());
        if (body.getDocSigs() != null)  request.put("docSigs", body.getDocSigs());
        request.put("isAttached", body.isAttached());
        request.put("userSig", body.getUserSig());
        request.put("orgSig", body.getOrgSig());
        return RestCore.given().url(API.Order.MEDICINE_ORDER()).auth(token).multipart(request).post().send()
                .extractAsModel("data.info", MedicalOrgRS.class);
    }


    public List<ProductServiceOrgsRS> productServiceOrgs(Object body) {
        return RestCore.given().url(API.Order.PRODUCT_SERVICE_ORGS()).auth(token)
                .bodyEncrypt(body, ProductServiceOrgsRQ.class)
                .post().send()
                .extractAsModels("data.list", ProductServiceOrgsRS.class);
    }

    public Response newProductOrder(Object body) {
        return RestCore.given().url(API.Order.NEW_PRODUCT_ORDER()).auth(token).bodyEncrypt(body, NewProductOrderRQ.class).post().send();
    }

    public Response orderAssignTakenSample(Object body) {
        return RestCore.given().url(API.Order.ORDER_ASSIGN_TAKEN_SAMPLE()).auth(token).bodyEncrypt(body, OrderAssignTakenSampleRQ.class).post().send();
    }


}