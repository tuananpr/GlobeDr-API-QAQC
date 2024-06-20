package com.apis.globedr.services.database.entities;

import java.util.Date;

public class Notification {
    private Integer notificationId;
    private Integer type;
    private Integer senderId;
    private Date onDate;
    private Integer refId1;
    private Integer refId2;
    private Integer refId3;
    private Integer importance;
    private String message;
    public Notification(){}



    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Integer getRefId1() {
        return refId1;
    }

    public void setRefId1(Integer refId1) {
        this.refId1 = refId1;
    }

    public Integer getRefId2() {
        return refId2;
    }

    public void setRefId2(Integer refId2) {
        this.refId2 = refId2;
    }

    public Integer getRefId3() {
        return refId3;
    }

    public void setRefId3(Integer refId3) {
        this.refId3 = refId3;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
