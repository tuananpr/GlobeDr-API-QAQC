package com.apis.globedr.services.database.entities;

public class Sponsor {
    private int id;
    private String description;
    private int orgId;
    private int weight;
    private int value;
    private int currency;
    private int type;
    private int status;

    public Sponsor(int id, String description, int orgId, int weight, int value, int currency, int type, int status) {
        this.id = id;
        this.description = description;
        this.orgId = orgId;
        this.weight = weight;
        this.value = value;
        this.currency = currency;
        this.type = type;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
