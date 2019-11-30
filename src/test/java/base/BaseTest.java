package base;

import com.google.common.io.Files;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.LoginPage;
import utils.Utils;

import java.io.File;
import java.io.IOException;

import static utils.Constants.*;

public class BaseTest {
    private WebDriver driver;
    protected LoginPage loginPage;
    protected static EyesManager eyesManager;

    @BeforeClass
    public void setUp() {
        driver = Utils.getWebDriver();
        if (Utils.getConfigProperties().getProperty("default.browser").equals("firefox")) {
            driver.manage().window().maximize();
        }

        eyesManager = new EyesManager(driver, "TAU Hackathon");
        eyesManager.getEyes().setForceFullPageScreenshot(true);
    }

    @BeforeMethod
    public void openHomePage() {
        loginPage = new LoginPage();
        loginPage.openPage(LOGIN_PAGE);
    }

    @AfterMethod
    public void recordFailure(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            TakesScreenshot camera = (TakesScreenshot) driver;
            File screenshot = camera.getScreenshotAs(OutputType.FILE);
            System.out.println("Screenshot taken: " + screenshot.getAbsolutePath());
            try {
                Files.move(screenshot, new File("resources/screenshots/failed_" + result.getName() + ".png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @AfterClass
    public void terminateDriver() {
        if (driver != null) {
            Utils.browserClose();
        }
        eyesManager.abort();
    }

    /**
     * This function will provide the parameter data
     * rows - number of time test has to be repeated
     * cols - number of parameters in test data
     *
     * @return stored data in Object array
     */
    @DataProvider
    protected Object[][] parameterLoginData() {

        Object[][] data = new Object[4][3];

        data[0][0] = EMPTY_FIELD;
        data[0][1] = EMPTY_FIELD;
        data[0][2] = "Both Username and Password must be present";

        data[1][0] = USERNAME_FIELD;
        data[1][1] = EMPTY_FIELD;
        data[1][2] = "Password must be present";

        data[2][0] = EMPTY_FIELD;
        data[2][1] = PASSWORD_FIELD;
        data[2][2] = "Username must be present";

        data[3][0] = USERNAME_FIELD;
        data[3][1] = PASSWORD_FIELD;
        data[3][2] = DEMO_PAGE;

        return data;
    }

    @DataProvider
    protected Object[][] parameterLoginDataV2() {

        Object[][] data = new Object[4][3];

        data[0][0] = EMPTY_FIELD;
        data[0][1] = EMPTY_FIELD;
        data[0][2] = "Please enter both username and password";

        data[1][0] = USERNAME_FIELD;
        data[1][1] = EMPTY_FIELD;
        data[1][2] = "Password must be present";

        data[2][0] = EMPTY_FIELD;
        data[2][1] = PASSWORD_FIELD;
        data[2][2] = "Username must be present";

        data[3][0] = USERNAME_FIELD;
        data[3][1] = PASSWORD_FIELD;
        data[3][2] = DEMO_ADS_PAGE;

        return data;
    }
}
