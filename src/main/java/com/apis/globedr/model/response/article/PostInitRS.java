package com.apis.globedr.model.response.article;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostInitRS {

    @JsonProperty("postMsg")
    PostMsgRS postMsg = new PostMsgRS();


    @JsonProperty("categories")
    List<PostCategoryRS> categories = new ArrayList();

    public String getCategorySigByName(String categoryName) {
        return getCategories().stream()
                .filter(c -> c.getCategoryName().equalsIgnoreCase(categoryName))
                .map(c -> c.getCategorySignature())
                .findFirst()
                .orElse(null);
    }
}
