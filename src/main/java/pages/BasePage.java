package pages;

import managers.DriverManager;
import managers.PagesManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Sergey Nesterov
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    @FindBy(xpath = "//nav//input")
    private WebElement search;

    @FindBy(xpath = "//a[contains(@class,'cart-link')]")
    private WebElement basketIcon;

    @FindBy(xpath = "//*[@class='cart-link__price']")
    private WebElement basketIconPrice;

    @FindBy(xpath = "//*[@class='cart-link__badge']")
    private WebElement basketIconCount;

    @FindBy(className = "header-top-menu__info-phone")
    private WebElement phone;


    public BasePage() {
        driver = DriverManager.getWebDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
        js = (JavascriptExecutor) driver;
    }

    public SearchResults typeInSearchFormAndRequest(String request) {
        search.sendKeys(request);
        search.sendKeys(Keys.ENTER);
        return PagesManager.getInstance().getSearchResults();
    }

    public BasketPage clickOnBasketIcon() {
        basketIcon.click();
        waitFor(basketIconCount);
        return PagesManager.getInstance().getBasketPage();
    }

    public int getBasketIconPrice() {
        return Integer.parseInt(basketIconPrice.getText().replaceAll("\\D", ""));
    }

    public int getBasketIconCount() {
        return Integer.parseInt(basketIconCount.getText().replaceAll("\\D", ""));
    }

    public void waitUntilBasketIconCountChange(String countBefore) {
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElement(basketIconCount, countBefore))
        );
    }

    public WebElement waitClick(WebElement we) {
        return wait.until(ExpectedConditions.elementToBeClickable(we));
    }

    public WebElement waitFor(WebElement we) {
        return wait.until(ExpectedConditions.visibilityOf(we));
    }

    public void scrollUp(){
        js.executeScript("arguments[0].scrollIntoView()", phone);
    }
}
