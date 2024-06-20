package com.apis.globedr.model.response.connection;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ConnectionDetailRS {

    @JsonUnwrapped
    private ConnectionRS connection;

    @JsonUnwrapped
    private UserRS user;




}
