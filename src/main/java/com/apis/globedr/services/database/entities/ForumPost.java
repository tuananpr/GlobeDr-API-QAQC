package com.apis.globedr.services.database.entities;

import java.util.Date;

public class ForumPost {



    private Integer postId;
    private Integer type;
    private Integer status;
    private Integer toApptId;
    private Date onDate;
    private Integer createdById;
    private String tags;
    private Integer categoryId;
    private Integer postSource;
    private String subject;
    private Date createdDate;
    private Integer createdByType;

    public ForumPost(){}

    public ForumPost(Integer postId, Integer type, Integer status, Integer toApptId, Date onDate, Integer createdById,
                     String tags, Integer categoryId, Integer postSource, String subject, Date createdDate, Integer createdByType) {
        this.postId = postId;
        this.type = type;
        this.status = status;
        this.toApptId = toApptId;
        this.onDate = onDate;
        this.createdById = createdById;
        this.tags = tags;
        this.categoryId = categoryId;
        this.postSource = postSource;
        this.subject = subject;
        this.createdDate = createdDate;
        this.createdByType = createdByType;
    }


    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getToApptId() {
        return toApptId;
    }

    public void setToApptId(Integer toApptId) {
        this.toApptId = toApptId;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Integer getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPostSource() {
        return postSource;
    }

    public void setPostSource(Integer postSource) {
        this.postSource = postSource;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedByType() {
        return createdByType;
    }

    public void setCreatedByType(Integer createdByType) {
        this.createdByType = createdByType;
    }


}
