package com.apis.globedr.business.chat;

import com.apis.globedr.apis.ChatApi;
import com.apis.globedr.business.campaign.AbsSendMessage;
import com.apis.globedr.enums.ChatMsgType;
import com.apis.globedr.enums.ChatType;
import com.apis.globedr.enums.RecipientType;
import com.apis.globedr.enums.SenderType;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.general.Recipient;
import com.apis.globedr.model.request.chat.*;
import com.apis.globedr.model.response.chat.*;
import com.apis.globedr.model.response.consult.GetActionsRS;
import com.apis.globedr.model.response.telemedicine.CallRecipientRS;
import com.apis.globedr.model.step.chat.ChatMS;
import com.apis.globedr.model.step.chat.CustomerCaresMS;
import com.apis.globedr.model.step.consult.ConsultMS;
import com.apis.globedr.model.step.telemedicine.VideoCallMS;
import com.rest.core.response.Response;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbsChatBus extends AbsSendMessage {

    ChatApi chatApi = ChatApi.getInstant();


    public List<ConversationRS> loads(String name) {
        ChatMS body = ChatMS.builder().conversationName(name).build();
        return loads(body);
    }

    protected ConversationRS loadsByName(ChatMS body) {
        return loads(body).stream()
                .filter(c -> {
                    if (body.getConversationName() != null)
                        return c.getName().equalsIgnoreCase(body.getConversationName());
                    return true;
                })
                .findFirst().orElse(null);
    }


    public LoadConversationRS loadConversation(ChatMS body) {
        String conversationSig = loadsByName(body).getConversationSig();

        body = beforeLoadConversation(body);
        body.setConversationSig(conversationSig);
        return chatApi.loadConversation(body);
    }


    public Response openMsg(ChatMS body) {
        LoadMsgsRS msg = loadMsgs(body).stream().findFirst().orElse(null);
        return chatApi.openMsg(msg);
    }

    public Response clickMsg(ChatMS body) {
        LoadMsgsRS msg = loadMsgs(body).stream().findFirst().orElse(null);

        body.setMsgSig(msg.getMsgSig());
        body.setConversationSig(msg.getConversationSig());
        return chatApi.clickMsg(body);
    }


    public Response getSeen() {
        return chatApi.getSeen();
    }


    public Response changeConversationName(ConversationNameRQ body) {
        return chatApi.conversationName(body);
    }

    public List<RecipientChatRS> loadRecipients(ChatMS body) {
        return chatApi.loadRecipients(body);
    }

    public List<String> loadRecipientNames(LoadRecipientsRQ body) {
        return chatApi.loadRecipients(body).stream().map(s -> s.getName()).collect(Collectors.toList());
    }

    public Response setAvatar(ChatMS body) {
        String conversationSig = loadsByName(body).getConversationSig();
        body.setConversationSig(conversationSig);
        return chatApi.conversationAvatar(body);
    }

    public Response leave(ChatMS body) {
        String conversationSig = loadsByName(body).getConversationSig();
        body.setConversationSig(conversationSig);
        body.setViewerType(SenderType.User.value());
        body.setRecipients(new ArrayList<>());
        return chatApi.conversationRecipient(body);
    }

    public Response uploadMsgDocs(UploadMsgDocsRQ body) {
        body.setMsgType(ChatMsgType.Msg.value());
        body.setSenderType(SenderType.User.value());
        return chatApi.uploadMsgDocs(body);
    }


    public Response kickMember(ChatMS body) {
        String conversationSig = loadsByName(body).getConversationSig();

        List<Recipient> recipients = loadRecipients(body).stream()
                .filter(recipient -> body.getRecipientName().contains(recipient.getName()))
                .map(recipient -> new Recipient(RecipientType.User.value(), recipient.getRecipSig()))
                .collect(Collectors.toList());

        ConversationRecipientRQ request = ConversationRecipientRQ.builder()
                .conversationSig(conversationSig)
                .viewerType(SenderType.User.value())
                .recipients(recipients)
                .build();

        return chatApi.conversationRecipient(request);
    }



    public List<CallRecipientRS> customerCares(CustomerCaresMS body) {
        return chatApi.customerCares(body);
    }

    public Response customerCareStatus(CustomerCaresMS body) {
        return chatApi.customerCareStatus(body);
    }

    public Response subConversations(SubConversationsRQ body) {
        return chatApi.subConversations(body);
    }

    public List<ConversationTypesRS> conversationTypes(CustomerCaresMS body) {
        return chatApi.conversationTypes(body);
    }



    public List<CallRecipientRS> customerCares(VideoCallMS body) {
        return chatApi.customerCares(body);
    }

    public Response turnOnCustomerCare(TurnOnCustomerCareRQ body) {
        return chatApi.turnOnCustomerCare(body);
    }

    public Response doneConversation(ChatMS body) {
        return chatApi.donePickUp(body);
    }

    public Response userGroups(UserGroupsRQ body) {
        return chatApi.userGroups(body);
    }

    public Response actions(ChatMS body) {
        return chatApi.actions(body);
    }

    protected SendMessageRS sendAttachmentFile(ChatMS body) {
        return null;
    }

    protected SendMessageRS sendCampaign(ChatMS body) {
        return null;
    }

    protected void validation(ChatMS body) { }


    public abstract ConversationRS findConversation(String ownerSig, List<Recipient> recipients, ChatType chatType);

    public abstract ChatMS beforeLoadConversation(ChatMS body);

    public abstract ConversationRS createConversation(List<Recipient> recipients, ChatType chatType);


    public abstract List<LoadMsgsRS> loadMsgs(ChatMS body);

    public abstract List<ConversationRS> loads(ChatMS body);

}
