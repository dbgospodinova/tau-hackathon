package pages.base;

public class ClickElement extends Element {
    public void clickById(String id) {
        findElementById(id).click();
    }

    public void clickByXpath(String xpath) {
        findElementByXpath(xpath).click();
    }
}
