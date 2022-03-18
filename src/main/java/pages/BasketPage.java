package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Sergey Nesterov
 */
public class BasketPage extends BasePage {

    @FindBy(xpath = "//*[@class='cart-items__product']//*[contains(@class,'container')]")
    private List<WebElement> productList;

    private By
            productName = By.xpath(".//a[contains(@class,'name')]"),
            plusButton = By.xpath(".//i[contains(@class,'plus')]"),
            deleteButton = By.xpath(".//button[text()='Удалить']"),
            price = By.xpath(".//*[@class='price__current']");

    private WebElement cardPrice;

    @FindBy(xpath = "//*[@class='cart-tabs']//*[contains(text(),'Вернуть удалённый')]")
    private WebElement restoreProduct;

    @FindBy(className = "total-amount__count")
    private WebElement basketCount;

    @FindBy(xpath = "//*[@class='total-amount__label']//*[@class='price__current']")
    private WebElement basketPrice;


    // -1  Если не найден элемент
    public int getProductPrice(String name) {
        for (WebElement p : productList) {
            if (p.findElement(productName).getText().contains(name)) {
                return Integer.parseInt(p.findElement(price).getText().replaceAll("\\D", ""));
            }
        }
        return -1;
    }

    public BasketPage restoreProduct() {
        String countProductBefore = Integer.toString(getBasketIconCount());
        scrollUp();
        restoreProduct.click();
        waitUntilBasketIconCountChange(countProductBefore);
        return this;
    }

    public BasketPage clickDelete(String name) {
        return clickButton(name, deleteButton);
    }

    public BasketPage clickPlus(String name) {
        return clickButton(name, plusButton);
    }

    public BasketPage clickButton(String name, By button) {
        String countProductBefore = Integer.toString(getBasketIconCount());
        scrollUp();
        for (WebElement p : productList) {
            if (p.findElement(productName).getText().contains(name)) {
                waitClick(p.findElement(button)).click();
                waitUntilBasketIconCountChange(countProductBefore);
                return this;
            }
        }
//        Assertions
        System.err.println("Не найден продукт с названием содержащим: " + name);
        return this;
    }
}
