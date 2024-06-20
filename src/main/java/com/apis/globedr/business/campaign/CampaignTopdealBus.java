package com.apis.globedr.business.campaign;

import com.apis.globedr.business.topdeal.TopDealBus;
import com.apis.globedr.enums.ChatMsgType;
import com.apis.globedr.apis.ChatApi;
import com.apis.globedr.apis.TopDealApi;
import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.apis.globedr.model.step.topdeal.PromotionMS;
import org.junit.Assert;


public class CampaignTopdealBus extends CampaignAttachmentBus {
    TopDealBus topDealBus = new TopDealBus();

    @Override
    protected void validation(ChatMS body) {
        super.validation(body);
        if (body.getAttachmentFileName() == null)
            Assert.fail("Please set attachmentFileName to send campaign As voucher name, topdeal name, article name");
    }


    @Override
    protected SendMessageRS sendAttachmentFile(ChatMS body) {
        String topDealSig = topDealBus.loadTopDeals(body.getAttachmentFileName(), body.getSenderSig()).get(0).getTopDealSig();
        String promotionSig = topDealBus.loadPromotions(PromotionMS.builder().topDealSig(topDealSig).build()).get(0).getPromotionSig();

        body = prepareAttachmentFile(body);
        body.setMsg(promotionSig);
        body.setMsgType(ChatMsgType.TopDeal.value());

        return chatApi.sendMessage(body);
    }


    public SendMessageRS send(ChatMS body) {
        body = prepare(body);
        SendMessageRS absMsg = sendCampaign(body);
        sendAttachmentFile(body);
        return absMsg;
    }
}
