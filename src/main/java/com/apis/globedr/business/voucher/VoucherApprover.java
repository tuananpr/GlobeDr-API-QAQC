package com.apis.globedr.business.voucher;

import com.apis.globedr.model.response.voucher.BuyVoucherRS;
import com.apis.globedr.model.response.voucher.LoadVouchersRS;
import com.apis.globedr.model.response.voucher.ManagerLoadVouchersRS;
import com.apis.globedr.model.step.voucher.UseVoucherMS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.rest.core.response.Response;

import java.util.List;

public class VoucherApprover extends VoucherManagerBus{


    public Response approveVoucher(VoucherMS body) {
        body.setVoucherSig(getVoucherSig(body));
        return voucherManagerApi.approveVoucher(body);
    }

    public Response deActiveVoucher(VoucherMS body) {
        body.setVoucherSig(getVoucherSig(body));
        return voucherManagerApi.deActiveVoucher(body);
    }

    @Override
    public List<ManagerLoadVouchersRS> loadVouchersByManage(VoucherMS body) {
        body.setLoadMyVoucher(false);
        return voucherManagerApi.loadVouchers(body);
    }




}
