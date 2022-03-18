import entities.Product;
import managers.DriverManager;
import managers.PagesManager;
import managers.PropertiesManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Sergey Nesterov
 */
public class BeforeTests {
    protected static PropertiesManager properties = PropertiesManager.getInstance();

    @BeforeEach
    void beforeEach() {
        DriverManager.getWebDriver().get(properties.get("home.url"));
    }

    @AfterEach
    void afterEach() {
        try {
            long sleepAfterTest = Long.parseLong(properties.get("sleep.after.test")) * 1000;
            Thread.sleep(sleepAfterTest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DriverManager.quit();
        PagesManager.deleteInstance();
        Product.clearList();
    }
}
