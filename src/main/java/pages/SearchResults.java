package pages;

import managers.PagesManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * @author Sergey Nesterov
 */
public class SearchResults extends BasePage{

    @FindBy(xpath = "//div[@id='search-results']")
    private WebElement resultsBlock;

    @FindBy(xpath = "//a[contains(@class,'catalog-product__name')]")
    private List<WebElement> productList;

    public ProductCardPage clickOnProductContainedName(String name){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='title']")));
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
