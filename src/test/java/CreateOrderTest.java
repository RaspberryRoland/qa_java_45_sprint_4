import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pom.MainPage;
import popUpWindows.ConfirmOrderPopUpWindow;
import popUpWindows.SuccessPopUpWindow;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String name;
    private final String surname;
    private final String address;
    private final int metroNumber;
    private final String metroStation;
    private final String telephoneNumber;
    private final String date;
    private final String duration;


    private WebDriver driver;

    public CreateOrderTest(
            String name,
            String surname,
            String address,
            int metroNumber,
            String metroStation,
            String telephoneNumber,
            String date,
            String duration) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metroNumber = metroNumber;
        this.metroStation = metroStation;
        this.telephoneNumber = telephoneNumber;
        this.date = date;
        this.duration = duration;
    }

    @Before
    public void init() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Parameterized.Parameters
    public static Object[][] enterFields() {
        return new Object[][] {
                {"Иван", "Игорев", "ул. Достоевского", 1, "Бульвар Рокоссовского", "+79173320401", "02.05.2025", "сутки"},
                {"Игорь", "Иванов", "ул. Селениума", 2, "Черкизовская", "+79173320402", "03.04.2025", "двое суток"}
        };
    }


    @Test
    public void createOrderWithUpperButton() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .closeCookiePopUpWindow()
                .clickUpperOrderButton();
        OrderForm orderForm = new OrderForm(driver);
        orderForm
                .fieldFirstFormForOrder(name, surname, address, metroNumber, metroStation, telephoneNumber)
                .clickNextButton()
                .fieldSecondFormForOrder(date, duration)
                .clickOrderButton();
        ConfirmOrderPopUpWindow confirmOrderPopUpWindow = new ConfirmOrderPopUpWindow(driver);
        confirmOrderPopUpWindow.checkIsOpened();
        confirmOrderPopUpWindow.clickAcceptButton();
        SuccessPopUpWindow successPopUpWindow = new SuccessPopUpWindow(driver);

        Assert.assertTrue(successPopUpWindow.checkIsOpened());

        Assert.assertTrue(successPopUpWindow.checkOrderState());
    }

    @Test
    public void createOrderWithLowerButton() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .closeCookiePopUpWindow()
                .clickLowerOrderButton();
        OrderForm orderForm = new OrderForm(driver);
        orderForm
                .fieldFirstFormForOrder(name, surname, address, metroNumber, metroStation, telephoneNumber)
                .clickNextButton()
                .fieldSecondFormForOrder(date, duration)
                .clickOrderButton();
        ConfirmOrderPopUpWindow confirmOrderPopUpWindow = new ConfirmOrderPopUpWindow(driver);
        confirmOrderPopUpWindow.checkIsOpened();
        confirmOrderPopUpWindow.clickAcceptButton();
        SuccessPopUpWindow successPopUpWindow = new SuccessPopUpWindow(driver);

        Assert.assertTrue(successPopUpWindow.checkIsOpened());

        Assert.assertTrue(successPopUpWindow.checkOrderState());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
