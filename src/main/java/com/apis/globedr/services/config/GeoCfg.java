package com.apis.globedr.services.config;

import java.io.IOException;
import java.util.Properties;

public class GeoCfg {

    private Properties getConfig() {
        ClassLoader cl = this.getClass().getClassLoader();
        Properties config = new Properties();
        try {
            config.load(cl.getResourceAsStream("geo.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }


    public String get(String key) {
        return getConfig().getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(getConfig().getProperty(key));
    }


    public String urlGoogle(String key) {
        return getConfig().getProperty(key);
    }


    public String getGoogleUri() {
        String domain = get("google.uri");
        String apiVersion = get("google.version");
        return domain + apiVersion;
    }

    public String getOpenCaseUri() {
        String domain = get("Open.cage.uri");
        String apiVersion = "";
        return domain + apiVersion;
    }


}
