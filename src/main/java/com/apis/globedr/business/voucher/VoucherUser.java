package com.apis.globedr.business.voucher;

import com.apis.globedr.model.response.voucher.BuyVoucherRS;
import com.apis.globedr.model.response.voucher.LoadVouchersRS;
import com.apis.globedr.model.response.voucher.ManagerLoadVouchersRS;
import com.apis.globedr.model.response.voucher.VoucherCategoryRS;
import com.apis.globedr.model.step.voucher.VoucherCategoryMS;
import com.apis.globedr.model.step.voucher.VoucherMS;
import com.rest.core.response.Response;

import java.util.List;

public class VoucherUser extends VoucherSignedIn {
    @Override
    public List<VoucherCategoryRS> loadCategories(VoucherCategoryMS info) {
        return voucherApi.getCategories(info);
    }

    @Override
    public List<ManagerLoadVouchersRS> loadVouchersByManage(VoucherMS body) {
        return null;
    }




}
