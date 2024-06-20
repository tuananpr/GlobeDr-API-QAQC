package com.apis.globedr.model.request.connection;


import com.fasterxml.jackson.annotation.JsonAlias;

public class RequestConnectionsRQ {
    @JsonAlias({"userSig"})
    private String toUserSig;
    private String deviceId;
}
