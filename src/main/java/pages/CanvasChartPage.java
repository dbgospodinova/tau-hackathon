package pages;

import java.io.IOException;

import static utils.Constants.CANVAS_CHART;
import static utils.Constants.DATA_NEXT_YEAR_BUTTON;

public class CanvasChartPage extends Page {
    public void clickShowDataForNextYear() {
        clickButton(DATA_NEXT_YEAR_BUTTON);
    }

    public boolean verifyCanvasChart(String canvasChartPicture) throws IOException {
        return verifyChart(canvasChartPicture, CANVAS_CHART);
    }

}
