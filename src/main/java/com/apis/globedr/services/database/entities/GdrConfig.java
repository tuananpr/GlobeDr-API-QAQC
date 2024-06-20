package com.apis.globedr.services.database.entities;

public class GdrConfig {
    private String Id;
    private String name;
    private String value;

    public GdrConfig(String id, String name, String value) {
        Id = id;
        this.name = name;
        this.value = value;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("id : " + getId());
        builder.append("name : " + getName());
        builder.append("value : " + getValue());
        return builder.toString();
    }

}
