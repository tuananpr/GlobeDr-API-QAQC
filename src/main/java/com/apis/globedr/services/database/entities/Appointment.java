package com.apis.globedr.services.database.entities;

import java.util.Date;

public class Appointment {
    private Integer id;
    private Integer type;
    private Integer status;
    private Date createdDate;
    private Date onDate;
    private Integer byId;
    private Integer byType;
    private Integer told;
    private Integer toType;
    private String reason;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Integer getById() {
        return byId;
    }

    public void setById(Integer byId) {
        this.byId = byId;
    }

    public Integer getByType() {
        return byType;
    }

    public void setByType(Integer byType) {
        this.byType = byType;
    }

    public Integer getTold() {
        return told;
    }

    public void setTold(Integer told) {
        this.told = told;
    }

    public Integer getToType() {
        return toType;
    }

    public void setToType(Integer toType) {
        this.toType = toType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Appointment() {
    }

    public Appointment(Integer id, Integer type, Integer status, Date createdDate, Date onDate, Integer byId, Integer byType, Integer told, Integer toType, String reason) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.createdDate = createdDate;
        this.onDate = onDate;
        this.byId = byId;
        this.byType = byType;
        this.told = told;
        this.toType = toType;
        this.reason = reason;
    }


}
