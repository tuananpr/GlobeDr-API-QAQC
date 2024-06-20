package com.apis.globedr.business.article;

import com.apis.globedr.model.response.article.LoadCategoryWithPostRS;
import com.apis.globedr.model.step.article.ArticleMS;
import com.apis.globedr.model.response.article.CommentRS;
import com.apis.globedr.apis.ForumApi;
import com.rest.core.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public abstract class AbsArticleUserBus extends AbsArticleBus {

    public List<LoadCategoryWithPostRS> loadCategoryWithPost(ArticleMS body) {
        return ForumApi.getInstant().loadCategoryWithPost(body);
    }

    public Response comment(ArticleMS body) {
        return ForumApi.getInstant().comment(loadsAndGetPostSig(body), body.getMsgContent());
    }


    public List<CommentRS> loadComment(ArticleMS body) {
        return ForumApi.getInstant().loadComment(loadsAndGetPostSig(body));
    }


    public Response likeComment(ArticleMS body) {
        CommentRS comment = loadComment(body).stream().filter(c -> c.getMsgContent().equalsIgnoreCase(body.getMsgContent())).findFirst().orElse(null);
        return ForumApi.getInstant().likeComment(comment.getMsgSignature(), true);
    }


    public Response unlikeComment(ArticleMS body) {
        CommentRS comment = loadComment(body).stream().filter(c -> c.getMsgContent().equalsIgnoreCase(body.getMsgContent())).findFirst().orElse(null);
        return ForumApi.getInstant().likeComment(comment.getMsgSignature(), false);
    }

    public Response addDocToComment(ArticleMS body) {
        return ForumApi.getInstant().uploadPostMsgDocs(loadsDetail(body).getPostSignature(), body.getFiles());
    }





}
