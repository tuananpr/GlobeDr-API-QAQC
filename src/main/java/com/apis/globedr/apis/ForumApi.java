package com.apis.globedr.apis;

import com.apis.globedr.model.request.article.ArticlePostsRQ;
import com.apis.globedr.model.request.article.LoadCategoryWithPostRQ;
import com.apis.globedr.model.response.article.CommentRS;
import com.apis.globedr.model.response.article.LoadCategoryWithPostRS;
import com.apis.globedr.model.response.article.LoadPostDetailRS;
import com.apis.globedr.model.step.article.ArticleMS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.rest.core.RestCore;
import com.apis.globedr.constant.API;
import com.apis.globedr.constant.Text;
import com.rest.core.response.Response;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForumApi extends BaseApi {


    private ForumApi() {
    }

    private static ForumApi instant;

    public static ForumApi getInstant() {
        if (instant == null) instant = new ForumApi();
        return instant;
    }


    public LoadPostDetailRS loadPostDetail(String postSignature) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ARTICLE_POST_SIGNATURE, postSignature);
        return RestCore.given().url(API.Forum.LOAD_POST_DETAIL()).auth(token).body(body).post().send()
                .extractAsModel("data", LoadPostDetailRS.class);
    }


    public LoadPostDetailRS loadPostDetail2(String postSignature) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ARTICLE_POST_SIGNATURE, postSignature);
        return RestCore.given().url(API.Forum.LOAD_POST_DETAIL2()).auth(token).body(body).post().send()
                .extractAsModel("data", LoadPostDetailRS.class);

    }

    public Response comment(String postSignature, String content) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ARTICLE_POST_SIGNATURE, postSignature);
        body.put(Text.ARTICLE_MSG_CONTENT, content);
        return RestCore.given().url(API.Forum.COMMENT()).auth(token).body(body).post().send();
    }


    public List<CommentRS> loadComment(String postSignature) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ARTICLE_POST_SIGNATURE, postSignature);
        return RestCore.given().url(API.Forum.LOAD_COMMENTS()).auth(token).body(body).post().send()
                .extractAsModels("data.list", CommentRS.class);

    }

    public List<CommentRS> loadComment2(String postSignature) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ARTICLE_POST_SIGNATURE, postSignature);
        return RestCore.given().url(API.Forum.LOAD_COMMENTS2()).auth(token).body(body).post().send()
                .extractAsModels("data.list", CommentRS.class);
    }


    public Response likeComment(String postMsgSig, boolean isLike) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ARTICLE_POST_MSG_SINGNATURE, postMsgSig);
        body.put(Text.IS_LIKE, isLike);
        return likeComment(body);
    }

    public Response likeComment(Map<String, Object> body) {
        return RestCore.given().url(API.Forum.LIKE_COMMENT()).auth(token).body(body).post().send();
    }


    public Response uploadPostMsgDocs(String postSignature, File files) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ARTICLE_POST_SIGNATURE, postSignature);
        body.put(Text.FILES, files);
        return RestCore.given().url(API.Forum.UPLOAD_POST_MSG_DOCS()).auth(token).multipart(body).post().send();
    }


    public Response loadRelatedPost(String postSignature) {
        Map<String, Object> body = new HashMap<>();
        body.put(Text.ARTICLE_POST_SIGNATURE, postSignature);
        return RestCore.given().url(API.Forum.RELATED_POST()).auth(token).body(body).post().send();
    }


    public List<LoadPostDetailRS> loadArticles(Object body) {
        return RestCore.given().url(API.Forum.ARTICLE_POSTS()).auth(token)
                .body(body, ArticlePostsRQ.class).post().send()
                .extractAsModels("data.list", LoadPostDetailRS.class);

    }

    public List<LoadCategoryWithPostRS> loadCategoryWithPost(ArticleMS body) {
        return RestCore.given().url(API.Forum.LOAD_CATEGORY_WITH_POST()).auth(token)
                .body(body, LoadCategoryWithPostRQ.class).post().send()
                .extractAsModels("data.list", LoadCategoryWithPostRS.class);

    }

    public List<LoadCategoryWithPostRS> loadCategoryWithPost2(ArticleMS body) {
        return RestCore.given().url(API.Forum.LOAD_CATEGORY_WITH_POST2()).auth(token)
                .body(body, LoadCategoryWithPostRQ.class).post().send()
                .extractAsModels("data.list", LoadCategoryWithPostRS.class);

    }

}
