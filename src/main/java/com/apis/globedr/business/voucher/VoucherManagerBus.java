package com.apis.globedr.business.voucher;

import com.apis.globedr.model.general.VoucherSig;
import com.apis.globedr.model.request.voucher.ExportClickUsersRQ;
import com.apis.globedr.model.request.voucher.LoadCategoriesRQ;
import com.apis.globedr.model.request.voucher.VoucherClickUsersRQ;
import com.apis.globedr.model.response.voucher.BuyVoucherRS;
import com.apis.globedr.model.response.voucher.LoadVouchersRS;
import com.apis.globedr.model.response.voucher.VoucherCategoryRS;
import com.apis.globedr.model.step.voucher.CardMS;
import com.apis.globedr.model.step.voucher.VoucherCategoryMS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.rest.core.response.Response;

import java.util.Arrays;
import java.util.List;

public abstract class VoucherManagerBus extends VoucherSignedIn{


    public Response createCategory(VoucherCategoryMS body) {
        return voucherManagerApi.createdVoucherCategory(body);
    }

    public Response editCategory(String oldCategoryName, VoucherCategoryMS newData) {
        String sig = loadCategories(oldCategoryName).getCategorySig();
        newData.setCategorySig(sig);
        return voucherManagerApi.editCategory(newData);
    }

    public Response deActiveCategory(VoucherCategoryMS body) {
        String sig = loadCategory(body).getCategorySig();
        return voucherManagerApi.deactiveCategory(sig);
    }

    public VoucherCategoryRS loadCategories(String name) {
        LoadCategoriesRQ request = LoadCategoriesRQ
                .builder()
                .name(name)
                .build();
        return voucherManagerApi.loadVoucherCategory(request).stream().findFirst().orElse(null);
    }

    public String getCategorySig(String name) {
        LoadCategoriesRQ request = LoadCategoriesRQ
                .builder()
                .name(name)
                .build();
        return voucherManagerApi.loadVoucherCategory(request).stream().map(c->c.getCategorySig()).findFirst().orElse(null);
    }

    public VoucherCategoryRS loadCategory(VoucherCategoryMS info) {
        return loadCategories(info).stream().findFirst().orElse(null);
    }


    public List<VoucherCategoryRS> loadCategories(VoucherCategoryMS info) {
        return voucherManagerApi.loadVoucherCategory(info);
    }

    public VoucherSig createVoucher(VoucherMS body) {
        return voucherManagerApi.createVoucher(body);
    }
    public Response addCard(String voucherName, CardMS body) {
        VoucherMS rLVoucher = VoucherMS.builder()
                .name(voucherName)
                .orgSig(body.getOrgSig())
                .build();
        body.setVoucherSig(getVoucherSig(rLVoucher));
        return voucherManagerApi.addCard(body);
    }

    public Response addAutoCard(String voucherName, CardMS body) {
        VoucherMS rLVoucher = VoucherMS.builder()
                .name(voucherName)
                .orgSig(body.getOrgSig())
                .build();
        body.setVoucherSig(getVoucherSig(rLVoucher));
        return voucherManagerApi.addAutoCard(body);
    }

    public String getVoucherSig(String voucherName) {
        VoucherMS body = VoucherMS.builder().name(voucherName).build();
        return getVoucherSig(body);
    }

    public String getVoucherSig(CardMS body) {
        VoucherMS request = mapping(body, VoucherMS.class);
        return this.loadVouchersByManage(request).stream().map(v->v.getVoucherSig()).findFirst().orElse(null);
    }

    public String getVoucherSig(VoucherMS body) {
        return this.loadVouchersByManage(body).stream().map(v->v.getVoucherSig()).findFirst().orElse(null);
    }


    public Response updateVoucherHotIcon(VoucherMS body) {
        body.setVoucherSig(getVoucherSig(body));
        return voucherManagerApi.updateVoucherHotIcon(body);
    }
    public Response updateVoucherIcon(VoucherMS body) {
        body.setVoucherSig(getVoucherSig(body));
        return voucherManagerApi.updateVoucherIcon(body);
    }

    public Response updateVoucher(VoucherMS oldVoucher, VoucherMS newData) {
        newData.setCategorySig(getCategorySig(oldVoucher.getCategoryName()));
        newData.setVoucherSig(getVoucherSig(oldVoucher));
        return voucherManagerApi.updateVoucher(newData);
    }

    public Response loadCards(CardMS body) {
        String voucherSig = getVoucherSig(body);
        body.setVoucherSigs(Arrays.asList(voucherSig));
        return voucherManagerApi.loadCards(body);
    }

    public Response setHotVoucher(VoucherMS body) {
        VoucherMS loadV = VoucherMS.builder()
                .orgSig(body.getOrgSig())
                .categoryName(body.getCategoryName())
                .name(body.getName())
                .build();
        body.setVoucherSig(getVoucherSig(loadV));
        return voucherManagerApi.setHotVoucher(body);
    }

    public Response setPopularVoucher(VoucherMS body) {
        VoucherMS loadV = VoucherMS.builder()
                .orgSig(body.getOrgSig())
                .categoryName(body.getCategoryName())
                .name(body.getName())
                .build();
        body.setVoucherSig(getVoucherSig(loadV));
        return voucherManagerApi.setPopularVoucher(body);
    }

    public Response voucherClickUsers(VoucherClickUsersRQ body) {
        return voucherManagerApi.voucherClickUsers(body);
    }
    public Response exportClickUsers(ExportClickUsersRQ body) {
        return voucherManagerApi.exportClickUsers(body);
    }



    public abstract Response approveVoucher(VoucherMS body);

    public abstract Response deActiveVoucher(VoucherMS body);
}
