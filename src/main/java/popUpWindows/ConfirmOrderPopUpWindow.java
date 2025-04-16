package popUpWindows;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmOrderPopUpWindow extends PopUpWindow{
    private final By popUpWindow = By.xpath(
            "//div[contains(@class,'Order_Modal_')]");
    private final By acceptButton = By.xpath(
            "//div[contains(@class,'Order_Modal_')]//button[text()='Да']");
    private final WebDriver driver;

    public ConfirmOrderPopUpWindow(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public boolean checkIsOpened() {
        return driver.findElement(popUpWindow).isDisplayed();
    }

    public ConfirmOrderPopUpWindow clickAcceptButton() {
        driver.findElement(acceptButton).click();
        return this;
    }
}
