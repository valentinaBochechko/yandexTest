package yaRu;

import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import yaRu.Pages.EmailPage;
import yaRu.Pages.MailPage;
import yaRu.Pages.PassportPage;
import yaRu.Pages.SearchPage;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class MailTest {
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

    //Тест на нажатие на кнопку войти в почту
    @Test
    public void loginToEmailTest() {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.loginToEmailClick();
        EmailPage emailPage = new EmailPage(driver);
        emailPage.getEmailButtons();
        wait.until(ExpectedConditions.titleContains("Почта"));
        //проверка, что выполнен переход на страницу и кнопки создать аккаунт и войти есть на странице
        if (!emailPage.getEmailButtons().isEmpty()) {
            assertEquals("Создать аккаунт",
                    emailPage.getEmailButtons().get(0));
            assertEquals("Войти",
                    emailPage.getEmailButtons().get(1));
            assertEquals("Создать аккаунт",
                    emailPage.getEmailButtons().get(2));
            assertEquals("Войти",
                    emailPage.getEmailButtons().get(3));
        } else {
            fail("Список кнопок входа в почту пустой");
        }
    }

    //Тест на открытие страницы с залогиненной почтой
    private void registrationInEmail(String username, String password) throws InterruptedException {
        driver.get("https://mail.yandex.ru");
        EmailPage emailPage = new EmailPage(driver);
        emailPage.loginEmailClick();
        emailPage.auth(username, password);
        // Ждем загрузки страницы с таймаутом в 10 секунд
        wait.until(ExpectedConditions.titleContains("Входящие"));
        Thread.sleep(1000);
    }

    @Test
    public void openSearchPageEmailLoginTest() throws InterruptedException {
        //регистрируемся в почте, возвращаемся на страницу
        String username = "t.yaRu";
        String password = "testYaRu";
        registrationInEmail(username, password);

        driver.get("https://ya.ru");
        wait.until(ExpectedConditions.titleIs("Яндекс"));

        SearchPage searchPage = new SearchPage(driver);
        // проверка, что отображаетсч верное имя пользователя
        assertEquals(searchPage.usernameText(), username.toLowerCase());
        // нажимаем на логин, проверяем, что попали в Яндекс.Паспорт
        searchPage.usernameClick();

        PassportPage passportPage = new PassportPage(driver);
        if (!passportPage.getNameNavigationItems().isEmpty()) {
            assertEquals("Управление аккаунтом",
                    passportPage.getNameNavigationItems().get(0));
            assertEquals("Мои сервисы",
                    passportPage.getNameNavigationItems().get(1));
            assertEquals("Отзывы и оценки",
                    passportPage.getNameNavigationItems().get(2));
        } else {
            fail("Список иконок пустой");
        }

        driver.get("https://ya.ru");
        wait.until(ExpectedConditions.titleIs("Яндекс"));

        // нажимаем на Почту, проверяем, что попали в почтовый ящик
        searchPage.mailClick();
        // Ждем загрузки страницы с таймаутом в 10 секунд
        wait.until(ExpectedConditions.titleContains("Входящие"));
        MailPage mailPage = new MailPage(driver);
        if (!mailPage.getTypeMailFoldesItems().isEmpty()) {
            //проверям, что получили список папкок в ящике в заданном порялке
            assertEquals("Входящие",
                    mailPage.getTypeMailFoldesItems().get(0));
            assertEquals("Отправленные",
                    mailPage.getTypeMailFoldesItems().get(1));
            assertEquals("Удалённые",
                    mailPage.getTypeMailFoldesItems().get(2));
            assertEquals("Спам",
                    mailPage.getTypeMailFoldesItems().get(3));
            assertEquals("Черновики",
                    mailPage.getTypeMailFoldesItems().get(4));
        } else {
            fail("Список папок пустой");
        }

        //возвращаемся на начальную страницу
        driver.get("https://ya.ru");
        wait.until(ExpectedConditions.titleIs("Яндекс"));

        // нажимаем на ссылку Выход, проверяем, что нет кнопки Логин, Почта, Выход и есть кнопка Войти в почту
        searchPage.mailExitClick();
        if (searchPage.findMailHref())
            assertEquals("Войти в почту",
                    searchPage.getTextMailLoginHref());
        else {
            fail("Ссылка \"Войти в почту\" не доступна");
        }
    }

    @AfterClass
    public static void stopDriver() {
        driver.quit();
    }
}