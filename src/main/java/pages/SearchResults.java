package pages;

import managers.PagesManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Sergey Nesterov
 */
public class SearchResults extends BasePage{

    @FindBy(className = "title")
    private WebElement title;

    @FindBy(xpath = "//a[contains(@class,'catalog-product__name')]")
    private List<WebElement> productList;

    public ProductCardPage clickOnProductContainedName(String name){
        waitFor(title);
        for (WebElement product : productList){
            if(product.getText().contains(name)){
                product.click();
                return PagesManager.getInstance().getProductCardPage();
            }
        }
        //Assertions
        System.err.println("Не найден продукт с названием: " + name);
        return PagesManager.getInstance().getProductCardPage();
    }
}
