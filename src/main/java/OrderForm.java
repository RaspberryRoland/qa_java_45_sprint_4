import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderForm {

    //Текстовое поле для заполнения имени
    private final By nameTextField = By.xpath(
            "//div[contains(@class,'Input_InputContainer')]//input[@placeholder ='* Имя']");
    //Текстовое поле для заполнения фамилии
    private final By surnameTextField = By.xpath(
            "//div[contains(@class,'Input_InputContainer')]//input[@placeholder ='* Фамилия']");
    //Текстовое поле для адреса доставки
    private final By deliveryAddressTextField = By.xpath(
            "//div[contains(@class,'Input_InputContainer')]//input[@placeholder ='* Адрес: куда привезти заказ']");
    //Выпадающий список станции метро
    private final By metroStationCombobox = By.className(
            "select-search__input");
    //Текстовое поле номер телефона
    private final By telephoneNumberTextField = By.xpath(
            "//div[contains(@class,'Input_InputContainer')]//input[@placeholder ='* Телефон: на него позвонит курьер']");
    //Кнопка Далее
    private final By orderNextButton = By.xpath("//div[contains(@class,'Order_NextButton')]/button");

    //Текстовое поле для ввода даты
    private final By dateTextField = By.xpath(
            "//div[contains(@class, 'react-datepicker')]//input");
    //Выпадающий список
    private final By durationComboboxDropDownArrow = By.xpath(
            "//span[@class='Dropdown-arrow']");
    //Кнопка Заказать
    private final By orderButton = By.xpath(
            "//div[contains(@class, 'Order_Buttons')]/button[contains(text(), 'Заказать')]");

    private final WebDriver driver;

    public OrderForm(WebDriver driver) {
        this.driver = driver;
    }
    public void enterOrderName(String name) {
        driver.findElement(nameTextField).clear();
        driver.findElement(nameTextField).sendKeys(name);
    }

    public void enterOrderSurname(String surname) {
        driver.findElement(surnameTextField).clear();
        driver.findElement(surnameTextField).sendKeys(surname);
    }

    public void enterOrderDeliveryAddress(String address) {
        driver.findElement(deliveryAddressTextField).clear();
        driver.findElement(deliveryAddressTextField).sendKeys(address);
    }

    public void enterOrderTelephoneNumber(String telephoneNumber) {
        driver.findElement(telephoneNumberTextField).clear();
        driver.findElement(telephoneNumberTextField).sendKeys(telephoneNumber);
    }

    public void chooseMetroStation(int metroNumber, String metroStationName) {
        driver.findElement(metroStationCombobox).click();
        By newStateMetro = By.xpath(
                "//button[@value=" + metroNumber + "]//div[contains(text(), '" + metroStationName + "')]");
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(newStateMetro));
        driver.findElement(newStateMetro).click();
    }

    public OrderForm fieldFirstFormForOrder(String name, String surname, String address, int metroNumber, String metroStation, String telephoneNumber) throws InterruptedException {
        enterOrderName(name);
        enterOrderSurname(surname);
        enterOrderDeliveryAddress(address);
        Thread.sleep(5000);
        chooseMetroStation(metroNumber, metroStation);
        enterOrderTelephoneNumber(telephoneNumber);
        return this;
    }

    public OrderForm clickNextButton() {
        driver.findElement(orderNextButton).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(driver.findElement(dateTextField)));
        return this;
    }

    public void enterOrderDate(String date) {
        driver.findElement(dateTextField).clear();
        driver.findElement(dateTextField).sendKeys(date);
    }

    public void chooseDuration(String duration) {
        driver.findElement(durationComboboxDropDownArrow).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//div[@class='Dropdown-menu']/div[contains(text(), '" + duration + "')]"))).click();
    }

    public OrderForm fieldSecondFormForOrder(String date, String duration){
        enterOrderDate(date);
        chooseDuration(duration);
        return this;
    }

    public OrderForm clickOrderButton() {
        driver.findElement(orderButton).click();
        return this;
    }
}