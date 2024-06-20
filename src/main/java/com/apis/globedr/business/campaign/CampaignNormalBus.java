package com.apis.globedr.business.campaign;

import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;


public class CampaignNormalBus extends CampaignAttachmentBus {


    @Override
    protected void validation(ChatMS body) {

    }


    @Override
    protected SendMessageRS sendAttachmentFile(ChatMS body) {
        return null;
    }


    public SendMessageRS send(ChatMS body) {
        body = prepare(body);
        return sendCampaign(body);
    }

}
