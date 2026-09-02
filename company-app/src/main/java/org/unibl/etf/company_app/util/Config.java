package org.unibl.etf.company_app.util;

import java.io.*;
import java.util.*;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("../application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                try (InputStream directInput = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
                    if (directInput != null) {
                        properties.load(directInput);
                    } else {
                        System.err.println("The application.properties file was not found on the classpath");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
