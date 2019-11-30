package pages;

import static utils.Constants.*;

public class LoginPage extends Page {
    public String getLoginFormHeaderText() {
        return getElementText(LOGIN_HEADER);
    }

    public String getErrorMessage() {
        return getElementText(ERROR_MESSAGE_FIELD);
    }

    public void setUsername(String username) {
        enterValueInTextField(USERNAME_FIELD, username);
    }

    public void setPassword(String password) {
        enterValueInTextField(PASSWORD_FIELD, password);
    }

    public DemoAppPage clickLoginButton() {
        clickButton(LOGIN_BUTTON);
        return new DemoAppPage();
    }

    public DemoAppAdsPage clickLoginButton(String page) {
        clickButton(LOGIN_BUTTON);
        return new DemoAppAdsPage();
    }
}
