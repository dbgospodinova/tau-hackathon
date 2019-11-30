import base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.CanvasChartPage;
import pages.DemoAppAdsPage;
import pages.DemoAppPage;

import java.io.IOException;

import static utils.Constants.*;

public class VisualAITests extends BaseTest {
    @BeforeClass
    public static void startVisualTestSuite() {
        eyesManager.setBatchName("Visual Tests");
    }

    @Test
    public void loginPageUIElementsTest() {
        // Arrange and // Act
        loginPage.clickLoginButton();

        // Assert
        eyesManager.validateWindow();
    }

    @Test(dataProvider = "parameterLoginData")
    public void loginDataDrivenTest(String username, String password, String message) {
        // Arrange
        loginPage.setUsername(username);
        loginPage.setPassword(password);

        // Act
        DemoAppPage demoAppPage = loginPage.clickLoginButton();

        // Assert
        eyesManager.validateWindow();
    }

    @Test
    public void tableSortTest() {
        // Arrange
        loginPage.setUsername(USERNAME_FIELD);
        loginPage.setPassword(PASSWORD_FIELD);
        DemoAppPage demoAppPage = loginPage.clickLoginButton();

        // Act
        demoAppPage.clickAmountsHeader();

        // Assert
        eyesManager.validateWindow();
    }

    @Test
    public void canvasChartTest() throws IOException {
        // Arrange
        loginPage.setUsername(USERNAME_FIELD);
        loginPage.setPassword(PASSWORD_FIELD);
        DemoAppPage demoAppPage = loginPage.clickLoginButton();
        CanvasChartPage canvasChartPage = demoAppPage.clickCompareExpensesButton();
        // Assert
        //eyesManager.validateWindow();

        // Act
        canvasChartPage.clickShowDataForNextYear();

        // Assert
        eyesManager.validateWindow();
    }

    @Test
    public void dynamicContentTest() {
        // Arrange
        loginPage.openPage(LOGIN_PAGE_ADS);
        loginPage.setUsername(USERNAME_FIELD);
        loginPage.setPassword(PASSWORD_FIELD);

        // Act
        DemoAppAdsPage demoAppAdsPage = loginPage.clickLoginButton(LOGIN_PAGE_ADS);

        // Assert
        eyesManager.validateWindow();
    }
}
