package com.apis.globedr.business.article;

import com.apis.globedr.model.step.article.ArticleMS;
import com.apis.globedr.model.response.article.LoadPostDetailRS;
import com.apis.globedr.model.response.article.PostInitRS;
import com.apis.globedr.apis.ForumApi;
import com.rest.core.response.Response;
import lombok.Data;


public class ArticleUserBus extends AbsArticleUserBus {


    @Override
    protected PostInitRS init(ArticleMS body) {
        return null;
    }

    @Override
    public Response create(ArticleMS body) {
        return null;
    }

    @Override
    public Response updateTo(ArticleMS oldPost, ArticleMS newData) {
        return null;
    }

    @Override
    public LoadPostDetailRS loadsDetail(ArticleMS body) {
        return ForumApi.getInstant().loadPostDetail(loadsAndGetPostSig(body));
    }

    @Override
    public void loadRejectLog(ArticleMS body) {

    }

    @Override
    public void delete(ArticleMS body) {

    }

    @Override
    public void loadNotes(ArticleMS body) {

    }

    @Override
    public void approve(ArticleMS body) {

    }

    @Override
    public void reject(ArticleMS body) {

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
    public Response updateForumIcon(String orgSig, String postSig, String pathFile) {
        return null;
    }

    @Override
    public void submitPost(ArticleMS body) {

    }

}
