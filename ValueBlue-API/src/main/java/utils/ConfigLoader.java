package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties;

    public ConfigLoader() {
        properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Configurations/Config.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
                LogClass.info("Configuration loaded successfully from config.properties");
            } else {
                LogClass.error("config.properties file not found in classpath");
                throw new RuntimeException("config.properties file not found in classpath");
            }
        } catch (IOException e) {
            LogClass.error("Failed to load configuration from config.properties", e);
            throw new RuntimeException("Failed to load configuration from config.properties", e);
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            LogClass.warn("Property for key '" + key + "' not found in config.properties");
        }
        return value;
    }
}
