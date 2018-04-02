package yaRu.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(className = "b-page-title")
    private WebElement startPageText;

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        if (!"Как сделать Яндекс стартовой страницей".equals(driver.getTitle())) {
            throw new IllegalStateException("Page not open");
        }
    }

    public String getTextStartPageText() {
        return startPageText.getText();
    }
}
