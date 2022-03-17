package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Sergey Nesterov
 */
public class BasketPage extends BasePage {

    @FindBy(xpath = "//h1[@class='cart-title']")
    private WebElement basketHeader;

    @FindBy(className = "header-top-menu__info-phone")
    private WebElement phone;

    @FindBy(xpath = "//*[@class='cart-items__product']//*[contains(@class,'container')]")
    private List<WebElement> productList;

    private By
            productName = By.xpath(".//a[contains(@class,'name')]"),
            plusButton = By.xpath(".//i[contains(@class,'plus')]"),
            minusButton = By.xpath(".//*[contains(@class,'button_minus')]"),
            deleteButton = By.xpath(".//button[text()='Удалить']"),
            checkBox = By.xpath(".//*[contains(@class,'base-ui-checkbox__icon')]"),
            price = By.xpath(".//*[@class='price__current']");

    private WebElement cardPrice;

    @FindBy(xpath = "//*[@class='cart-tabs']//*[contains(text(),'Вернуть удалённый')]")
    private WebElement restoreProduct;

    @FindBy(className = "total-amount__count")
    private WebElement basketCount;

    @FindBy(xpath = "//*[@class='total-amount__label']//*[@class='price__current']")
    private WebElement basketPrice;

    private String hat = "//*[@class='cart-items__product']//*[contains(@class,'container')]//*[contains(text(), '%s')]//ancestor::div[@class='cart-items__product']//*[contains(@class,'base-ui-checkbox__icon')]";



    //methods
    public int getBasketCount() {
        return Integer.parseInt(basketCount.getText().replaceAll("\\D", ""));
    }

    public int getBasketPrice() {
        return Integer.parseInt(basketPrice.getText().replaceAll("\\D", ""));
    }

    public int getProductPrice(String name) {
        for (WebElement p : productList) {
            if (p.findElement(productName).getText().contains(name)) {
                return Integer.parseInt(p.findElement(price).getText().replaceAll("\\D", ""));
            }
        }
        //Assertions
//        System.err.println("Не найден продукт с названием содержащим: " + name);
        return -1;
    }

    public BasketPage restoreProduct() {
        scrollUp();
        restoreProduct.click();
        return this;
    }

    public BasketPage clickCheckBox(String name) {
        waitClick(driver.findElement(By.xpath(String.format(hat, name))));
        return clickButton(name, checkBox);
    }

    public BasketPage clickDelete(String name) {
        return clickButton(name, deleteButton);
    }

    public BasketPage clickPlus(String name) {

        return clickButton(name, plusButton);
    }

    public BasketPage clickMinus(String name) {
        return clickButton(name, minusButton);
    }

    public BasketPage clickButton(String name, By button) {
        String countProductBefore = Integer.toString(getBasketIconCount());
        for (WebElement p : productList) {
            if (p.findElement(productName).getText().contains(name)) {
                scrollUp();
                waitClick(waitFor(p.findElement(button))).click();
                waitUntilBasketIconCountChange(countProductBefore);
                return this;
            }
        }
        //Assertions
        System.err.println("Не найден продукт с названием содержащим: " + name);
        return this;
    }
}
