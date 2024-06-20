package com.apis.globedr.business.campaign;

import com.apis.globedr.apis.VoucherManagerApi;
import com.apis.globedr.enums.ChatMsgType;
import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.apis.globedr.model.step.voucher.VoucherMS;

import org.junit.Assert;



public class CampaignVoucherBus extends CampaignAttachmentBus {


    @Override
    protected void validation(ChatMS body) {
        super.validation(body);
        if (body.getAttachmentFileName() == null)
            Assert.fail("Please set attachmentFileName to send campaign As voucher name, topdeal name, article name");
    }

    @Override
    protected SendMessageRS sendAttachmentFile(ChatMS body) {
        VoucherMS loadVoucher = VoucherMS
                .builder()
                .name(body.getAttachmentFileName())
                .orgSig(body.getSenderSig()) //  SenderSig is orgSig
                .isAdminLoad(true)
                .build();
        String voucherSig = VoucherManagerApi.getInstant().loadVouchers(loadVoucher).stream()
                .map(v -> v.getVoucherSig()).findFirst().orElse(null);

        body = prepareAttachmentFile(body);
        body.setMsg(voucherSig);
        body.setMsgType(ChatMsgType.Voucher.value());

        return chatApi.sendMessage(body);
    }

    public SendMessageRS send(ChatMS body) {
        body = prepare(body);
        SendMessageRS absMsg = sendCampaign(body);
        sendAttachmentFile(body);
        return absMsg;
    }


}
