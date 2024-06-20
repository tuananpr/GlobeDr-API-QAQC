package com.apis.globedr.services.database.entities;

import java.util.Date;

public class RatingReview {
    private Integer id;
    private Integer entityId;
    private Integer entityType;
    private String review;
    private Integer byUserId;
    private Date onDate;
    private Integer isHidden;
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getByUserId() {
        return byUserId;
    }

    public void setByUserId(Integer byUserId) {
        this.byUserId = byUserId;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    public RatingReview(Integer id, Integer entityId, Integer entityType, String review, Integer byUserId, Date onDate, Integer isHidden, String reason) {
        this.id = id;
        this.entityId = entityId;
        this.entityType = entityType;
        this.review = review;
        this.byUserId = byUserId;
        this.onDate = onDate;
        this.isHidden = isHidden;
        this.reason = reason;
    }

}
