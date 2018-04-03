package yaRu;

//тестирование поиска

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import yaRu.Pages.SearchPage;
import yaRu.Pages.YandexPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class SearchTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        TypeOS typeOS = new TypeOS();
        typeOS.setProperty();
        driver = new FirefoxDriver();
    }

    //тест на отправку пустого запроса
    @Test
    public void emptySearchTest() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);
        searchPage.inputQueryEnter("");
        searchPage.inputQuerySubmit();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Thread.sleep(3000);
        assertEquals("— Яндекс: ничего не найдено",
                driver.getTitle());
        YandexPage yandexPage = new YandexPage(driver);
        assertEquals("Задан пустой поисковый запрос",
                yandexPage.getEmptyMessageText());
    }

    // Тест на ввод запроса максимальной длинны (400 символов)
    @Test
    public void maxLenTest() throws InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        int maxLen = 400;
        Random rnd = new Random();
        for (int i = 0; i < maxLen; i++) {
            stringBuilder.append(Character.toString((char) (rnd.nextInt(94) + 33)));
        }

        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);

        searchPage.inputQueryEnter(stringBuilder.toString());
        searchPage.inputQuerySubmit();
        Thread.sleep(3000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит первые 85 символов поискового запроса
        if (driver.getTitle().contains(stringBuilder.toString().substring(0, 85))) {
            assertEquals(stringBuilder.toString(),
                    yandexPage.getTextSearchInput());
        }
    }

    // Тест на превышение максимальной длинны (401 символ)
    @Test
    public void maxLenPlusTest() throws InterruptedException {
        StringBuilder stringBuilder = new StringBuilder();
        int maxLen = 401;
        Random rnd = new Random();
        for (int i = 0; i < maxLen; i++) {
            stringBuilder.append(Character.toString((char) (rnd.nextInt(94) + 33)));
        }

        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);

        searchPage.inputQueryEnter(stringBuilder.toString());
        searchPage.inputQuerySubmit();
        Thread.sleep(3000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит первые 85 символов поискового запроса
        if (driver.getTitle().contains(stringBuilder.toString().substring(0, 85))) {
            assertEquals(stringBuilder.toString().substring(0, 400),
                    yandexPage.getTextSearchInput());
        }
    }

    // Тест на поиск на русском языке, полное совпадение
    @Test
    public void languageRusTest() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);
        String text = "Привет";

        searchPage.inputQueryEnter(text);
        //driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        Thread.sleep(1000);

        for (String str: searchPage.getDropDownListItems()) {
            if (!str.contains(text.toLowerCase()))
                fail("Результат поиска не содержит исходный текст");
        }
        searchPage.inputQuerySubmit();
        Thread.sleep(3000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит первые 85 символов поискового запроса
        if (driver.getTitle().equals(text)) {
            assertEquals(text,
                    yandexPage.getTextSearchInput());
        }
    }

    // Тест на поиск на английском языке, полное совпадение
    @Test
    public void languageEngTest() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);
        String text = "Hello";

        searchPage.inputQueryEnter(text);
        Thread.sleep(3000);

        for (String str: searchPage.getDropDownListItems()) {
            if (!str.contains(text.toLowerCase()))
                fail("Результат поиска не содержит исходный текст");
        }
        searchPage.inputQuerySubmit();
        Thread.sleep(3000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит первые 85 символов поискового запроса
        if (driver.getTitle().equals(text)) {
            assertEquals(text,
                    yandexPage.getTextSearchInput());
        }
    }

    // Тест поиска русского слова, на английской раскладке, полное совпадение
    @Test
    public void languageRusTrancleteTest() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);
        String text = "Ghbdtn";
        String normalText = "Привет";

        searchPage.inputQueryEnter(text);
        Thread.sleep(1000);

        for (String str: searchPage.getDropDownListItems()) {
            if (!str.contains(normalText.toLowerCase()))
                fail("Результат поиска не содержит исходный текст");
        }

        searchPage.inputQuerySubmit();
        Thread.sleep(1000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит первые 85 символов поискового запроса
        if (driver.getTitle().equals(text)) {
            assertEquals(text,
                    yandexPage.getTextSearchInput());
        }
    }

    // Тест поиска английского слова, на русской раскладке, полное совпадение
    @Test
    public void languageEngTrancleteTest() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);
        String text = "Руддщ";
        String normalText = "Hello";

        searchPage.inputQueryEnter(text);
        Thread.sleep(1000);

        for (String str: searchPage.getDropDownListItems()) {
            if (!str.contains(normalText.toLowerCase()))
                fail("Результат поиска не содержит исходный текст");
        }
        searchPage.inputQuerySubmit();
        Thread.sleep(1000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит первые 85 символов поискового запроса
        if (driver.getTitle().equals(text)) {
            assertEquals(text,
                    yandexPage.getTextSearchInput());
        }
    }

    // Тест поиска русского слова на транслите
    @Test
    public void languageTrancleteTest() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);

        String text = "privet";
        String normalText = "привет";

        searchPage.inputQueryEnter(text);
        Thread.sleep(2000);

        for (String str: searchPage.getDropDownListItems()) {
            if ((!str.contains(normalText.toLowerCase())) && (!str.contains(text.toLowerCase())))
                fail("Результат поиска не содержит исходный текст");
        }
        searchPage.inputQuerySubmit();
        Thread.sleep(3000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит первые 85 символов поискового запроса
        if (driver.getTitle().equals(text)) {
            assertEquals(text,
                    yandexPage.getTextSearchInput());
        }
    }

    //тест поиска по спец. символам
    @Test
    public void specSymbolTest() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);

        String symbolStr = "!@#$%^&*()_-+=";
        List<String> textNameSymbol = new ArrayList<>();
        textNameSymbol.add("восклицательный знак");
        textNameSymbol.add("коммерческое at");
        textNameSymbol.add("октоторп");
        textNameSymbol.add("знак доллара");
        textNameSymbol.add("знак процента");
        textNameSymbol.add("циркумфлекс");
        textNameSymbol.add("амперсанд");
        textNameSymbol.add("астериск знак");
        textNameSymbol.add("круглая скобка");
        textNameSymbol.add("круглая скобка");
        textNameSymbol.add("символ нижнее подчеркивание");
        textNameSymbol.add("дефис");
        textNameSymbol.add("знак плюс");
        textNameSymbol.add("знак равенства");

        YandexPage yandexPage = new YandexPage(driver);
        for (int i = 0; i < symbolStr.length(); i++) {
            String subStr = symbolStr.substring(i, i+1);
            searchPage.inputQueryEnter(subStr);
            Thread.sleep(1000);

            for (String str: searchPage.getDropDownListItems()) {
                if (str.indexOf(subStr) != 0)
                    fail("Первый символ не заданный спец. символ");
            }

            searchPage.inputQuerySubmit();
            Thread.sleep(2000);
            //проверка, что title открытой страницы содержит первые 85 символов поискового запроса
            if (driver.getTitle().contains(textNameSymbol.get(i))) {
                assertEquals(textNameSymbol.get(i),
                        yandexPage.getTextSearchInput());
            } else {
                fail("Запрошенного текста нет в заголовке");
            }

            driver.get("https://ya.ru");
            Thread.sleep(1000);
        }
    }

    //тестирование отчистки поля ввода
    @Test
    public void clearInputKeyboard() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);
        String text = "привет";

        searchPage.inputQueryEnter(text);
        Thread.sleep(1000);

        for (String str: searchPage.getDropDownListItems()) {
            if (!str.contains(text.toLowerCase()))
                fail("Результат поиска не содержит исходный текст");
        }
        searchPage.clearSearchInput();
        searchPage.inputQuerySubmit();
        Thread.sleep(1000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит пусмтой запрос
        assertEquals("— Яндекс: ничего не найдено",
                driver.getTitle());
        assertEquals("Задан пустой поисковый запрос",
                yandexPage.getEmptyMessageText());
    }

    //тестирование отчистки поля ввода, повторный ввод данных
    @Test
    public void clearInputButton() throws InterruptedException {
        driver.get("https://ya.ru");
        SearchPage searchPage = new SearchPage(driver);

        String text = "привет";

        searchPage.inputQueryEnter(text);
        Thread.sleep(1000);

        searchPage.clearSearchInput();
        searchPage.inputQuerySubmit();
        Thread.sleep(1000);

        YandexPage yandexPage = new YandexPage(driver);
        //проверка, что title открытой страницы содержит пусмтой запрос
        assertEquals("— Яндекс: ничего не найдено",
                driver.getTitle());
        assertEquals("Задан пустой поисковый запрос",
                yandexPage.getEmptyMessageText());
    }

    @AfterClass
    public static void stopDriver() {
       driver.quit();
    }
}
