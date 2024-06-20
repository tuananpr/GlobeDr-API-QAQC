package com.apis.globedr.apis;

import com.apis.globedr.helper.*;
import com.apis.globedr.model.general.CategorySig;
import com.apis.globedr.model.general.VoucherSig;
import com.apis.globedr.model.request.voucher.*;
import com.apis.globedr.model.response.voucher.ManagerLoadVouchersRS;
import com.apis.globedr.model.response.voucher.VoucherCategoryRS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.google.gson.JsonObject;

import java.util.*;

public class VoucherManagerApi extends BaseApi {


    private VoucherManagerApi() {
    }

    private static VoucherManagerApi instant;

    public static VoucherManagerApi getInstant() {
        if (instant == null) instant = new VoucherManagerApi();
        return instant;
    }


    public Response createdVoucherCategory(Object body) {
        return RestCore.given().url(API.VoucherManager.CREATE_CATEGORY()).auth(token)
                .body(body, CreateCategoryRQ.class).post().send();
    }

    public Response editCategory(Object body) {
        return RestCore.given().url(API.VoucherManager.UPDATE_CATEGORIES()).auth(token)
                .body(body, CreateCategoryRQ.class).put().send();
    }

    public VoucherSig createVoucher(Object body) {
        return RestCore.given().url(API.VoucherManager.CREATE_VOUCHER()).auth(token)
                .body(body, CreateVoucherRQ.class).post().send()
                .extractAsModel("data", VoucherSig.class);

    }


    public List<ManagerLoadVouchersRS> loadVouchers(Object body) {
        return RestCore.given().url(API.VoucherManager.LOAD_VOUCHER()).auth(token)
                .body(body, ManageLoadVouchersRQ.class).post().send()
                .extractAsModels("data.list", ManagerLoadVouchersRS.class);
    }


    public Response updateVoucher(Object body) {
        return RestCore.given().url(API.VoucherManager.UPDATE_VOUCHER()).auth(token).body(body, CreateVoucherRQ.class).put().send();
    }



    public Response addCard(Object body) {
        return RestCore.given().url(API.VoucherManager.ADD_CARDS()).auth(token).body(body, AddCardsRQ.class).post().send();
    }

    public Response addAutoCard(Object body) {
        return RestCore.given().url(API.VoucherManager.ADD_CARD_AUTO()).auth(token).body(body, AddCardAutoRQ.class).post().send();
    }



    public List<VoucherCategoryRS> loadVoucherCategory(Object body) {
        return RestCore.given().url(API.VoucherManager.LOAD_CATEGORIES())
                .auth(token).body(body, LoadCategoriesRQ.class).post().send()
                .extractAsModels("data.list", VoucherCategoryRS.class );
    }

    public Response updateVoucherHotIcon(Object body) {
        return RestCore.given().url(API.VoucherManager.UPDATE_VOUCHER_HOT_ICON()).auth(token)
                .multipart(body, UploadVoucherHotIconRQ.class).post().send();
    }

    public Response updateVoucherIcon(Object body) {
        return RestCore.given().url(API.VoucherManager.UPDATE_VOUCHER_ICON()).auth(token)
                .multipart(body, UploadVoucherHotIconRQ.class).post().send();

    }



    public Response deactiveCategory(String categorySig) {
        return RestCore.given().url(API.VoucherManager.DEACTIVE_CATEGORY()).auth(token).body(new CategorySig(categorySig)).delete().send();

    }

    public Response voucherClickUsers(Object body) {
        return RestCore.given().url(API.VoucherManager.VOUCHER_CLICK_USERS()).auth(token)
                .body(body, VoucherClickUsersRQ.class).post().send();
    }

    public Response exportClickUsers(Object body) {
        return RestCore.given().url(API.VoucherManager.EXPORT_CLICK_USERS()).auth(token)
                .body(body, ExportClickUsersRQ.class).post().send();
    }

    public Response setHotVoucher(Object body) {
        return RestCore.given().url(API.VoucherManager.SET_HOT_VOUCHER())
                .auth(token).body(body, SetHotVoucherRQ.class).put().send();
    }

    public Response setPopularVoucher(Object body) {
        return RestCore.given().url(API.VoucherManager.SET_POPULAR_VOUCHER())
                .auth(token).body(body, SetHotVoucherRQ.class).put().send();
    }

    public Response loadCards(Object body) {
        return RestCore.given().url(API.VoucherManager.LOAD_GIFT_CARDS()).auth(token)
                .body(body, LoadGiftCardsRQ.class).post().send();
    }

    public Response approveVoucher(Object body) {
        return RestCore.given().url(API.VoucherManager.APPROVE_VOUCHER()).auth(token).body(body, ApproveVoucherRQ.class).put().send();
    }

    public Response deActiveVoucher(Object body) {
        return RestCore.given().url(API.VoucherManager.DEACTIVE_VOUCHER()).auth(token).body(body, ApproveVoucherRQ.class).put().send();
    }


}
