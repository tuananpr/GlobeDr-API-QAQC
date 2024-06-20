package com.apis.globedr.apis;

import com.apis.globedr.model.general.MsgSig;
import com.apis.globedr.model.general.PostSig;
import com.apis.globedr.model.request.consult.*;
import com.apis.globedr.model.response.consult.*;
import com.apis.globedr.model.response.article.CommentRS;
import com.rest.core.RestCore;
import com.rest.core.response.Response;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.apis.globedr.helper.Common;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

public class ConsultantApi extends BaseApi {

    public int page = 1;
    public int pageSize = 20;

    private ConsultantApi(){}
    private static ConsultantApi instant;
    public static ConsultantApi getInstant(){
        if(instant == null) instant = new ConsultantApi();
        return instant;
    }

    public MsgSig addFile(Object body) {
        return RestCore.given().url(API.Consult.ADD_FILE()).auth(token)
                .multipart(body, AddFileRQ.class).post().send()
                .extractAsModel("data", MsgSig.class);
    }


    public AddCommentRS addComment(Object body) {
        return RestCore.given().url(API.Consult.ADD_COMMENT()).auth(token)
                .bodyEncrypt(body, AddCommentRQ.class).post().send()
                .extractAsModel("data", AddCommentRS.class);
    }


    public List<CommentRS> loadAllComment(Object body) {
        return RestCore.given().url(API.Consult.GET_COMMENTS()).auth(token)
                .bodyEncrypt(body, PostSig.class).post().send()
                .extractAsModels("data.list", CommentRS.class );
    }



    public List<QuestionRS> loadQuestions(Object body) {
        return RestCore.given().url(API.Consult.LOAD_QUESTIONS()).auth(token)
                .bodyEncrypt(body, LoadQuestionsRQ.class).post().send()
                .extractAsModels("data.list", QuestionRS.class );
    }


    public GetActionsRS getActions(String postSig) {
        return RestCore.given().url(API.Consult.GET_ACTIONS()).auth(token)
                .bodyEncrypt(new PostSig(postSig)).post().send()
                .extractAsModel("data", GetActionsRS.class);
    }



    public QuestionRS getQuestion(String postSig) {
        return RestCore.given().url(API.Consult.GET_QUESTION()).auth(token).bodyEncrypt(new PostSig(postSig)).post().send()
                .extractAsModel("data.question", QuestionRS.class);
    }

    public void getComments(String postSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.POST_SIG, postSig);
        RestCore.given().url(API.Consult.GET_COMMENTS()).auth(token).bodyEncrypt(body).post().send();
    }




    public void setTime(Map<String, Object> dataTable, String postSig, String conversationSig, String orgSig) {
        Map<String, Object> body = Common.getMap(dataTable);
        body.put(Text.POST_SIG, postSig);
        body.put(Text.CHAT_CONVERSATION_SIG, conversationSig);
        body.put(Text.ORG_SIG, orgSig);
        RestCore.given().url(API.Consult.APPOINTMENT_SET_TIME()).auth(token).body(body).post().send();
    }

    public Response reviewQuestion(Object body) {
        return RestCore.given().url(API.Consult.REVIEW_QUESTION()).auth(token).bodyEncrypt(body).post().send();
    }

    public Response questionReviews() {
        return RestCore.given().url(API.Consult.QUESTION_REVIEWS()).auth(token).bodyEncrypt(new PostSig()).post().send();
    }

    public Response addHealthDoc(Object body) {
        return RestCore.given().url(API.Consult.ADD_HEALTH_DOC()).auth(token).bodyEncrypt(body, AddHealthDocRQ.class).post().send();
    }




}
