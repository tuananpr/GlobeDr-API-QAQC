package com.apis.globedr.services.authorization;

import com.apis.globedr.services.config.Environment;
import com.rest.core.RestCore;
import com.rest.core.request.authorization.TokenAuthorization;
import com.rest.core.request.body.JsonBody;
import com.rest.core.response.Response;

import java.util.HashMap;
import java.util.Map;

public class Token extends TokenAuthorization {

    private static Token instance;

    public static Token getInstance() {
        if (instance == null) {
            instance = new Token();
        }
        return instance;
    }

    @Override
    public void refresh() {
        Map<String, Object> body = new HashMap<>();
        body.put("accessToken", this.getAccessToken());
        body.put("RefreshToken", this.getRefreshToken());
        body.put("deviceId", new Environment().get("deviceId"));

        Response response = RestCore.given().auth(null)
                .body(new JsonBody(body))
                .sendWithoutRefreshToken();

        save(
                response.extract("data.accessToken"),
                response.extract("data.refreshToken"),
                response.extract("data.tokenType")
        );

    }

    public void clear() {
        //instance = new Token();
        save("", "", "");
    }

    @Override
    public void save(String accessToken, String refreshToken, String tokenType) {
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        setTokenType(tokenType);
    }


}
