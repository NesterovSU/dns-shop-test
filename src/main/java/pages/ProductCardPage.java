package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Sergey Nesterov
 */
public class ProductCardPage extends BasePage{

    @FindBy(xpath = "//h1[contains(@class,'title')]")
    private WebElement header;

    @FindBy(xpath = "//*[@class='product-card-top__buy']//*[@class='product-buy__price']")
    private WebElement price;

    @FindBy(xpath = "//*[@class='product-card-top__buy']//*[text()='Купить']")
    private WebElement buyButton;

    @FindBy(xpath = "//button[text()='В корзине']")
    private WebElement inBasketButton;

    public int getPrice(){
        return Integer.parseInt(price.getText().replaceAll("\\D", ""));
    }

    public ProductCardPage clickBuy(){
        scrollUp();
        buyButton.click();
        new WebDriverWait(driver, 10).withMessage("Не удалось добавить в корзину")
                .until(ExpectedConditions.visibilityOf(inBasketButton));
        return this;
    }
}
