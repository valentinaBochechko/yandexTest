package yaRu.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class MailPage {
    @FindBy(className = "mail-NestedList-Item-Name")
    private List<WebElement> typeMailFolders;

    private final WebDriver driver;

    public MailPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        if (!"Входящие — Яндекс.Почта".equals(driver.getTitle())) {
            throw new IllegalStateException("Page not open");
        }
    }

    public List<String> getTypeMailFoldesItems() {
        List<String>getTextWebElement = new ArrayList<String>();
        for (WebElement webElement: typeMailFolders)
            getTextWebElement.add(webElement.getText());
        return getTextWebElement;
    }
}
