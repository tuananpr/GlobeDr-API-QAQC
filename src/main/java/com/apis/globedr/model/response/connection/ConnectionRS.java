package com.apis.globedr.model.response.connection;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionRS {
    private Integer connectionId;
    private String connectionSig;
    private Integer connectionStatus;
}
