package com.apis.globedr.services.database.entities;

public class OrgScheduleTime {
    private Integer id;



    private Integer orgId;
    private Integer appointmentType;
    private Integer weekday;



    private Integer fromMinute;
    private Integer toMinute;

    private Integer fromHour;
    private Integer toHour;

    private Integer timeType;
    private Integer isVisble;


    public OrgScheduleTime(Integer id, Integer orgId, Integer appointmentType, Integer weekday, Integer fromMinute, Integer toMinute, Integer fromHour, Integer toHour, Integer timeType, Integer isVisble) {
        this.id = id;
        this.orgId = orgId;
        this.appointmentType = appointmentType;
        this.weekday = weekday;
        this.fromMinute = fromMinute;
        this.toMinute = toMinute;
        this.fromHour = fromHour;
        this.toHour = toHour;
        this.timeType = timeType;
        this.isVisble = isVisble;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(Integer appointmentType) {
        this.appointmentType = appointmentType;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public void setWeekday(Integer weekday) {
        this.weekday = weekday;
    }

    public Integer getFromMinute() {
        return fromMinute;
    }

    public void setFromMinute(Integer fromMinute) {
        this.fromMinute = fromMinute;
    }

    public Integer getToMinute() {
        return toMinute;
    }

    public void setToMinute(Integer toMinute) {
        this.toMinute = toMinute;
    }

    public Integer getFromHour() {
        return fromHour;
    }

    public void setFromHour(Integer fromHour) {
        this.fromHour = fromHour;
    }

    public Integer getToHour() {
        return toHour;
    }

    public void setToHour(Integer toHour) {
        this.toHour = toHour;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public Integer getIsVisble() {
        return isVisble;
    }

    public void setIsVisble(Integer isVisble) {
        this.isVisble = isVisble;
    }
}
