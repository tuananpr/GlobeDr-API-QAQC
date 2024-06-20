package com.apis.globedr.business.changelog;

import com.apis.globedr.apis.VoucherManagerApi;
import com.apis.globedr.enums.ChangeLogLinkType;
import com.apis.globedr.model.step.changelog.ChangelogMS;
import com.apis.globedr.model.step.voucher.VoucherMS;

public class ChangelogVoucherBus extends ChangelogBus {
    @Override
    protected ChangelogMS prepare(ChangelogMS body){
        VoucherMS loadVoucher = VoucherMS
                .builder()
                .name(body.getLinkName())
                .orgSig(body.getOrgSig())
                .isAdminLoad(true)
                .build();
        Integer id = VoucherManagerApi.getInstant().loadVouchers(loadVoucher).stream()
                .map(v -> v.getVoucherId()).findFirst().orElse(null);

        body.setLinkId(id);
        body.setLinkName(null);
        body.setLinkType(ChangeLogLinkType.Voucher.value());
        return body;
    }
}
