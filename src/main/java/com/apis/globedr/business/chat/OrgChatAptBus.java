package com.apis.globedr.business.chat;

import com.apis.globedr.enums.ChatType;
import com.apis.globedr.model.general.Recipient;
import com.apis.globedr.model.response.chat.ConversationRS;
import com.apis.globedr.model.response.chat.LoadMsgsRS;
import com.apis.globedr.model.step.chat.ChatMS;

import java.util.List;

public class OrgChatAptBus extends AbsOrgChatBus {

    @Override
    public ConversationRS createConversation(List<Recipient> recipients, ChatType chatType) {
        return null;
    }

    @Override
    public List<LoadMsgsRS> loadMsgs(ChatMS info) {

        ChatMS body = ChatMS.builder()
                .conversationSig(findConversation(info.getOwnerSig(), info.getRecipients(), info.getType()).getConversationSig())
                .build();

        return chatApi.loadMsgs(body);
    }

}
