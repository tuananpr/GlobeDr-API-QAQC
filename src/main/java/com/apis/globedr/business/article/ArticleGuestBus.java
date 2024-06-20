package com.apis.globedr.business.article;

import com.apis.globedr.model.response.article.LoadCategoryWithPostRS;
import com.apis.globedr.model.step.article.ArticleMS;
import com.apis.globedr.model.response.article.CommentRS;
import com.apis.globedr.model.response.article.LoadPostDetailRS;
import com.apis.globedr.model.response.article.PostInitRS;
import com.apis.globedr.apis.ForumApi;
import com.rest.core.response.Response;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class ArticleGuestBus extends AbsArticleBus {

    public List<LoadCategoryWithPostRS> loadCategoryWithPost(ArticleMS body) {
        return ForumApi.getInstant().loadCategoryWithPost2(body);
    }

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
        return ForumApi.getInstant().loadPostDetail2(loadsAndGetPostSig(body));
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
    public Response comment(ArticleMS body) {
        return null;
    }


    public List<CommentRS> loadComment(ArticleMS body) {
        return ForumApi.getInstant().loadComment2(loadsAndGetPostSig(body));
    }

    @Override
    public Response likeComment(ArticleMS body) {
        return null;
    }

    @Override
    public Response unlikeComment(ArticleMS body) {
        return null;
    }

    @Override
    public Response addDocToComment(ArticleMS body) {
        return null;
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
