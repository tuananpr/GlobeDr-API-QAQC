package com.apis.globedr.services.database.entities;

public class OrgRoomSchedule {
    private Integer orgId;
    private Integer roomId;
    private Integer userId;



    private Integer weekday;
    private String roomName;
    private Integer specialtyCode;

    public OrgRoomSchedule(Integer orgId, Integer roomId, Integer userId, Integer weekday, String roomName, Integer specialtyCode) {
        this.orgId = orgId;
        this.roomId = roomId;
        this.userId = userId;
        this.weekday = weekday;
        this.roomName = roomName;
        this.specialtyCode = specialtyCode;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getSpecialtyCode() {
        return specialtyCode;
    }

    public void setSpecialtyCode(Integer specialtyCode) {
        this.specialtyCode = specialtyCode;
    }
}
