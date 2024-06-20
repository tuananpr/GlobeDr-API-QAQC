package com.apis.globedr.model.response.appointment;

public class Doctor {

    private String avatar;
    private String name;
    private String signature;
    private String specialties;
    private String title;
    private int userId;

    public Doctor(){}
    public Doctor(String avatar, String name, String signature, String specialties, String title, int userId) {
        this.avatar = avatar;
        this.name = name;
        this.signature = signature;
        this.specialties = specialties;
        this.title = title;
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSpecialties() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties = specialties;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
