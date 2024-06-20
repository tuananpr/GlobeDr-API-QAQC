package com.apis.globedr.business.chat;

import com.apis.globedr.enums.ChatMsgType;
import com.apis.globedr.enums.ChatType;
import com.apis.globedr.enums.PageDashboard;
import com.apis.globedr.enums.SenderType;
import com.apis.globedr.model.general.Recipient;
import com.apis.globedr.model.request.chat.FindConversationRQ;
import com.apis.globedr.model.response.chat.ConversationRS;
import com.apis.globedr.model.response.chat.LoadMsgsRS;
import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;

import java.util.List;

public abstract class AbsOrgChatBus extends AbsChatBus {


    public ConversationRS findConversation(String ownerSig, List<Recipient> recipients, Integer chatType) {
        FindConversationRQ body = new FindConversationRQ();
        body.setFindExisted(true);
        body.setType(chatType);
        body.setOwnerSig(ownerSig);
        body.setOwnerType(SenderType.Org.value());
        body.setRecipients(recipients);
        return chatApi.findConversation(body);
    }


    @Override
    public ConversationRS findConversation(String ownerSig, List<Recipient> recipients, ChatType chatType) {
        return findConversation(ownerSig, recipients, chatType.value());
    }

    @Override
    public ChatMS beforeLoadConversation(ChatMS body) {
        body.setViewerType(SenderType.Org.value());
        body.setViewerSig(body.getOrgSig());
        return body;
    }

    @Override
    public SendMessageRS send(ChatMS body) {
        body.setMsgType(ChatMsgType.Msg.value());
        body.setSenderType(SenderType.Org.value());
        return chatApi.sendMessage(body);
    }

    @Override
    public List<ConversationRS> loads(ChatMS body) {
        body.setPageDashboard(PageDashboard.PageMedicalBussiness);
        return chatApi.loadConversations(body);
    }

}
