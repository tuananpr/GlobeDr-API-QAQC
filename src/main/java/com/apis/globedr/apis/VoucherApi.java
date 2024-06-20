package com.apis.globedr.apis;

import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.VoucherSig;
import com.apis.globedr.model.request.review.*;
import com.apis.globedr.model.response.voucher.BuyVoucherRS;
import com.apis.globedr.model.response.voucher.LoadVouchersRS;
import com.apis.globedr.model.response.voucher.VoucherCategoryRS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoucherApi extends BaseApi {


    private VoucherApi() {
    }

    private static VoucherApi instant;

    public static VoucherApi getInstant() {
        if (instant == null) instant = new VoucherApi();
        return instant;
    }

    public void loadVouchers() {
        Map<String, Object> body = new HashMap<>();
        loadVouchers(body);
    }

    public void loadVouchers(Map<String, Object> body) {
        body.put(Text.PAGE, null);
        body.put(Text.PAGE_SIZE, null);
        RestCore.given().url(API.Voucher.LOAD_VOUCHERS()).auth(token)
                .body(body, LoadVouchersRQ.class).post().send();
    }


    public List<VoucherCategoryRS> getCategories(Object body) {
        return RestCore.given().url(API.Voucher.GET_CATEGORIES())
                .auth(token).params(body, GetCategoriesRQ.class).get().send()
                .extractAsModels("data.list", VoucherCategoryRS.class);
    }


    public List<LoadVouchersRS> loadVouchers(Object body) {
        return RestCore.given().url(API.Voucher.LOAD_VOUCHERS()).auth(token)
                .body(body, LoadVouchersRQ.class).post().send()
                .extractAsModels("data.list", LoadVouchersRS.class);
    }


    public List<LoadVouchersRS> loadVouchersAllowAnonymous(Object body) {
        return RestCore.given()
                .url(API.Voucher.LOAD_VOUCHERS_ALLOW_ANONYMOUS())
                .body(body, LoadVouchersAllowAnonymousRQ.class)
                .post().send()
                .extractAsModels("data.list", LoadVouchersRS.class);

    }


    public Response countMyVoucher() {
        return RestCore.given().url(API.Voucher.COUNT_MY_VOUCHER()).auth(token).get().send();

    }


    public Response getReview(Object body) {
        return RestCore.given().url(API.Voucher.GET_REVIEW()).auth(token)
                .params(body, GetReviewRQ.class).get().send();
    }

    public Response review(Object body) {
        return RestCore.given().url(API.Voucher.REVIEW()).auth(token)
                .body(body, ReviewRQ.class).post().send();
    }


    public Response useVoucher(Object body) {
        return RestCore.given().url(API.Voucher.USE_VOUCHER()).auth(token)
                .body(body, UseVoucherRQ.class).post().send();
    }

    public Response getVoucher(String voucherSig) {
        return RestCore.given().url(API.Voucher.GET_VOUCHER()).auth(token)
                .params(new VoucherSig(voucherSig)).get().send();
    }

    public Response clickVoucherLink(Object body) {
        return RestCore.given().url(API.Voucher.CLICK_VOUCHER_LINK()).auth(token).body(body, ClickVoucherLinkRQ.class).put().send();
    }

    public BuyVoucherRS buyVoucher(String voucherSig) {
        return RestCore.given().url(API.Voucher.BUY_VOUCHER()).auth(token)
                .params(new VoucherSig(voucherSig)).get().send()
                .extractAsModel("data", BuyVoucherRS.class);
    }

    public List<LoadVouchersRS> getMyVouchers() {
        return RestCore.given().url(API.Voucher.GET_MY_VOUCHERS()).auth(token)
                .params(new Page(), Page.class).get().send()
                .extractAsModels("data.list", LoadVouchersRS.class);
    }


}
