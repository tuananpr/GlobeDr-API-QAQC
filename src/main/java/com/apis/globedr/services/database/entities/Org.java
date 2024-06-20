package com.apis.globedr.services.database.entities;

public class Org {
    private int organizationId;
    private String name1;
    private int type;
    private String address;
    public Org(){}

    public Org(int organizationId, String name1, int type, String address) {
        this.organizationId = organizationId;
        this.name1 = name1;
        this.type = type;
        this.address = address;
    }


    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
