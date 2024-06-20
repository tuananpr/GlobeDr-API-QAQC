package com.apis.globedr.business.voucher;

import com.apis.globedr.model.response.voucher.LoadVouchersRS;
import com.apis.globedr.model.response.voucher.ManagerLoadVouchersRS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.rest.core.response.Response;

import java.util.Arrays;
import java.util.List;

public class VoucherOrg extends VoucherManagerBus{

    public List<ManagerLoadVouchersRS> loadVouchersByManage(VoucherMS body) {
        body.setLoadMyVoucher(true);
        return voucherManagerApi.loadVouchers(body);
    }

    @Override
    public Response approveVoucher(VoucherMS body) {
        return null;
    }

    @Override
    public Response deActiveVoucher(VoucherMS body) {
        return null;
    }
}
