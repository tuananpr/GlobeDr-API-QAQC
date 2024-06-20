package com.apis.globedr.business.campaign;

import com.apis.globedr.business.AbsBus;
import com.apis.globedr.enums.ChatType;
import com.apis.globedr.enums.RecipientType;
import com.apis.globedr.enums.SenderType;
import com.apis.globedr.model.general.Recipient;
import com.apis.globedr.apis.ChatApi;
import com.apis.globedr.apis.OrgMemberApi;
import com.apis.globedr.model.response.chat.CampaignDetailsRS;
import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.fasterxml.jackson.annotation.*;
import com.rest.core.response.Response;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.List;


public abstract class AbsSendMessage extends AbsBus {

    protected ChatApi chatApi = ChatApi.getInstant();

    protected abstract SendMessageRS sendAttachmentFile(ChatMS body);

    protected abstract SendMessageRS sendCampaign(ChatMS body);

    protected abstract void validation(ChatMS body);

    public ChatMS prepare(ChatMS body) {
        String msgSig = ChatApi.getInstant().createTempMessage();
        String recipientSig = OrgMemberApi.getInstant().loadOrgGroup(body.getSenderSig(), body.getGroup());

        body.setRecipients(Arrays.asList(new Recipient(RecipientType.Group.value(), recipientSig)));
        body.setMsgSig(msgSig);
        body.setSenderType(SenderType.Org.value());
        body.setConversationType(ChatType.Campaign.value());
        body.setFindExisted(false);
        return body;
    }


    public abstract SendMessageRS send(ChatMS body);

}
