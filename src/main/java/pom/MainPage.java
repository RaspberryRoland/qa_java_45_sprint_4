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

    //Ожидаемый скрытый текст
    private final List<String> expectedValues = Arrays.asList(
        "Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, " +
                "можете просто сделать несколько заказов — один за другим.",
        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. " +
                "Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. " +
                "Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.",
        "Только начиная с завтрашнего дня. Но скоро станем расторопнее.",
        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без " +
                "передышек и во сне. Зарядка не понадобится.",
        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.",
        "Да, обязательно. Всем самокатов! И Москве, и Московской области.");
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
    public boolean checkExpandedText() throws Exception {
        for (int i = 0; i < driver.findElements(accordHeaders).size(); i++) {
            clickToExpandTextBySequenceNumber(i);
            if (!expectedValues.get(i).equals(driver.findElement(By.xpath(partOfAccordionPanelXpath + i + "']"))
                    .getText())) {
                throw new Exception(String.format("Фактический текст '%s' отличается от ожидаемого '%s'",
                        driver.findElement(By.xpath(partOfAccordionPanelXpath + i + "']")).getText(),
                        expectedValues.get(i)));
            }
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
