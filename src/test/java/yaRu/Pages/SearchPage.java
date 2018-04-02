package yaRu.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class SearchPage {
    //Посик ссылки "Сделать стартовой"
    @FindBy(linkText = "Сделать стартовой")
    private WebElement makeHomepage;

    //Посик ссылки "Войти в почту"
    @FindBy(className = "link_logout")
    private WebElement loginToEmail;

    //Посик ссылки с введеднным логином при входе в почту
    @FindBy(className = "personal__link")
    private WebElement username;

    //Посик ссылки "Почта"
    @FindBy(className = "personal__mail")
    private WebElement mail;

    //Посик ссылки "Почта"
    @FindBy(className = "personal__exit")
    private WebElement mailExit;

    //Текстовое поле ввода, вводится поисковый запрос
    @FindBy(id = "text")
    private WebElement inputQuery;

    //Выпадающий список, появляется после ввода данных в строку поиска
    @FindBy(className = "suggest2-item")
    private List<WebElement> itemDropDownList;

    //Кнопка, которая отчищает поле ввода запроса
    @FindBy(className = "input__clear_visibility_visible")
    private WebElement inputClear;

    //Кнопка голосового ввода
    @FindBy(className = "input__voice-search")
    private WebElement inputQueryVoice;

    //Кнопка "Найти"
    @FindBy(css = "button" )
    private WebElement findButton;

    //Кнопка "Яндекс"
    @FindBy(className = "layout__footer-logo")
    private WebElement yandexButton;

    private final WebDriver driver;

    public SearchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        if (!"Яндекс".equals(driver.getTitle())) {
            throw new IllegalStateException("Page not open");
        }
    }

    //Нажатие на ссылку "Сделать стартовой"
    public void makeHomepageClick() {
        makeHomepage.click();
    }

    //Нажатие на ссылку "Войти в почту"
    public void loginToEmailClick() {
        loginToEmail.click();
    }

    //Получение текста логина на странице поиска
    public String usernameText() {
        return username.getText();
    }

    //Нажатие на ссылку логинв на тестовой странице
    public void usernameClick() {
        username.click();
    }

    //Нажатие на ссылку "Почта"
    public void mailClick() {
        mail.click();
    }

    //Нажатие на ссылку "Выход"
    public void mailExitClick() {
        mailExit.click();
    }

    //функция проверяет, что доступна ссылка "Войти в почту"
    public boolean findMailHref(){
        return loginToEmail.isDisplayed();
    }

    //функция возвращает текст ссылки "Войти в почту"
    public String getTextMailLoginHref() {
        return loginToEmail.getText();
    }



    //функция, которая заполняет поле ввода поиска данными, передаваемыми ей
    public void inputQueryEnter(String text) {
        inputQuery.sendKeys(text);
    }

    //функция отправляет запрос на выполнение поиска
    public void inputQuerySubmit() {
        inputQuery.submit();
    }

    //получение элементов выпадающего списка вариантов запроса
    public List<String> getDropDownListItems() {
        List<String>getTextWebElement = new ArrayList<String>();
        for (WebElement webElement: itemDropDownList)
            getTextWebElement.add(webElement.getText());
        return getTextWebElement;
    }

    //функция очистки поля ввода
    public void clearSearchInput() {
        inputQuery.clear();
    }

    public SearchPage inputQueryVoiceClick() {
        inputQueryVoice.click();
        return this;
    }

    public SearchPage findButtonClick() {
        findButton.submit();
        return this;
    }


    public SearchPage inputClearClick() {
        inputClear.click();
        return this;
    }

    public SearchPage yandexButtonClick() {
        yandexButton.click();
        return this;
    }



}