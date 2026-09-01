package org.unibl.etf.faculty_app.util;

import java.io.*;
import java.util.*;

public class Config {
	private static final Properties properties = new Properties();
	
	static {
	    try (InputStream input = Config.class.getClassLoader().getResourceAsStream("../application.properties")) {
	        if (input != null) {
	            properties.load(input);
	        } else {
	            System.err.println("The application.properties file was not found on the classpath");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public static String getProperty(String key) {
	    return properties.getProperty(key);
	}
}
