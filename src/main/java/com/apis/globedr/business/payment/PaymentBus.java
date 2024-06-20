package com.apis.globedr.business.payment;

import com.apis.globedr.apis.PaymentApi;
import com.apis.globedr.model.request.payment.PaymentProductRQ;
import com.apis.globedr.model.request.payment.PayooOrderRQ;
import com.apis.globedr.model.response.product.ProductRS;
import com.apis.globedr.model.response.product.Shops;
import com.apis.globedr.model.step.order.OrderMS;
import com.rest.core.response.Response;

import java.util.List;


public class PaymentBus {
    private PaymentApi paymentApi = PaymentApi.getInstant();

    public ProductRS product(PaymentProductRQ info){
        return paymentApi.product(info);
    }

    public Response payooOrder(PayooOrderRQ info){
        return paymentApi.payooOrder(info);
    }

}
