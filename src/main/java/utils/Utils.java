package utils;

import org.openqa.selenium.WebDriver;

import java.util.Properties;

public class Utils {
    /**
     * Returns the project's WebDriver
     */
    public static WebDriver getWebDriver() {
        return WebDriverManager.WebDriverManagerEnum.INSTANCE.getDriver();
    }

    public static void browserClose() {
        WebDriverManager.WebDriverManagerEnum.INSTANCE.tearDown();
    }

    /**
     * Return all properties read from the project's configuration file: config.properties
     */
    public static Properties getConfigProperties() {
        return PropertiesManager.PropertiesManagerEnum.INSTANCE.getConfigProperties();
    }

    /**
     * Return all properties read from the project's configuration file: components_mapping.properties
     */
    public static Properties getElementsMappingProperties() {
        return PropertiesManager.PropertiesManagerEnum.INSTANCE.getElementsMappingProperties();
    }

    public static String getKey(String elementName) {
        String key = Utils.getElementsMappingProperties()
                .keySet()
                .stream()
                .filter(s -> s.toString().startsWith(elementName + "."))
                .findFirst().toString();
        key = key.replace("Optional[",
                "").replace("]", "");

        return key;
    }
}
