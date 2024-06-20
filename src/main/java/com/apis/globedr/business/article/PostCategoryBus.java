package com.apis.globedr.business.article;

import com.apis.globedr.apis.ForumManagerApi;
import com.apis.globedr.model.response.article.PostCategoryRS;
import com.apis.globedr.model.step.article.PostCategoryMS;
import com.apis.globedr.stepdefinition.SqlDatabaseStep;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rest.core.response.Response;
import lombok.*;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public abstract class PostCategoryBus {

    public PostCategoryBus create(PostCategoryMS body){
        new SqlDatabaseStep().deleteArticleCategory(body.getCategoryName());
        return ForumManagerApi.getInstant().addCategory(body);
    }

    public PostCategoryBus updateTo(PostCategoryMS oldCategory, PostCategoryMS newData){
        String categorySig = loads(oldCategory).get(0).getCategorySignature();
        newData.setCategorySignature(categorySig);
        return ForumManagerApi.getInstant().updateCategory(newData);
    }

    public Response delete(PostCategoryMS body){
        return null;
    }


    public List<PostCategoryRS> loads(PostCategoryMS body) {
        return ForumManagerApi.getInstant().loadCategories(body);
    }

}
