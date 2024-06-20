package com.apis.globedr.business.article;

import com.apis.globedr.apis.ForumManagerApi;
import com.apis.globedr.model.request.article.DeletePostRQ;
import com.apis.globedr.model.response.article.LoadPostDetailRS;
import com.apis.globedr.model.step.article.ArticleMS;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rest.core.response.Response;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor

@Accessors(chain = true)
@Builder(toBuilder = true)
@ToString
public class ArticleApproveBus extends AbsArticleManageBus {

    @Override
    public List<LoadPostDetailRS> loads(ArticleMS body) {
        body.setOrgSig(null);
        return ForumManagerApi.getInstant().loadPosts(body);
    }


    @Override
    public void loadNotes(ArticleMS body) {
        DeletePostRQ request = new DeletePostRQ();
        loads(body).forEach(p ->{
            request.setPostSignature(p.getPostSignature());
            request.setOrgSignature(p.getCreatedSig());
            ForumManagerApi.getInstant().loadNotes(request);
        });
    }


    @Override
    public void approve(ArticleMS body) {
        loads(body).forEach(p ->{
            body.setPostSignature(p.getPostSignature());
            ForumManagerApi.getInstant().approve(body);
        });

    }

    @Override
    public void reject(ArticleMS body) {
        loads(body).forEach(p ->{
            body.setPostSignature(p.getPostSignature());
            ForumManagerApi.getInstant().reject(body);
        });
    }

    @Override
    public Response showGlobedrArticleConfig(ArticleMS body, boolean isShow) {
        return null;
    }

    @Override
    public Response showGlobedrArticleValue(ArticleMS body) {
        return null;
    }

    @Override
    public void submitPost(ArticleMS body) {

    }


}
