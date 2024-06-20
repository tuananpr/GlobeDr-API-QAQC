package com.apis.globedr.business.campaign;

import com.apis.globedr.enums.ChatMsgType;
import com.apis.globedr.apis.ChatApi;
import com.apis.globedr.apis.ForumManagerApi;
import com.apis.globedr.model.response.chat.CampaignDetailsRS;
import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;


public class CampaignArticleBus extends AbsCampaign {


    @Override
    protected void validation(ChatMS body) {
    }

    @Override
    protected SendMessageRS sendAttachmentFile(ChatMS body) {
        return null;
    }

    @Override
    protected SendMessageRS sendCampaign(ChatMS body) {
        String fileName = body.getAttachmentFileName();
        String articleSig = ForumManagerApi.getInstant().loadCategoryWithPost(0)
                .stream()
                .flatMap(
                        listCategory->listCategory.getList()
                                .stream()
                                .filter(p->p.getTitle().equalsIgnoreCase(fileName))
                                .map(p->p.getPostSignature())
                ).findFirst().orElse(null);

        body.setConversationName(body.getAttachmentFileName());
        body.setMsg(articleSig);
        body.setMsgType(ChatMsgType.PostForum.value());

        return chatApi.addMessage(body);
    }

    public SendMessageRS send(ChatMS body) {
        body = prepare(body);
        SendMessageRS absMsg = sendCampaign(body);
        return absMsg;
    }



}
