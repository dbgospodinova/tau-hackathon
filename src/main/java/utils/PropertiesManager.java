package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static utils.Constants.CONFIG_PROPERTIES_FILE_PATH;
import static utils.Constants.ELEMENTS_MAPPING_PROPERTIES_FILE_PATH;

public class PropertiesManager {
    public enum PropertiesManagerEnum {
        /**
         * Singleton ENUM initialization
         */
        INSTANCE;

        Properties configProperties = loadConfig();
        Properties elementsMappingProperties = loadElementsMapping();

        /**
         * Getter for Configuration Properties
         *
         * @return
         */
        Properties getConfigProperties() {
            return configProperties;
        }

        /**
         * Getter for Elements Mapping Properties
         *
         * @return
         */
        Properties getElementsMappingProperties() {
            return elementsMappingProperties;
        }

        /**
         * Loads all of the project's properties taken from {@link CONFIG_PROPERTIES_FILE_PATH}
         */
        private static Properties loadConfig() {
            return loadProperties(CONFIG_PROPERTIES_FILE_PATH);
        }

        /**
         * Load Components Mapping from properties file
         *
         * @return
         */
        private static Properties loadElementsMapping() {
            return loadProperties(ELEMENTS_MAPPING_PROPERTIES_FILE_PATH);
        }

        /**
         * The try-with-resources statement ensures that each resource is closed at the end of the statement
         *
         * @return
         */
        private static Properties loadProperties(String url) {
            Properties properties = new Properties();
            try (FileInputStream fis = new FileInputStream(url)) {
                properties.load(fis);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return properties;
        }
    }
}
