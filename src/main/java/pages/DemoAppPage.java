package pages;

import java.util.List;

import static utils.Constants.*;

public class DemoAppPage extends Page {
    public void clickAmountsHeader() {
        clickButton(AMOUNTS_HEADER);
    }

    public List<String> getAmounts() {
        return getElementsText(AMOUNTS);
    }

    public List<String> getStatuses() {
        return getElementsText(STATUSES);
    }

    public List<String> getDates() {
        return getElementsText(DATES);
    }

    public List<String> getDescriptions() {
        return getElementsText(DESCRIPTIONS);
    }

    public List<String> getCategories() {
        return getElementsText(CATEGORIES);
    }

    public CanvasChartPage clickCompareExpensesButton() {
        clickButton(CANVAS_CHART_BUTTON);
        return new CanvasChartPage();
    }
}
