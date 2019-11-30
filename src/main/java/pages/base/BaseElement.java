package pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Utils;

import java.util.List;


/**
 * Base Element class implements different findElementBy*
 * methods ordered ascending alphabetically
 *
 * @return
 */
public class BaseElement {
    private transient WebDriver driver = Utils.getWebDriver();

    public WebElement findElementById(String id) {
        return driver.findElement(By.id(id));
    }

    public WebElement findElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public WebElement findElementByClass(String className) {
        return driver.findElement(By.className(className));
    }

    public List<WebElement> findElementsById(String id) {
        return driver.findElements(By.id(id));
    }

    public List<WebElement> findElementsByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath));
    }
    public List<WebElement> findElementsByClass(String className) {
        return driver.findElements(By.className(className));
    }

}
