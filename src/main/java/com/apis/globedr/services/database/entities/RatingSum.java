package com.apis.globedr.services.database.entities;

public class RatingSum {
    private Integer id;
    private Integer mgtRatingReviewId;
    private Integer entityId;
    private Integer entityType;
    private Integer byUserId;
    private Integer questionId;
    private Integer score;
    private Integer isVisible;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMgtRatingReviewId() {
        return mgtRatingReviewId;
    }

    public void setMgtRatingReviewId(Integer mgtRatingReviewId) {
        this.mgtRatingReviewId = mgtRatingReviewId;
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

    public Integer getByUserId() {
        return byUserId;
    }

    public void setByUserId(Integer byUserId) {
        this.byUserId = byUserId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getScore() {
        return Integer.toString(score);
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Integer isVisible) {
        this.isVisible = isVisible;
    }

    public RatingSum(Integer id, Integer mgtRatingReviewId, Integer entityId, Integer entityType, Integer byUserId, Integer questionId, Integer score, Integer isVisible) {
        this.id = id;
        this.mgtRatingReviewId = mgtRatingReviewId;
        this.entityId = entityId;
        this.entityType = entityType;
        this.byUserId = byUserId;
        this.questionId = questionId;
        this.score = score;
        this.isVisible = isVisible;
    }
}
