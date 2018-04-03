package yaRu;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import yaRu.Pages.HomePage;
import yaRu.Pages.SearchPage;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SetupHomePageTest {
    private static WebDriver driver;
    private Wait<WebDriver> wait = new WebDriverWait(driver, 10);

    @BeforeClass
    public static void startDriver() {
        TypeOS typeOS = new TypeOS();

        switch (typeOS.getTypeOs().get(0)) {
            case "win": {
                switch (typeOS.getTypeOs().get(1)) {
                    case "32":
                        System.setProperty("webdriver.gecko.driver", "geckodriver32.exe");
                        break;
                    case "64":
                        System.setProperty("webdriver.gecko.driver", "geckodriver64.exe");
                        break;
                    default:
                        System.out.println("Неизвестное значение");
                        break;
                }
                break;
            }
            case "mac":
                System.setProperty("webdriver.gecko.driver", "geckodriverMac");
                break;
            case "linux":
                switch (typeOS.getTypeOs().get(1)) {
                    case "32":
                        System.setProperty("webdriver.gecko.driver", "geckodriverLinux32");
                        break;
                    case "64":
                        System.setProperty("webdriver.gecko.driver", "geckodriverLinux64");
                        break;
                    default:
                        System.out.println("Неизвестное значение");
                        break;
                }
                break;
            case "unknown":
            default:
                System.out.println("Неизвестное значение");
                break;
        }
        driver = new FirefoxDriver();
    }

    //Тест на нажатие на кнопку сделать стартовой
    @Test
    public void pressSetupStartPageButtonTest() {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.makeHomepageClick();
        HomePage homePage = new HomePage(driver);
        wait.until(ExpectedConditions.titleContains("стартовой"));
        //проверка, что выполнен переход на страницу
        assertEquals("Чтобы сделать Яндекс стартовой страницей, установите расширение",
                homePage.getTextStartPageText());
    }

    @AfterClass
    public static void stopDriver() {
        driver.quit();
    }
}
