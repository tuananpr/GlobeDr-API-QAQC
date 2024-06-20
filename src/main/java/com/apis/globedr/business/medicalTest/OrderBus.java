package com.apis.globedr.business.medicalTest;

import com.apis.globedr.apis.OrderApi;
import com.apis.globedr.constant.Text;
import com.apis.globedr.enums.DeliveryType;
import com.apis.globedr.helper.Common;
import com.apis.globedr.model.general.ProductServicesItem;
import com.apis.globedr.model.request.order.NewProductOrderRQ;
import com.apis.globedr.model.request.order.OrderAssignTakenSampleRQ;
import com.apis.globedr.model.request.product.ProductServiceSig;
import com.apis.globedr.model.response.account.SignInRS;
import com.apis.globedr.model.response.medicalTest.OrderDetailRS;
import com.apis.globedr.model.response.medicalTest.ProductServiceOrgsRS;
import com.apis.globedr.model.response.order.MedicalOrgRS;
import com.apis.globedr.model.response.order.OrderRS;
import com.apis.globedr.model.response.product.ProductServicesRS;
import com.apis.globedr.model.step.medicalTest.MedicalTestMS;
import com.apis.globedr.model.step.order.MedicalOrderMS;
import com.apis.globedr.model.step.order.OrderMS;
import com.rest.core.response.Response;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OrderBus {

    OrderApi orderApi = OrderApi.getInstant();

    public List<ProductServiceOrgsRS> loadOrgHasProduct(MedicalTestMS body){
        return orderApi.productServiceOrgs(body).stream()
                .filter(body.getOrgName() != null ? o->o.getName().equalsIgnoreCase(body.getOrgName()) : o-> true )
                .collect(Collectors.toList());
    }

    public Response countDoctorIndicated() {
        return orderApi.countDoctorIndicated();
    }

    public Response newMedicineTestOrder(MedicalTestMS body) {
        return orderApi.newMedicineTestOrder(body);
    }

    public List<OrderRS> orders(OrderMS body) {
        return orderApi.orders(body);
    }

    public List<OrderRS> orders(String orgSig) {
        OrderMS orderMS = new OrderMS();
        orderMS.setOrgSig(orgSig);
        return orderApi.orders(orderMS);
    }

    public String getOrderSig(OrderMS body) {
        return orders(body).stream().map(o->o.getOrderSig()).findFirst().orElse(null);
    }

    public Response listOrderProcess(OrderMS body) {
        OrderRS rs = orders(body).get(0);
        body.setOrderSig(rs.getOrderSig());
        return orderApi.listOrderProcess(body);
    }


    public Response orderProcess(OrderMS existed, OrderMS info) {
        OrderRS old = orders(existed).get(0);
        info.setOrderSig(old.getOrderSig());
        info.setOrgSig(existed.getOrgSig());
        return orderApi.orderProcess(info);
    }


    public OrderDetailRS orderDetail(OrderMS body) {
        OrderRS rs = orders(body).get(0);
        body.setOrderSig(rs.getOrderSig());
        return orderApi.orderDetail(body);
    }

    public OrderDetailRS orderDetail(String orderSig) {
        return orderApi.orderDetail(OrderMS.builder().orderSig(orderSig).build());
    }

    public Response orderAttachFile(OrderMS existed, OrderMS info) {
        OrderRS old = orders(existed).get(0);
        info.setOrderSig(old.getOrderSig());
        info.setOrgSig(existed.getOrgSig());
        return orderApi.orderAttachFile(info);
    }


    public MedicalOrgRS medicineOrder(MedicalOrderMS info) {
        return orderApi.medicineOrder(info);
    }

    public Response newProductOrder(OrderMS orderInfo, ProductServicesRS info, SignInRS user) {
        NewProductOrderRQ body = new NewProductOrderRQ();
        body.setOrgSig(info.getOrgSig());
        body.setPaymentType(info.getPaymentTypes());
        body.setProductServices(Arrays.asList(new ProductServiceSig(info.getProductSig())));
        body.setOnDate(Common.today(Text.FTIME_FULL));
        body.setReceiverName(user.getDisplayName());
        body.setPhone(user.getPhone());

        body.setDeliveryType(orderInfo.getDeliveryType());
        body.setAddress(orderInfo.getDeliveryAddress());
        body.setCountry(orderInfo.getCountry());
        body.setCity(orderInfo.getCity());
        body.setDistrict(orderInfo.getDistrict());
        body.setWard(orderInfo.getWard());
        return orderApi.newProductOrder(body);
    }

    public Response orderAssignTakenSample(OrderAssignTakenSampleRQ info) {
        return orderApi.orderAssignTakenSample(info);
    }

}
