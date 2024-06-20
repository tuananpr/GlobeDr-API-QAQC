package com.apis.globedr.services.database.entities;

public class UserMedicalDoc {
    private Integer docId;
    private String description;
    private String fileExt;
    private String search_description;
    private String text1;
    private String text2;
    private String text3;
    private Integer docType;
    private Integer attributes;
    private Integer version;
    private String onDate;
    private String createDate;
    private Integer userId;
    private Integer refId;

    public UserMedicalDoc(Integer docId, String description, String fileExt, String search_description, String text1, String text2, String text3, Integer docType, Integer attributes, Integer version, String onDate, String createDate, Integer userId, Integer refId) {
        this.docId = docId;
        this.description = description;
        this.fileExt = fileExt;
        this.search_description = search_description;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.docType = docType;
        this.attributes = attributes;
        this.version = version;
        this.onDate = onDate;
        this.createDate = createDate;
        this.userId = userId;
        this.refId = refId;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getSearch_description() {
        return search_description;
    }

    public void setSearch_description(String search_description) {
        this.search_description = search_description;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public Integer getDocType() {
        return docType;
    }

    public void setDocType(Integer docType) {
        this.docType = docType;
    }

    public Integer getAttributes() {
        return attributes;
    }

    public void setAttributes(Integer attributes) {
        this.attributes = attributes;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOnDate() {
        return onDate;
    }

    public void setOnDate(String onDate) {
        this.onDate = onDate;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }
}
