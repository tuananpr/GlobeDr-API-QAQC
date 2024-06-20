package com.apis.globedr.business.voucher;

import com.apis.globedr.model.request.review.UseVoucherRQ;
import com.apis.globedr.model.response.voucher.BuyVoucherRS;
import com.apis.globedr.model.response.voucher.LoadVouchersRS;
import com.apis.globedr.model.step.voucher.UseVoucherMS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.rest.core.response.Response;

import java.util.List;

public abstract class VoucherSignedIn extends VoucherBus {


    @Override
    public Response getVoucher(VoucherMS body) {
        String voucherSig = getVoucherSig(body);
        return voucherApi.getVoucher(voucherSig);
    }

    @Override
    public BuyVoucherRS buyVoucher(VoucherMS body) {
        String voucherSig = getVoucherSig(body);
        return voucherApi.buyVoucher(voucherSig);
    }

    public List<LoadVouchersRS> loadMyWallet() {
        return voucherApi.getMyVouchers();
    }

    public List<LoadVouchersRS> loadVouchers(Object body) {
        return voucherApi.loadVouchers(body);
    }




    public Response openlinkVoucher(VoucherMS body) {
        String voucherSig = getVoucherSig(body);
        body.setVoucherSig(voucherSig);
        return voucherApi.clickVoucherLink(body);
    }


}
