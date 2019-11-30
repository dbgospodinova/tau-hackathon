package pages.base;

public class Element extends BaseElement {

    public String getTextById(String id) {
        return findElementById(id).getText();
    }

    public String getTextByXpath(String xpath) {
        return findElementByXpath(xpath).getText();
    }

    public String getTextByClass(String className) {
        return findElementByClass(className).getText();
    }
}