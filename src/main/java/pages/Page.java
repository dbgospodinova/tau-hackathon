package pages;

import com.google.common.io.Files;
import org.openqa.selenium.*;
import pages.base.BaseElement;
import pages.elements.Button;
import pages.elements.InputTextField;
import pages.elements.TextField;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.getKey;

public class Page extends BaseElement {
    private WebDriver driver;
    private Button button;
    private TextField textField;
    private InputTextField inputTextField;

    public Page() {
        this.driver = Utils.getWebDriver();
        this.button = new Button();
        this.textField = new TextField();
        this.inputTextField = new InputTextField();
    }

    public void openPage(String page) {
        driver.get(Utils.getElementsMappingProperties()
                .getProperty(page));
    }

    public String getPage() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getElementText(String identifier) {
        String key = getKey(identifier);
        String[] keys = key.split("\\.");
        String text = "";

        switch (keys[1]) {
            case "id":
                text = textField.getTextById(Utils.getElementsMappingProperties().getProperty(key));
                break;
            case "class":
                text = textField.getTextByClass(Utils.getElementsMappingProperties().getProperty(key));
                break;
            case "xpath":
                text = textField.getTextByXpath(Utils.getElementsMappingProperties().getProperty(key));
                break;
            default:
                throw new NoSuchElementException(key + " doesn't exist");
        }

        return text;
    }

    public List<String> getElementsText(String identifier) {
        String key = getKey(identifier);
        List<String> collection = new ArrayList<>();
        boolean elementExists = true;
        int counter = 1;

        while (elementExists) {
            try {
                collection.add(textField.getTextByXpath
                        ("(" + Utils.getElementsMappingProperties().getProperty(key) + ")[" + counter + "]"));
                counter++;
            } catch (NoSuchElementException ex) {
                elementExists = false;
            }
        }

        return collection;
    }

    public void clickButton(String identifier) {
        String key = getKey(identifier);
        String[] keys = key.split("\\.");
        switch (keys[1]) {
            case "id":
                button.clickById(Utils.getElementsMappingProperties().getProperty(key));
                break;
            case "xpath":
                button.clickByXpath(Utils.getElementsMappingProperties().getProperty(key));
                break;
            default:
                throw new NoSuchElementException(key + " doesn't exist");
        }
    }

    public void enterValueInTextField(String identifier, String value) {
        String key = getKey(identifier);
        String[] keys = key.split("\\.");

        switch (keys[1]) {
            case "id":
                inputTextField.enterValueById(Utils.getElementsMappingProperties().getProperty(key), value);
                break;
            case "xpath":
                inputTextField.enterValueByXPath(Utils.getElementsMappingProperties().getProperty(key), value);
                break;
            default:
                throw new NoSuchElementException(key + " doesn't exist");
        }
    }

    public boolean elementExists(String element) {
        String key = getKey(element);
        String[] keys = key.split("\\.");
        List<WebElement> elements;

        switch (keys[1]) {
            case "id":
                elements = findElementsById(Utils.getElementsMappingProperties().getProperty(key));
                break;
            case "xpath":
                elements = findElementsByXpath(Utils.getElementsMappingProperties().getProperty(key));
                break;
            case "class":
                elements = findElementsByClass(Utils.getElementsMappingProperties().getProperty(key));
                break;
            default:
                throw new NoSuchElementException(key + " doesn't exist");
        }

        return !elements.isEmpty();
    }

    public String getElementAttributeText(String element, String attribute) {
        String key = getKey(element);
        String[] keys = key.split("\\.");
        String text = "";

        switch (keys[1]) {
            case "id":
                text = findElementById(Utils.getElementsMappingProperties().getProperty(key)).getAttribute(attribute);
                break;
            case "xpath":
                text = findElementByXpath(Utils.getElementsMappingProperties().getProperty(key)).getAttribute(attribute);
                break;
            default:
                throw new NoSuchElementException(key + " doesn't exist");
        }

        return text;
    }

    public String getElementLabel(String element) {
        String key = getKey(element);
        String[] keys = key.split("\\.");
        String label = "";

        switch (keys[1]) {
            case "id":
                label = findElementById(Utils.getElementsMappingProperties().getProperty(key)).getText();
                break;
            case "xpath":
                label = findElementByXpath(Utils.getElementsMappingProperties().getProperty(key)).getText();
                break;
            default:
                throw new NoSuchElementException(key + " doesn't exist");
        }
        return label;
    }

    public boolean verifyChart(String fileName, String element) throws IOException {
        String key = getKey(element);
        return compareCharts(fileName, findElementById(Utils.getElementsMappingProperties().getProperty(key)));
    }


    // Common method to compare the baseline image against actual
    private boolean compareCharts(String fileName, WebElement element) throws IOException {
        TakesScreenshot camera = (TakesScreenshot) driver;
        File screenshot = camera.getScreenshotAs(OutputType.FILE);
        System.out.println("Screenshot taken: " + screenshot.getAbsolutePath());
        try {
            Files.move(screenshot, new File("resources/result/" + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        BufferedImage imgA = null;
        BufferedImage imgB = null;

        try {
            File fileA = new File("resources/result/" + fileName);
            File fileB = new File("resources/snap/" + fileName);

            imgA = ImageIO.read(fileA);
            imgB = ImageIO.read(fileB);
        } catch (IOException e) {
            System.out.println(e);
        }


        if (imgA == null || imgB == null) {
            return false;
        }

        return bufferedImagesEqual(imgA, imgB);
    }

    boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public void waitPageToLoad() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


