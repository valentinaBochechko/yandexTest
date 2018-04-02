package yaRu.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class YandexPage {
    //поиск сообщения "Задан пустой поисковый запрос"
    @FindBy(className = "misspell__message")
    private WebElement emptyMessage;

    @FindBy(name = "text")
    private WebElement searchInput;

    private final WebDriver driver;

    public YandexPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    //Получение текста сообщения при пустом запросе
    public String getEmptyMessageText() {
        return emptyMessage.getText();
    }

    //Получение текста из поля поиска
    public String getTextSearchInput() {
        return searchInput.getAttribute("value");
    }
}
