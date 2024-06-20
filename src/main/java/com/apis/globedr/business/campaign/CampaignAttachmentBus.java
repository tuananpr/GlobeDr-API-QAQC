package com.apis.globedr.business.campaign;

import com.apis.globedr.enums.ChatMsgType;
import com.apis.globedr.enums.SenderType;
import com.apis.globedr.apis.ChatApi;
import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;
import org.junit.Assert;



public abstract class CampaignAttachmentBus extends AbsCampaign {



    @Override
    protected void validation(ChatMS body) {
        if (body.getSenderSig() == null) Assert.fail("Please set SenderSig to send campaign");
        if (body.getGroup() == null) Assert.fail("Please set Group to send campaign");
        if (body.getConversationName() == null) Assert.fail("Please set conversationName to send campaign");

    }

    protected ChatMS prepareAttachmentFile(ChatMS body) {
        return ChatMS.builder()
                .senderSig(body.getSenderSig())
                .senderType(SenderType.Org.value())
                .conversationSig(body.getConversationSig())
                .attachByMsgSig(body.getMsgSig())
                .build();
    }


    @Override
    protected SendMessageRS sendCampaign(ChatMS body) {
        body.setMsgType(ChatMsgType.Campaign.value());
        SendMessageRS msg = chatApi.addMessage(body);
        body.setConversationSig(msg.getConversationSig());
        return msg;
    }



}
