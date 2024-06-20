package com.apis.globedr.model.step.article;

import com.apis.globedr.enums.CategoryType;
import com.apis.globedr.enums.PostStatus;
import com.apis.globedr.model.general.Page;
import com.apis.globedr.model.general.file.FileFactory;
import com.apis.globedr.model.general.Tag;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleMS {
    private Integer categoryType;
    private Integer language;
    private String subject;
    private String msg;
    private String orgSig;
    private Integer postId;
    private String postMsgSignature;
    private String postSignature;
    private String postTitleMsg;
    private String msgContent;
    private String orgName;

    private Integer postStatus;
    private String postTitle;
    private File files;
    private Integer appId;
    private List<Tag> inputTags;
    private Integer type;
    private String key;
    private String note;
    private String comment;
    private Boolean isSendNoti;
    private boolean isLoadMyPost;
    private String categorySignature;
    private String categoryName;
    public void setIsLoadMyPost(boolean mPost){
        this.isLoadMyPost = mPost;
    }
    public boolean getIsLoadMyPost(){
        return isLoadMyPost;
    }


    public void setFiles(String pathFile) {
        this.files = FileFactory.getFile(pathFile).getFile();
    }
    @JsonProperty("inputTags")
    public void setInputTags(String tags) {
        if (inputTags == null) inputTags = new ArrayList<Tag>();
        for (String name : tags.split(",")) {
            inputTags.add(new Tag(name));
        }
    }

    @JsonProperty("inputTags")
    public List<Tag> getInputTags() {
        return inputTags;
    }

    public void setCategoryType(String type){
        this.categoryType = CategoryType.value(type);
    }

}
