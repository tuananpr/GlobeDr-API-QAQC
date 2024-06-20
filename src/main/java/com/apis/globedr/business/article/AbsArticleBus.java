package com.apis.globedr.business.article;

import com.apis.globedr.business.AbsBus;
import com.apis.globedr.model.response.article.LoadCategoryWithPostRS;
import com.apis.globedr.model.step.article.ArticleMS;
import com.apis.globedr.model.response.article.CommentRS;
import com.apis.globedr.model.response.article.LoadPostDetailRS;
import com.apis.globedr.model.response.article.PostInitRS;
import com.apis.globedr.apis.ForumApi;
import com.apis.globedr.apis.SearchApi;
import com.rest.core.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;

import java.util.List;



public abstract class AbsArticleBus extends AbsBus {

    protected void validateInit(ArticleMS body) {
        if (body.getLanguage() == null) Assert.fail("Please set language to create System Post");
    }


    protected abstract PostInitRS init(ArticleMS body);

    public abstract Response create(ArticleMS body);

    public abstract Response updateTo(ArticleMS oldPost, ArticleMS newData);

    public abstract LoadPostDetailRS loadsDetail(ArticleMS body);

    public abstract void loadRejectLog(ArticleMS body);

    public abstract void delete(ArticleMS body);

    public abstract void loadNotes(ArticleMS body);

    public abstract void approve(ArticleMS body);

    public abstract void reject(ArticleMS body);

    public abstract Response comment(ArticleMS body);

    public abstract List<CommentRS> loadComment(ArticleMS body);

    public abstract Response likeComment(ArticleMS body);

    public abstract Response unlikeComment(ArticleMS body);

    public abstract Response addDocToComment(ArticleMS body);

    public abstract Response showGlobedrArticleConfig(ArticleMS body, boolean isShow);

    public abstract Response showGlobedrArticleValue(ArticleMS body);

    public abstract List<LoadCategoryWithPostRS> loadCategoryWithPost(ArticleMS body);

    public abstract Response updateForumIcon(String orgSig, String postSig, String pathFile);
    public abstract void submitPost(ArticleMS body);


    public List<LoadPostDetailRS> loads(ArticleMS body) {
        return ForumApi.getInstant().loadArticles(body);
    }

    protected String loadsAndGetPostSig(ArticleMS body) {
        List<LoadPostDetailRS> rs = loads(body);
        if (body.getPostTitle() != null) {
            return rs.stream().filter(info -> info.getTitle().equalsIgnoreCase(body.getPostTitle()))
                    .map(info -> info.getPostSignature()).findFirst().orElse(null);
        }
        return rs.get(0).getPostSignature();
    }


    public Response loadRelatedPost(ArticleMS body) {
        return ForumApi.getInstant().loadRelatedPost(loadsAndGetPostSig(body));
    }


    public Response likePost(ArticleMS body) {
        return ForumApi.getInstant().likeComment(loadsDetail(body).getPostMsg().getMsgSig(), true);
    }


    public Response unlikePost(ArticleMS body) {
        return ForumApi.getInstant().likeComment(loadsDetail(body).getPostMsg().getMsgSig(), false);
    }


}
