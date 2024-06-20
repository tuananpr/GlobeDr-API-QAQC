package com.apis.globedr.business.article;

import com.apis.globedr.model.request.article.AddPostInitRQ;
import com.apis.globedr.model.step.article.ArticleMS;
import com.apis.globedr.model.response.article.CommentRS;
import com.apis.globedr.model.response.article.LoadPostDetailRS;
import com.apis.globedr.model.response.article.PostInitRS;
import com.apis.globedr.apis.ForumManagerApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

@Getter
@Setter
public class ArticleSystemBus {


    protected PostInitRS init(ArticleMS body) {
        AddPostInitRQ init = new AddPostInitRQ();
        init.setLanguage(body.getLanguage());
        return ForumManagerApi.getInstant().addSystemPostInit(init);
    }


    public Response create(ArticleMS body) {
        PostInitRS postInit = init(body);
        body.setPostMsgSignature(postInit.getPostMsg().getPostMsgSig());
        return ForumManagerApi.getInstant().addSystemPost(body);
    }


    public Response updateTo(ArticleMS oldPost, ArticleMS newData) {
        List<ArticleMS> rs = loads(oldPost).extractAsModels("$.data.list[*].list[*]", ArticleMS.class);
        newData.setPostSignature(rs.get(0).getPostSignature());
        newData.setPostMsgSignature(rs.get(0).getPostMsgSignature());
        return ForumManagerApi.getInstant().updateSystemPost(newData);
    }


    public Response loads(ArticleMS body) {
        return ForumManagerApi.getInstant().loadSystemPosts(body);
    }


}
