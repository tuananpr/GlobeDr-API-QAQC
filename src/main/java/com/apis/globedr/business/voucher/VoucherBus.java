package com.apis.globedr.business.voucher;

import com.apis.globedr.apis.VoucherApi;
import com.apis.globedr.apis.VoucherManagerApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.model.request.review.GetReviewRQ;
import com.apis.globedr.model.request.review.UseVoucherRQ;
import com.apis.globedr.model.response.voucher.BuyVoucherRS;
import com.apis.globedr.model.response.voucher.LoadVouchersRS;
import com.apis.globedr.model.response.voucher.ManagerLoadVouchersRS;
import com.apis.globedr.model.response.voucher.VoucherCategoryRS;
import com.apis.globedr.model.step.review.ReviewMS;
import com.apis.globedr.model.step.voucher.UseVoucherMS;
import com.apis.globedr.model.step.voucher.VoucherCategoryMS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.rest.core.response.Response;

import java.util.List;

public abstract class VoucherBus extends AbsBus {
    protected VoucherManagerApi voucherManagerApi = VoucherManagerApi.getInstant();
    protected VoucherApi voucherApi = VoucherApi.getInstant();


    public Response review(ReviewMS body) {
        return voucherApi.review(body);
    }

    public Response countMyVoucher() {
        return voucherApi.countMyVoucher();
    }

    public Response getReview(VoucherMS body) {
        return voucherApi.getReview(body);
    }

    public String getVoucherSig(Object body) {
        return loadVouchers(body).stream().map(v -> v.getVoucher().getSignature()).findFirst().orElse(null);
    }

    public String getVoucherSigByName(String name) {
        VoucherMS body = VoucherMS.builder()
                .name(name)
                .build();
        return loadVouchers(body).stream().map(v -> v.getVoucher().getSignature()).findFirst().orElse(null);
    }


    public abstract List<VoucherCategoryRS> loadCategories(VoucherCategoryMS info);

    public abstract List<ManagerLoadVouchersRS> loadVouchersByManage(VoucherMS body);

    public abstract List<LoadVouchersRS> loadVouchers(Object body);

    public abstract Response getVoucher(VoucherMS body);

    public abstract BuyVoucherRS buyVoucher(VoucherMS body);

    public Response useVoucher(VoucherMS existedVoucher, UseVoucherMS withInfo){
        String cardSig = voucherApi.buyVoucher( getVoucherSig(existedVoucher)).getCardSig();
        withInfo.setCardSig(cardSig);
        return voucherApi.useVoucher(withInfo);
    }

    public abstract Response openlinkVoucher(VoucherMS body);

    public abstract List<LoadVouchersRS> loadMyWallet();

}