package com.apis.globedr.apis;

import com.apis.globedr.helper.*;
import com.apis.globedr.model.general.OrgSig;
import com.apis.globedr.model.general.PostSig;
import com.apis.globedr.model.request.campaign.AddMessageRQ;
import com.apis.globedr.model.request.chat.*;
import com.apis.globedr.model.response.chat.*;
import com.apis.globedr.model.response.telemedicine.CallRecipientRS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;

import java.util.*;

public class ChatApi extends BaseApi {


    private ChatApi() {
    }

    private static ChatApi instant;

    public static ChatApi getInstant() {
        if (instant == null) instant = new ChatApi();
        return instant;
    }

    public String createTempMessage() {
        RestCore.given().url(API.Chat.CREATE_TEMPMSG()).auth(token).put().send();
        return Data.response.extract("data.msgSig");
    }


    public Response getActions(Object body) {
        return RestCore.given().url(API.Chat.GET_ACTIONS()).auth(token).bodyEncrypt(body, PostSig.class).delete().send();
    }


    public Response conversationRecipient(Object body) {
        return RestCore.given().url(API.Chat.CONVERSATION_RECIPIENT()).auth(token).bodyEncrypt(body, ConversationRecipientRQ.class).delete().send();
    }



    public LoadConversationRS loadConversation(Object body) {
        return RestCore.given().url(API.Chat.LOAD_CONVERSATION()).auth(token)
                .bodyEncrypt(body, LoadConversationRQ.class).post().send()
                .extractAsModel("data.conversation", LoadConversationRS.class);
    }

    public List<ConversationRS> loadConversations(Object body) {
        return RestCore.given().url(API.Chat.LOAD_CONVERSATIONS()).auth(token)
                .bodyEncrypt(body, LoadConversationsRQ.class).post().send()
                .extractAsModels("data.list", ConversationRS.class );
    }

    public List<LoadMsgsRS> loadMsgs(Object body) {
        return RestCore.given().url(API.Chat.LOAD_MSGS()).auth(token)
                .bodyEncrypt(body, LoadMsgsRQ.class).post().send()
                .extractAsModels("data.list", LoadMsgsRS.class );
    }

