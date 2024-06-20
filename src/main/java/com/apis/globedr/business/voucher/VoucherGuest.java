package com.apis.globedr.business.voucher;

import com.apis.globedr.model.response.voucher.BuyVoucherRS;
import com.apis.globedr.model.response.voucher.LoadVouchersRS;
import com.apis.globedr.model.response.voucher.ManagerLoadVouchersRS;
import com.apis.globedr.model.response.voucher.VoucherCategoryRS;
import com.apis.globedr.model.step.voucher.UseVoucherMS;
import com.apis.globedr.model.step.voucher.VoucherCategoryMS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.rest.core.response.Response;

import java.util.List;

public class VoucherGuest extends VoucherBus{
    @Override
    public List<VoucherCategoryRS> loadCategories(VoucherCategoryMS info) {
        return null;
    }

    @Override
    public List<ManagerLoadVouchersRS> loadVouchersByManage(VoucherMS body) {
        return null;
    }

    @Override
    public List<LoadVouchersRS> loadVouchers(Object body) {
        return voucherApi.loadVouchersAllowAnonymous(body);
    }

    @Override
    public Response getVoucher(VoucherMS body) {
        return null;
    }

    @Override
    public BuyVoucherRS buyVoucher(VoucherMS body) {
        return null;
    }

    @Override
    public Response openlinkVoucher(VoucherMS body) {
        return null;
    }

    @Override
    public List<LoadVouchersRS> loadMyWallet() {
        return null;
    }
}
