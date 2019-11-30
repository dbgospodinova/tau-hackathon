import base.BaseTest;
import org.testng.annotations.Test;
import pages.CanvasChartPage;
import pages.DemoAppAdsPage;
import pages.DemoAppPage;
import utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;
import static utils.Constants.*;

public class TraditionalTestsV2 extends BaseTest {
    @Test
    public void loginPageUIElementsTest() {
        // Arrange and // Act
        loginPage.clickLoginButton();

        // Assertions
        // Assert Login Form header
        assertEquals(loginPage.getLoginFormHeaderText(), "Logout Form",
                "Login Form header doesn't match");

        // Assert the error text
        assertEquals(loginPage.getErrorMessage(), "Please enter both username and password",
                "Error message doesn't match");

        // Assert if username exists
        assertTrue(loginPage.elementExists(USERNAME_FIELD), "Username field doesn't exist");
        // Assert username placeholder text
        assertEquals(loginPage.getElementAttributeText(USERNAME_FIELD, "placeholder"),
                "John Smith", "Username placeholder doesn't match");
        // Assert username label exists
        assertEquals(loginPage.getElementLabel(USERNAME_FIELD_LABEL), "Username",
                "Username label doesn't match");

        // Assert if password exists
        assertTrue(loginPage.elementExists(PASSWORD_FIELD), "Password field doesn't exist");
        // Assert password placeholder text
        assertEquals(loginPage.getElementAttributeText(PASSWORD_FIELD, "placeholder"),
                "ABC$*1@", "Password placeholder doesn't match");
        // Assert password label exists
        assertEquals(loginPage.getElementLabel(PASSWORD_FIELD_LABEL), "Pwd",
                "Password label doesn't match");

        // Assert if Log In button field exists
        assertTrue(loginPage.elementExists(LOGIN_BUTTON), "Login button doesn't exist");

        // Assert Remember Me checkbox exists
        assertTrue(loginPage.elementExists("remember_me"), "Remember Me checkbox doesn't exist");
        // Assert if Remember Me text exists
        assertEquals(loginPage.getElementLabel("remember_me_label"), "Remember Me",
                "Remember Me text doesn't exist");

        // Assert if Twitter button exists
        assertTrue(loginPage.elementExists("twitter2"), "Twitter button doesn't exist");
        // Assert if Facebook button exists
        assertTrue(loginPage.elementExists("facebook2"), "Facebook button doesn't exist");
        // Assert if LinkedIn button exists - Button doesn't exist in version2
        //assertTrue(loginPage.elementExists("linkedIn"), "LinkedIn button doesn't exist");

        // Assert page title
        assertEquals(loginPage.getTitle(), "ACME demo app", "Title doesn't match");
    }

    @Test(dataProvider = "parameterLoginDataV2")
    public void loginDataDrivenTest(String username, String password, String message) {
        // Arrange
        loginPage.setUsername(username);
        loginPage.setPassword(password);

        // Act
        DemoAppAdsPage demoAppPage = loginPage.clickLoginButton("V2");

        // Assert
        if (username.equals("") && password.equals("")) {
            assertEquals(loginPage.getErrorMessage(), message, "Message doesn't match");
        } else if (password.equals("")) {
            assertFalse(demoAppPage.elementExists(ERROR_MESSAGE_FIELD), "Message exists");
        } else if (username.equals("")) {
            assertEquals(loginPage.getErrorMessage(), message, "Message doesn't match");
        } else {
            assertEquals(demoAppPage.getPage(), message, "Unsuccessful login");
        }
    }

    @Test
    public void tableSortTest() {
        // Arrange
        loginPage.setUsername(USERNAME_FIELD);
        loginPage.setPassword(PASSWORD_FIELD);
        DemoAppPage demoAppPage = loginPage.clickLoginButton();
        List<String> statuses = demoAppPage.getStatuses();
        List<String> dates = demoAppPage.getDates();
        List<String> descriptions = demoAppPage.getDescriptions();
        List<String> categories = demoAppPage.getCategories();
        Collections.sort(statuses);
        Collections.sort(dates);
        Collections.sort(descriptions);
        Collections.sort(categories);

        // Act
        demoAppPage.clickAmountsHeader();
        List<String> amounts = demoAppPage.getAmounts();

        List<String> result = new ArrayList<>(amounts);
        List<String> negatives = new ArrayList<>();
        int index = 0;
        for (String amount : result) {
            if (amount.startsWith("-")) {
                negatives.add(amount);
            }
        }

        result.removeAll(negatives);
        result.addAll(0, negatives);

        List<String> statusesUpdated = demoAppPage.getStatuses();
        List<String> datesUpdated = demoAppPage.getDates();
        List<String> descriptionsUpdated = demoAppPage.getDescriptions();
        List<String> categoriesUpdated = demoAppPage.getCategories();
        Collections.sort(statusesUpdated);
        Collections.sort(datesUpdated);
        Collections.sort(descriptionsUpdated);
        Collections.sort(categoriesUpdated);

        // Assert
        assertTrue(amounts.containsAll(result) && amounts.size() == result.size(),
                "Amounts are not sorted in natural order");
        assertTrue(statuses.containsAll(statusesUpdated) && statuses.size() == statusesUpdated.size(),
                "Column Status is changed");
        assertTrue(dates.containsAll(datesUpdated) && dates.size() == datesUpdated.size(),
                "Column Date is changed");
        assertTrue(descriptions.containsAll(descriptionsUpdated) && descriptions.size() == descriptionsUpdated.size(),
                "Column Description is changed");
        assertTrue(categories.containsAll(categoriesUpdated) && categories.size() == categoriesUpdated.size(),
                "Column Category is changed");
    }

    /**
     * Test will fail on the first execution. It needs two execution to take accurate screenshots.
     * For each execution move the screenshot from screenshots to snap and rename it.
     * Screenshots - 'browserName'_chart_baseline.png and 'browserName'_next_year_chart_baseline.png
     *
     * @throws IOException
     */
    @Test
    public void canvasChartTest() throws IOException {
        // Arrange
        loginPage.setUsername(USERNAME_FIELD);
        loginPage.setPassword(PASSWORD_FIELD);
        DemoAppPage demoAppPage = loginPage.clickLoginButton();
        CanvasChartPage canvasChartPage = demoAppPage.clickCompareExpensesButton();
        canvasChartPage.waitPageToLoad();

        boolean compareImages = canvasChartPage.verifyCanvasChart
                (Utils.getConfigProperties().getProperty("default.browser") + "_chart_baseline_v2.png");
        assertTrue(compareImages);

        // Act
        canvasChartPage.clickShowDataForNextYear();
        canvasChartPage.waitPageToLoad();

        // Assert

        compareImages = canvasChartPage.verifyCanvasChart
                (Utils.getConfigProperties().getProperty("default.browser") + "_next_year_chart_baseline_v2.png");
        assertTrue(compareImages);
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
        assertFalse(demoAppAdsPage.elementExists(DYNAMIC_IMAGE_ONE), "Image exists");
        assertTrue(demoAppAdsPage.elementExists(DYNAMIC_IMAGE_TWO), "Image doesn't exist");
    }
}
