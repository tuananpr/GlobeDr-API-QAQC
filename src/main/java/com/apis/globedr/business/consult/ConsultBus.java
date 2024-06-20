package com.apis.globedr.business.consult;

import com.apis.globedr.apis.AuditorApi;
import com.apis.globedr.apis.ConsultantApi;
import com.apis.globedr.apis.CoordinatorApi;
import com.apis.globedr.apis.ProviderApi;
import com.apis.globedr.business.AbsBus;
import com.apis.globedr.model.general.MsgSig;
import com.apis.globedr.model.general.PostSig;
import com.apis.globedr.model.request.consult.AddCommentRQ;
import com.apis.globedr.model.response.consult.*;
import com.apis.globedr.model.request.consult.ReviewQuestionRQ;
import com.apis.globedr.model.response.article.CommentRS;
import com.apis.globedr.model.step.consult.ConsultMS;
import com.apis.globedr.model.step.health.HealthDocMS;
import com.apis.globedr.model.step.telemedicine.VideoCallMS;
import com.rest.core.response.Response;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ConsultBus extends AbsBus {

    protected CoordinatorApi coordinatorApi = CoordinatorApi.getInstant();
    protected ConsultantApi consultantApi = ConsultantApi.getInstant();
    protected ProviderApi providerApi = ProviderApi.getInstant();
    protected AuditorApi auditorApi = AuditorApi.getInstant();

    public String getPostSigOfQuestionContent(ConsultMS body) {
        return loadQuestionsByContent(body).stream().map(p -> p.getPostSig()).findFirst().orElse(null);
    }

    public String getPostSigOfQuestionContent(String msg) {
        return getPostSigOfQuestionContent(ConsultMS.builder().msg(msg).build());
    }

    public AddCommentRS createQuestion(ConsultMS body) {
        return consultantApi.addComment(body);
    }

    public AddCommentRS addComment(String content, ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return addComment(content, postSig);
    }

    public AddCommentRS addComment(String content, String postSig) {
        AddCommentRQ request = AddCommentRQ.builder().postSig(postSig).msg(content).build();
        return consultantApi.addComment(request);
    }

    public List<QuestionRS> loadQuestionsByContent(ConsultMS body) {
        return (body.getMsg() == null) ?
                loadQuestions(body) :
                loadQuestions(body).stream()
                        .filter(body.getMsg() != null ? c -> c.hasMsg(body.getMsg()) : c -> true)
                        .filter(body.getUserName() != null ? c->c.hasUserName(body.getUserName()) : c -> true)
                        .collect(Collectors.toList());
    }

    public List<QuestionRS> loadQuestionsByContent(String msg) {
        return loadQuestionsByContent(ConsultMS.builder().msg(msg).build());
    }

    public MsgSig addFile(ConsultMS body) {
        return consultantApi.addFile(body);
    }

    public List<CommentRS> loadComments(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return consultantApi.loadAllComment(new PostSig(postSig));
    }

    public QuestionRS getQuestion(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return consultantApi.getQuestion(postSig);
    }

    public QuestionRS getQuestion(VideoCallMS body) {
        String postSig = getPostSigOfQuestionContent(mapping(body, ConsultMS.class));
        return consultantApi.getQuestion(postSig);
    }

    public List<CommentRS> loadComments(String postSig) {
        return consultantApi.loadAllComment(new PostSig(postSig));
    }

    public void addFileIntoConsult(String file, ConsultMS body) {
        ConsultMS fileR = new ConsultMS().setFile(file);
        String msgSig = addFile(fileR).getMsgSig();

        loadQuestionsByContent(body).forEach(c -> {
            ConsultMS post = ConsultMS.builder().msgSig(msgSig).postSig(c.getPostSig()).build();
            createQuestion(post);
        });
    }

    public GetActionsRS getActions(ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        return consultantApi.getActions(postSig);
    }

    public Response reviewQuestion(int score, String review, ConsultMS body) {
        String postSig = getPostSigOfQuestionContent(body);
        ReviewQuestionRQ request = ReviewQuestionRQ
                .builder()
                .score(score)
                .review(review)
                .postSig(postSig).build();
        return consultantApi.reviewQuestion(request);
    }



    public Response addHealthDoc(HealthDocMS body) {
        return consultantApi.addHealthDoc(body);
    }

    public abstract List<QuestionRS> loadQuestions(ConsultMS body);

    public abstract Response sendNotiToPatient(ConsultMS body);

    public abstract Response getActivityLog(ConsultMS body);



}
