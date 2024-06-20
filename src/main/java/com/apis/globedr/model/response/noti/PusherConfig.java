package com.apis.globedr.model.response.noti;



import java.io.Serializable;

public class PusherConfig implements Serializable {
    private String key;
    private String authEndpoint;
    private Boolean encrypted;
    private String cluster;

    public PusherConfig(){}
    public PusherConfig(String key, String authEndpoint, Boolean encrypted, String cluster) {
        this.key = key;
        this.authEndpoint = authEndpoint;
        this.encrypted = encrypted;
        this.cluster = cluster;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuthEndpoint() {
        return authEndpoint;
    }

    public void setAuthEndpoint(String authEndpoint) {
        this.authEndpoint = authEndpoint;
    }

    public Boolean getEncrypted() {
        return encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }
}
