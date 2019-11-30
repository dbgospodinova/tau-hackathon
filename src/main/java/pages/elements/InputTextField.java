package pages.elements;


import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import pages.base.Element;

public class InputTextField extends Element {
    public void enterValueById(String id, String value) {
        enterValueInWebElement(findElementById(id), value, null);
    }

    public void enterValueByXPath(String xpath, String value) {
        enterValueInWebElement(findElementByXpath(xpath), value, null);
    }

    public void enterValueByXPathAndPressKey(String xpath, String value, String key) {
        enterValueInWebElement(findElementByXpath(xpath), value, key);
    }

    private void enterValueInWebElement(WebElement textField, String value, String key) {
        textField.click();

        if (key != null) {
            String uppercase = key.toUpperCase();
            textField.sendKeys(value, Keys.valueOf(uppercase));
        } else {
            textField.sendKeys(value);
        }
    }
}
