package yaRu.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class PassportPage {
    @FindBy(className = "dheader-navigation-item")
    private List<WebElement> navigationPanel;

    private final WebDriver driver;

    public PassportPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        if (!"Яндекс.Паспорт".equals(driver.getTitle())) {
            throw new IllegalStateException("Page not open");
        }
    }

    public List<String> getNameNavigationItems() {
        List<String>getTextWebElement = new ArrayList<String>();
        for (WebElement webElement: navigationPanel)
            getTextWebElement.add(webElement.getText());
        return getTextWebElement;
    }
}
