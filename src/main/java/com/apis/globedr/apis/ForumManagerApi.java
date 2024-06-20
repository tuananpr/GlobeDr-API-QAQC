package com.apis.globedr.apis;

import com.apis.globedr.model.request.article.*;
import com.apis.globedr.model.response.article.LoadCategoryWithPostRS;
import com.apis.globedr.model.response.article.LoadPostDetailRS;
import com.apis.globedr.model.response.article.PostCategoryRS;
import com.apis.globedr.model.response.article.PostInitRS;
import com.apis.globedr.business.article.PostCategoryBus;
import com.apis.globedr.business.article.PostCategoryManage;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumManagerApi extends BaseApi {


    private ForumManagerApi() {
    }

    private static ForumManagerApi instant;

    public static ForumManagerApi getInstant() {
        if (instant == null) instant = new ForumManagerApi();
        return instant;
    }


    public List<LoadCategoryWithPostRS> loadCategoryWithPost(int language) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.LANGUAGE, language);
        return RestCore.given().url(API.ForumManager.LOAD_CATEGORY_WITH_POST()).auth(token)
                .body(body, LoadCategoryWithPostRQ.class).post().send()
                .extractAsModels("data.list", LoadCategoryWithPostRS.class);
    }

    public PostCategoryBus addCategory(Object body) {
        return RestCore.given().url(API.ForumManager.ADD_CATEGORY()).auth(token)
                .body(body, AddCategoryRQ.class).post().send()
                .extractAsModel("data", PostCategoryManage.class);
    }

    public PostCategoryBus updateCategory(Object body) {
        // Same object with AddCategoryRQ
        return RestCore.given().url(API.ForumManager.UPDATE_CATEGORY()).auth(token)
                .body(body, AddCategoryRQ.class).post().send()
                .extractAsModel("data", PostCategoryManage.class);
    }

    public List<PostCategoryRS> loadCategories(Object body) {
        return RestCore.given().url(API.ForumManager.LOAD_CATEGORIES())
                .auth(token).body(body, LoadCategoriesRQ.class).post().send()
                .extractAsModels("data.categories", PostCategoryRS.class);
    }


    public PostInitRS addPostInit(Object body) {
        return RestCore.given().url(API.ForumManager.ADD_POST_INIT()).auth(token)
                .body(body, AddPostInitRQ.class).post().send()
                .extractAsModel("data.postInit", PostInitRS.class);
    }

    public Response addPost(Object body) {
        return RestCore.given().url(API.ForumManager.ADD_POST()).auth(token)
                .body(body, AddPostRQ.class).post().send();
    }

    public Response updatePost(Object body) {
        // Same object with AddPostRQ
        return RestCore.given().url(API.ForumManager.UPDATE_POST()).auth(token)
                .body(body, AddPostRQ.class).post().send();
    }


    public List<LoadPostDetailRS> loadPosts(Object body) {
        return RestCore.given().url(API.ForumManager.POSTS()).auth(token)
                .body(body, PostsRQ.class).post().send()
                .extractAsModels("data.list", LoadPostDetailRS.class);
    }


    public LoadPostDetailRS loadPostDetail(Object body) {
        return RestCore.given().url(API.ForumManager.LOAD_POST_DETAIL()).auth(token)
                .body(body, LoadPostDetailRQ.class).post().send().
                        extractAsModel("data", LoadPostDetailRS.class);
    }


    public Response delete(Object body) {
        return RestCore.given().url(API.ForumManager.DELETE_POST()).auth(token)
                .body(body, DeletePostRQ.class).post().send();
    }


    public Response loadNotes(Object body) {
        // Same object with DeletePostRQ
        return RestCore.given().url(API.ForumManager.LOAD_NOTES()).auth(token)
                .body(body, DeletePostRQ.class).post().send();
    }

    public Response loadRejectLog(Object body) {
        // Same object with DeletePostRQ
        return RestCore.given().url(API.ForumManager.LOAD_REJECT_LOG()).auth(token)
                .body(body, DeletePostRQ.class).post().send();
    }


    public Response approve(Object body) {
        // Same object with DeletePostRQ
        return RestCore.given().url(API.ForumManager.APPROVE_POST()).auth(token)
                .body(body, DeletePostRQ.class).post().send();
    }


    public Response reject(Object body) {
        // Same object with DeletePostRQ
        return RestCore.given().url(API.ForumManager.REJECT_POST()).auth(token)
                .body(body, DeletePostRQ.class).post().send();
    }

    public Response updateForumIcon(Object body) {
        return RestCore.given().url(API.ForumManager.UPLOAD_FORUM_ICON()).auth(token)
                .multipart(body, UploadForumIconRQ.class).post().send();
    }


    public Response loadSystemPosts(Object body) {
        return RestCore.given().url(API.ForumManager.LOAD_SYSTEM_POSTS()).auth(token)
                .body(body, LoadSystemPostRQ.class).post().send();
    }

    public Response updateSystemPost(Object body) {
        // same object with AddSystemPostRQ
        return RestCore.given().url(API.ForumManager.UPDATE_SYSTEM_POST()).auth(token)
                .body(body, AddSystemPostRQ.class).post().send();
    }


    public Response showGlobedrArticleConfig(Object body) {
        return RestCore.given().url(API.ForumManager.SHOW_GLOBEDR_ARTICLE_CONFIG()).auth(token)
                .bodyEncrypt(body, ShowGlobedrArticleConfigRQ.class).post().send();
    }

    public Response showGlobedrArticleValue(String orgSig) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ORG_SIG, orgSig);
        return RestCore.given().url(API.ForumManager.SHOW_GLOBEDR_ARTICLE_VALUE()).auth(token).bodyEncrypt(body).post().send();
    }


    public Response addSystemPost(Object body) {
        return RestCore.given().url(API.ForumManager.ADD_SYSTEM_POST()).auth(token)
                .body(body, AddSystemPostRQ.class).post().send();
    }


    public PostInitRS addSystemPostInit(Object body) {
        // same object with AddPostInitRQ
        return RestCore.given().url(API.ForumManager.ADD_SYSTEM_POST_INIT()).auth(token)
                .body(body, AddPostInitRQ.class).post().send()
                .extractAsModel("data.postInit", PostInitRS.class);
    }

    public Response submitPost(Object body) {
        return RestCore.given().url(API.ForumManager.SUBMIT_POST()).auth(token)
                .body(body, SubmitPostRQ.class).post().send();
    }

}
