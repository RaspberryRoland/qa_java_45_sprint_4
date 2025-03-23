package popUpWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SuccessPopUpWindow extends PopUpWindow{
    public final By popUpWindow = By.xpath(
            "//div[contains(@class,'Order_Modal_')]");
    private final By statusOrderText = By.xpath(
            "//div[contains(text(),'Заказ оформлен')]");

    private final WebDriver driver;

    public SuccessPopUpWindow(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public boolean checkIsOpened() {
        return driver.findElement(popUpWindow).isDisplayed();
    }

    public boolean checkOrderState() {
        return driver.findElement(statusOrderText).isDisplayed();
    }

}
