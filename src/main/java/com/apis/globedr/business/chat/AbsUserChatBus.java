package com.apis.globedr.business.chat;

import com.apis.globedr.enums.ChatMsgType;
import com.apis.globedr.enums.ChatType;
import com.apis.globedr.enums.PageDashboard;
import com.apis.globedr.enums.SenderType;
import com.apis.globedr.model.general.Recipient;
import com.apis.globedr.model.request.chat.CreateConversationRQ;
import com.apis.globedr.model.request.chat.FindConversationRQ;
import com.apis.globedr.model.response.chat.ConversationRS;
import com.apis.globedr.model.response.chat.LoadMsgsRS;
import com.apis.globedr.model.response.chat.SendMessageRS;
import com.apis.globedr.model.step.chat.ChatMS;

import java.util.List;

public abstract class AbsUserChatBus extends AbsChatBus{



    @Override
    public List<LoadMsgsRS> loadMsgs(ChatMS body) {
        String conversationSig = loadConversation(body).getConversationSig();
        body.setConversationSig(conversationSig);
        return chatApi.loadMsgs(body);
    }

    @Override
    public ConversationRS findConversation(String ownerSig, List<Recipient> recipients, ChatType chatType) {
        FindConversationRQ body = new FindConversationRQ();
        body.setFindExisted(true);
        body.setType(chatType.value());
        body.setOwnerType(SenderType.User.value());
        body.setOwnerSig(ownerSig);
        body.setRecipients(recipients);
        return chatApi.findConversation(body);
    }

    @Override
    public ChatMS beforeLoadConversation(ChatMS body) {
        body.setViewerType(SenderType.User.value());
        return body;
    }

    public ConversationRS createConversation(List<Recipient> recipients, ChatType chatType) {
        CreateConversationRQ body = new CreateConversationRQ();
        body.setOwnerType(SenderType.User.value());
        body.setType(chatType.value());
        body.setFindExisted(true);
        body.setRecipients(recipients);
        return chatApi.createConversation(body);
    }


    @Override
    public SendMessageRS send(ChatMS body) {
        body.setMsgType(ChatMsgType.Msg.value());
        body.setSenderType(SenderType.User.value());
        return chatApi.sendMessage(body);
    }

    @Override
    public List<ConversationRS> loads(ChatMS body) {
        body.setPageDashboard(PageDashboard.PageProfile);
        return chatApi.loadConversations(body);
    }
}
