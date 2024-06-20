package com.apis.globedr.model.response.article;

import com.apis.globedr.business.article.PostCategoryBus;
import com.apis.globedr.business.article.PostCategoryManage;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;
import lombok.experimental.Accessors;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)

public class LoadPostDetailRS {
//    @JsonUnwrapped
//    private Post post = new Post();


    @Builder.Default
    @JsonUnwrapped
    private PostCategoryRS postCategory = new PostCategoryRS();

    @Builder.Default
    @JsonAlias({"myMsg", "msg"})
    private PostMsgRS postMsg = new PostMsgRS();

    private String postSignature;
    private String createdSig;
    private String title;
    private String onDate;
    private String createdName;
    private Boolean isRequestHot;
    private Integer postId;

}
