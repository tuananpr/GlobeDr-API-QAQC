package com.apis.globedr.business.chat;


import com.apis.globedr.enums.*;
import com.apis.globedr.model.general.Recipient;
import com.apis.globedr.model.response.chat.*;
import com.apis.globedr.model.step.chat.ChatMS;
import java.util.List;


public class OrgChatBus extends AbsOrgChatBus {


    @Override
    public List<LoadMsgsRS> loadMsgs(ChatMS body) {
        String conversationSig = loadConversation(body).getConversationSig();
        body.setConversationSig(conversationSig);
        return chatApi.loadMsgs(body);
    }

    @Override
    public ConversationRS createConversation(List<Recipient> recipients, ChatType chatType) {
        return null;
    }

}
