package base;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Utils;

public class EyesManager {
    private Eyes eyes;
    private String appName;
    private WebDriver driver;

    public EyesManager(WebDriver driver, String appName) {
        this.driver = driver;
        this.appName = appName;

        eyes = new Eyes();
        eyes.setApiKey(Utils.getConfigProperties().getProperty("applitools.api.key"));
    }

    public void setBatchName(String batchName) {
        eyes.setBatch(new BatchInfo(batchName));
    }

    public void validateWindow() {
        eyes.open(driver, appName,
                Thread.currentThread().getStackTrace()[2].getMethodName());

        eyes.setForceFullPageScreenshot(true);
        eyes.checkWindow();
        eyes.close();
    }

    public void validateElement(By locator) {
        eyes.open(driver, appName,
                Thread.currentThread().getStackTrace()[2].getMethodName());

        eyes.checkElement(locator);
        eyes.close();
    }

    public void validateFrame(String locator) {
        eyes.open(driver, appName,
                Thread.currentThread().getStackTrace()[2].getMethodName());

        eyes.checkFrame(locator);
        eyes.close();
    }

    public void abort() {
        eyes.abortIfNotClosed();
    }

    public Eyes getEyes() {
        return eyes;
    }
}

