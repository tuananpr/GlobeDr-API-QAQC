package com.apis.globedr.services.config;

import java.io.IOException;
import java.util.Properties;

public class RedisCfg {

    private Properties getConfig() {
        ClassLoader cl = this.getClass().getClassLoader();
        Properties config = new Properties();
        try {
            config.load(cl.getResourceAsStream("redis.properties"));
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

}
