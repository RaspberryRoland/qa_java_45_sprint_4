import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pom.MainPage;

public class checkExpandedTextTest {

    private WebDriver driver;

    @Before
    public void init() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Test
    public void checkExpandedText() throws Exception {
        MainPage mainPage = new MainPage(driver);
        mainPage.open()
                .closeCookiePopUpWindow();
        Assert.assertTrue(mainPage.checkExpandedText());
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
