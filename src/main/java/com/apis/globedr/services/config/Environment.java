package com.apis.globedr.services.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Environment {

    public static Environment INSTANCE = new Environment();

    private Properties getEnvironment() {

        ClassLoader cl = this.getClass().getClassLoader();
        Properties config = new Properties();
        try {
            //config.load(cl.getResourceAsStream("env-test-api.properties"));
            config.load(new InputStreamReader(cl.getResourceAsStream("env-test-api.properties"), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    public String get(String key){
        return getEnvironment().getProperty(key);
    }


    private static boolean isExistFile(String path) {
        File f = new File(path);
        return f.exists();
    }

    private static Properties loadProperties(String path) {
        try {
            if (isExistFile(path)) {
                Properties prop = new Properties();
                BufferedReader ip = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
                prop.load(ip);
                return prop;
            }else{
                throw new Exception(String.format("Error : Not found properties file %s ", path));
            }

        } catch (IOException e) {
            System.out.println(String.format("Error while reading properties file %s: %s ", path, e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
