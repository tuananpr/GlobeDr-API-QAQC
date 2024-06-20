package com.apis.globedr.business.article;

import com.apis.globedr.model.request.article.DeletePostRQ;
import com.apis.globedr.model.request.article.UploadForumIconRQ;
import com.apis.globedr.model.step.article.ArticleMS;
import com.apis.globedr.model.response.article.LoadPostDetailRS;
import com.apis.globedr.model.response.article.PostInitRS;
import com.apis.globedr.apis.ForumManagerApi;
import com.apis.globedr.model.general.file.FileFactory;
import com.rest.core.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;

import java.util.List;


public abstract class AbsArticleManageBus extends AbsArticleUserBus {


    public Response create(ArticleMS body) {
        PostInitRS postInit = init(body);
        body.setPostMsgSignature(postInit.getPostMsg().getPostMsgSig());
        body.setCategorySignature(postInit.getCategorySigByName(body.getCategoryName()));
        return ForumManagerApi.getInstant().addPost(body);
    }


    protected PostInitRS init(ArticleMS body) {
        validateInit(body);
        if(body.getLanguage() == null) Assert.fail("Please set language to create System Post");
        return ForumManagerApi.getInstant().addPostInit(body);
    }


    public void loadRejectLog(ArticleMS body) {
        DeletePostRQ request = new DeletePostRQ();
        loads(body).forEach(p ->{
            request.setPostSignature(p.getPostSignature());
            request.setOrgSignature(body.getOrgSig());
            ForumManagerApi.getInstant().loadRejectLog(request);
        });
    }


    public void delete(ArticleMS body) {
        DeletePostRQ request = new DeletePostRQ();
        loads(body).forEach(p ->{
            request.setPostSignature(p.getPostSignature());
            request.setOrgSignature(body.getOrgSig());
            ForumManagerApi.getInstant().delete(request);
        });
    }




    public Response updateTo(ArticleMS oldPost, ArticleMS newData) {
        LoadPostDetailRS old =  loadsDetail(oldPost);
        newData.setPostMsgSignature(old.getPostMsg().getMsgSig());
        newData.setPostSignature(old.getPostSignature());
        newData.setOrgSig(oldPost.getOrgSig());

        return  ForumManagerApi.getInstant().updatePost(newData);
    }


    @Override
    public List<LoadPostDetailRS> loads(ArticleMS body) {
        body.setIsLoadMyPost(true);
        return ForumManagerApi.getInstant().loadPosts(body);
    }


    @Override
    public LoadPostDetailRS loadsDetail(ArticleMS body) {
        return ForumManagerApi.getInstant().loadPostDetail(loads(body).get(0));
    }

    @Override
    public Response updateForumIcon(String orgSig, String postSig, String pathFile) {
        UploadForumIconRQ request = new UploadForumIconRQ();
        request.setOrgSignature(orgSig);
        request.setPostSignature(postSig);
        request.setFile(FileFactory.getFile(pathFile).getFile());
        return ForumManagerApi.getInstant().updateForumIcon(request);
    }

}
