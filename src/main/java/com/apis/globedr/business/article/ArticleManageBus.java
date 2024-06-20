package com.apis.globedr.business.article;

import com.apis.globedr.apis.ForumManagerApi;
import com.apis.globedr.model.request.article.DeletePostRQ;
import com.apis.globedr.model.request.article.ShowGlobedrArticleConfigRQ;
import com.apis.globedr.model.step.article.ArticleMS;
import com.rest.core.response.Response;
import lombok.Data;

public class ArticleManageBus extends AbsArticleManageBus {


    @Override
    public void approve(ArticleMS body) {

    }

    @Override
    public void reject(ArticleMS body) {

    }

    @Override
    public Response showGlobedrArticleConfig(ArticleMS body, boolean isShow) {
        ShowGlobedrArticleConfigRQ request = new ShowGlobedrArticleConfigRQ();
        request.setShowGlobedrArticle(isShow);
        request.setOrgSig(body.getOrgSig());
        return ForumManagerApi.getInstant().showGlobedrArticleConfig(request);
    }

    @Override
    public Response showGlobedrArticleValue(ArticleMS body) {
        return ForumManagerApi.getInstant().showGlobedrArticleValue(body.getOrgSig());
    }

    public void submitPost(ArticleMS body) {
        loads(body).forEach(p ->{
            body.setPostSignature(p.getPostSignature());
            ForumManagerApi.getInstant().submitPost(body);
        });
    }

    @Override
    public void loadNotes(ArticleMS body) {
        DeletePostRQ request = new DeletePostRQ();
        loads(body).forEach(p ->{
            request.setPostSignature(p.getPostSignature());
            request.setOrgSignature(body.getOrgSig());
            ForumManagerApi.getInstant().loadNotes(request);
        });
    }
}
