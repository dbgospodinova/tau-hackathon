package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

import static utils.Constants.DEFAULT_BROWSER;
import static utils.OptionsManager.getChromeOptions;
import static utils.OptionsManager.getFirefoxOptions;

public class WebDriverManager {
    public enum WebDriverManagerEnum {
        INSTANCE;

        private WebDriver driver;

        WebDriver getDriver() {
            if (driver == null) {
                driver = setupBrowser();
            }
            return driver;
        }

        void tearDown() {
            if (driver != null) {
                driver.quit();
            }
            driver = null;
        }

        private WebDriver setupBrowser() {
            Properties loginConfiguration = Utils.getConfigProperties();
            String browser = System.getProperty(DEFAULT_BROWSER) == null ? loginConfiguration.getProperty(DEFAULT_BROWSER) : null;
            Constants.Browsers browserName;
            try {
                browserName = Constants.Browsers.valueOf(browser);
            } catch (NullPointerException ne) {
                browserName = Constants.Browsers.firefox;
            } catch (IllegalArgumentException ie) {
                browserName = Constants.Browsers.firefox;
            }

            switch (browserName) {
                case firefox:
                    System.setProperty("webdriver.gecko.driver", Utils.getConfigProperties().getProperty(browserName.toString()));
                    //FirefoxDriverManager.getInstance().setup();
                    return new FirefoxDriver(getFirefoxOptions());
                case chrome:
                    System.setProperty("webdriver.chrome.driver", Utils.getConfigProperties().getProperty(browserName.toString()));
                    return new ChromeDriver(getChromeOptions());
                default:
                    throw new IllegalStateException("No enum constant " + browserName);
            }
        }
    }
}
