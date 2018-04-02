package yaRu.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EmailPage {
    @FindBy(className = "button2_size_mail-big")
    private List<WebElement> actionWithEmail;

    @FindBy(name = "login")
    private WebElement loginInput;

    @FindBy(name = "passwd")
    private WebElement passwdInput;

    @FindBy(className = "passport-Button")
    private WebElement loginButton;

    private final WebDriver driver;

    public EmailPage (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;

        if (!"Яндекс.Почта - бесплатная и надежная электронная почта".equals(driver.getTitle())) {
            throw new IllegalStateException("Page not open");
        }
    }

    public List<String> getEmailButtons() {
        List<String>getTextWebElement = new ArrayList<String>();
        for (WebElement webElement: actionWithEmail)
            getTextWebElement.add(webElement.getText());
        return getTextWebElement;
    }

    public void loginEmailClick() {
        actionWithEmail.get(1).click();
    }

    public void auth(String username, String password) {
        loginInput.sendKeys(username);
        passwdInput.sendKeys(password);
        loginButton.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
}
