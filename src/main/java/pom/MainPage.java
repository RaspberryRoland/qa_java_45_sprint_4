package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.List;

public class MainPage {
    //url приложения
    private static final String url = "https://qa-scooter.praktikum-services.ru/";

    //Кнопка заказать вверх страницы
    private final By orderUpperButton = By.xpath(
            "//div[contains(@class, 'Header_Nav')]//button[contains(@class, 'Button_Button')]");
    //Кнопка заказать внизу страницы
    private final By orderLowerButton = By.xpath(
            "//div[contains(@class, 'Home_FinishButton')]//button[contains(@class, 'Button_Button')]");

    //Меню всех выпадающих списков
    private final By faqAccordion = By.xpath(
            "//div[contains(@class, 'Home_FAQ')]/div[@class='accordion']");
    private final By accordHeaders = By.xpath(
            "//div[contains(@class, 'Home_FAQ')]/div[@class='accordion']//div[@role='heading']");
    //Общая часть локатора xpath заголовка, в котором содержится скрытый текст
    private final String partOfAccordionHeadingXpath =
            "//div[contains(@class, 'Home_FAQ')]/div[@class='accordion']//div[@id='accordion__heading-";
    //Общая часть локатора xpath со скрытым текстом
    private final String partOfAccordionPanelXpath =
            "//div[contains(@class, 'Home_FAQ')]/div[@class='accordion']//div[@id='accordion__panel-";
    //PopUp окна с информацией про куки
    private final By popUpCookieWindow = By.xpath("//div[contains(@class,'App_CookieConsent')]");

    //Кнопка для закрытия PopUp окна с информацией про куки
    private final By acceptCookieButton = By.xpath("//button[contains(@class,'App_CookieButton')]");

    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public MainPage open() {
        driver.get(url);
        return this;
    }

    //Закрытие окна с информацией про куки
    public MainPage closeCookiePopUpWindow() {
        if (driver.findElement(popUpCookieWindow).isDisplayed()) {
            driver.findElement(acceptCookieButton).click();
        }
        return this;
    }

    /**
     * Нажать на элемент по порядковому номеру для раскрытия содержимого
     * @param number - порядковый номер элемента
     */
    public void clickToExpandTextBySequenceNumber(int number) {
        if ("false".equals(driver.findElement(By.xpath(partOfAccordionHeadingXpath + number + "']"))
                .getDomAttribute("aria-expanded"))) {
            driver.findElement(By.xpath(partOfAccordionHeadingXpath + number + "']")).click();
        }
    }

    /**
     * Проверка совпадения ожидаемого и фактического текста, который скрыт в раскрывающемся заголовке
     * @throws Exception
     */
    public boolean checkExpandedText(int number, String expectedTextValue) throws Exception {
        clickToExpandTextBySequenceNumber(number);
            if (!expectedTextValue.equals(driver.findElement(
                    By.xpath(partOfAccordionPanelXpath + number + "']"))
                    .getText())) {
                throw new Exception(String.format("Фактический текст '%s' отличается от ожидаемого '%s'",
                        driver.findElement(By.xpath(partOfAccordionPanelXpath + number + "']")).getText(),
                        expectedTextValue));
            }
        return true;
    }

    public void clickUpperOrderButton() {
        driver.findElement(orderUpperButton).click();
    }

    public void clickLowerOrderButton() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(orderLowerButton));
        driver.findElement(orderLowerButton).click();
    }
}
