package com.apis.globedr.apis;


import com.apis.globedr.services.config.Environment;
import com.apis.globedr.services.authorization.Token;

public class BaseApi {
    public Token token = Token.getInstance();

}
