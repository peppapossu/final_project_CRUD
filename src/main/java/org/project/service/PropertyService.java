package org.project.service;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class PropertyService {
    private Properties properties = new Properties();

    public PropertyService() {
        loadProperties();
    }

    private static final String PATH_TO_FILE = "config.properties";

    private void loadProperties() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PATH_TO_FILE)) {
            this.properties.load(inputStream);
            return;
        } catch (IOException e) {
            System.err.println("Failed to load properties from " + PATH_TO_FILE);
        }
        throw new RuntimeException("Properties not loaded");
    }

}
