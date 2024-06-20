package com.apis.globedr.apis;

import com.apis.globedr.constant.API;
import com.apis.globedr.model.request.payment.PaymentProductRQ;
import com.apis.globedr.model.request.payment.PayooOrderRQ;
import com.apis.globedr.model.response.product.ProductRS;
import com.apis.globedr.model.response.product.Shops;
import com.rest.core.RestCore;
import com.rest.core.response.Response;

import java.util.List;

public class PaymentApi extends BaseApi {

    private PaymentApi() {
    }

    private static PaymentApi instant;

    public static PaymentApi getInstant() {
        if (instant == null) instant = new PaymentApi();
        return instant;
    }

    public ProductRS product(Object body) {
        Response rs = RestCore.given().url(API.Payment.PRODUCT()).auth(token).bodyEncrypt(body, PaymentProductRQ.class).post().send();
        Shops orderInfo = rs.extractXmlAsModel("data.order.orderInfo", Shops.class);
        ProductRS productRS = rs.extractAsModel("data.order", ProductRS.class);
        productRS.setOrderInfo(orderInfo);
        return productRS;
    }

    public Response payooOrder(Object body) {
        return RestCore.given().url(API.Payment.PAYOO_ORDER()).auth(token).bodyEncrypt(body, PayooOrderRQ.class).post().send();
    }
}