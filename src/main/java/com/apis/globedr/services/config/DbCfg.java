package com.apis.globedr.services.config;

import java.io.IOException;
import java.util.Properties;

public class DbCfg {

    public Properties getConfig(){
        ClassLoader cl = this.getClass().getClassLoader();
        Properties config = new Properties();
        try {
            config.load(cl.getResourceAsStream("database.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }


    public Properties getDBProfile(){
        Properties config = getConfig();
        Properties profileConfig = new Properties();
        profileConfig.setProperty("db.class", config.getProperty("db.class"));
        profileConfig.setProperty("db.Url", config.getProperty("db.Url"));
        profileConfig.setProperty("db.username", config.getProperty("db.username"));
        profileConfig.setProperty("db.password", config.getProperty("db.password"));
        profileConfig.setProperty("db.retry", config.getProperty("db.retry"));
        profileConfig.setProperty("db.name", config.getProperty("db.profile"));
        return profileConfig;
    }

    public Properties getDBPost(){
        Properties config = getConfig();
        Properties postConfig = new Properties();
        postConfig.setProperty("db.class", config.getProperty("db.class"));
        postConfig.setProperty("db.Url", config.getProperty("db.Url"));
        postConfig.setProperty("db.username", config.getProperty("db.username"));
        postConfig.setProperty("db.password", config.getProperty("db.password"));
        postConfig.setProperty("db.retry", config.getProperty("db.retry"));
        postConfig.setProperty("db.name", config.getProperty("db.post"));
        return postConfig;
    }


}
