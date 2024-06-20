package com.apis.globedr.services.authorization;


import com.rest.core.request.authorization.BasicAuthorization;

public class Basic extends BasicAuthorization {

    private static Basic instance;

    public static Basic getInstance() {
        if (instance == null) {
            instance = new Basic();
        }
        return instance;
    }

    @Override
    public void save(String user, String pass) {
        this.setUser(user);
        this.setPass(pass);
    }


}