    public void loadRecipients(String conversationSignature, String recipName) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.CHAT_CONVERSATION_SIG, conversationSignature);
        body.put(Text.NAME, recipName);
        RestCore.given().url(API.Chat.LOAD_RECIPIENTS()).auth(token).bodyEncrypt(body, LoadRecipientsRQ.class).post().send();
    }

    public List<RecipientChatRS> loadRecipients(Object body) {
        return RestCore.given().url(API.Chat.LOAD_RECIPIENTS()).auth(token)
                .bodyEncrypt(body, LoadRecipientsRQ.class).post().send()
                .extractAsModels("data.list", RecipientChatRS.class );
    }

    public List<LoadCampaignsRS> loadCampaigns(Object body) {
        return RestCore.given().url(API.Chat.LOAD_CAMPAIGNS()).auth(token)
                .bodyEncrypt(body, LoadCampaignsRQ.class).post().send()
                .extractAsModels("data.list", LoadCampaignsRS.class );
    }

    public Response loadUserClicked(Object body) {
        return RestCore.given().url(API.Chat.USER_CLICKED()).auth(token).bodyEncrypt(body, UserUnReadRQ.class).post().send();
    }

    public Response loadUserUnRead(Object body) {
        return RestCore.given().url(API.Chat.USER_UNREAD()).auth(token).bodyEncrypt(body, UserUnReadRQ.class).post().send();
    }

    public Response loadUserUnClicked(Object body) {
        return RestCore.given().url(API.Chat.USER_UNCLICKED()).auth(token).bodyEncrypt(body, UserUnReadRQ.class).post().send();
    }


    public Response clickMsg(Object body) {
        return RestCore.given().url(API.Chat.CLICK_MSG()).auth(token).bodyEncrypt(body, ClickMsgRQ.class).put().send();
    }


    public ConversationRS findConversation(Object body) {
        return RestCore.given().url(API.Chat.FIND_CONVERSATION()).auth(token)
                .bodyEncrypt(body, FindConversationRQ.class).post().send()
                .extractAsModel("data.conversation", ConversationRS.class);
    }


    public Response userGroups(Object body) {
        return RestCore.given().url(API.Chat.USER_GROUPS()).auth(token)
                .bodyEncrypt(body, UserGroupsRQ.class).post().send();
    }


    public Response actions(Object body) {
        return RestCore.given().url(API.Chat.ACTIONS()).auth(token)
                .bodyEncrypt(body, Actions.class).post().send();
    }


    public ConversationRS createConversation(Object body) {
        return RestCore.given().url(API.Chat.CREATE_CONVERSATION()).auth(token)
                .bodyEncrypt(body, CreateConversationRQ.class).post().send()
                .extractAsModel("data.conversation", ConversationRS.class);
    }

    public SendMessageRS sendMessage(Object body) {
        return RestCore.given().url(API.Chat.SEND_MESSAGE()).auth(token)
                .bodyEncrypt(body, SendMessageRQ.class).post().send()
                .extractAsModel("data.msg", SendMessageRS.class);
    }

    public Response uploadMsgDocs(Object body) {
        return RestCore.given().url(API.Chat.UPLOAD_MSG_DOCS()).auth(token).multipart(body).post().send();
    }


    public void conversationName(String converSig, String name) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.CHAT_CONVERSATION_SIG, converSig);
        body.put(Text.CHAT_VIEWER_TYPE, 1);
        body.put(Text.CHAT_CONVERSATION_NAME, name);
        conversationName(body);
    }

    public Response conversationName(Object body) {
        return RestCore.given().url(API.Chat.CONVERSATION_NAME()).auth(token).bodyEncrypt(body, ConversationNameRQ.class).put().send();
    }

    public Response conversationAvatar(Object body) {
        return RestCore.given().url(API.Chat.CONVERSATION_AVATAR()).auth(token).multipart(body, ConversationAvatarRQ.class).put().send();
    }

    public SendMessageRS addMessage(Object body) {
        return RestCore.given().url(API.Chat.ADD_MESSAGE()).auth(token)
                .bodyEncrypt(body, AddMessageRQ.class).post().send()
                .extractAsModel("data.msg", SendMessageRS.class);
    }

    public Response openMsg(Object body) {
        return RestCore.given().url(API.Chat.OPEN_MSG()).auth(token).bodyEncrypt(body, OpenMsgRQ.class).put().send();
    }
    public Response getSeen() {
        return RestCore.given().url(API.Chat.GET_SEEN()).auth(token).bodyEncrypt(new HashMap<>()).get().send();
    }

    public Response donePickUp(Object body) {
        return RestCore.given().url(API.Chat.DONE_PICK_UP()).auth(token).bodyEncrypt(body, ConversationSig.class).put().send();
    }

    public List<CallRecipientRS> customerCares(Object body) {
        return RestCore.given().url(API.Chat.CUSTOMER_CARES()).auth(token)
                .bodyEncrypt(body, CustomerCaresRQ.class).post().send()
                .extractAsModels("data.list", CallRecipientRS.class);
    }

    public Response customerCareStatus(Object body) {
        return RestCore.given().url(API.Chat.CUSTOMER_CARE_STATUS()).auth(token)
                .params(body, OrgSig.class).get().send();
    }


    public Response subConversations(Object body) {
        return RestCore.given().url(API.Chat.SUB_CONVERSATIONS()).auth(token).bodyEncrypt(body, SubConversationsRQ.class).post().send();
    }

    public List<ConversationTypesRS> conversationTypes(Object body) {
        return RestCore.given().url(API.Chat.CONVERSATION_TYPES()).auth(token).bodyEncrypt(body, OrgSig.class).post().send()
                .extractAsModels("data.list", ConversationTypesRS.class);
    }

    public Response turnOnCustomerCare(Object body) {
        return RestCore.given().url(API.Chat.TURN_ON_CUSTOMER_CARE()).auth(token).bodyEncrypt(body, TurnOnCustomerCareRQ.class).post().send();
    }

}
