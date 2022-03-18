import managers.DriverManager;
import managers.PropertiesManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 * @author Sergey Nesterov
 */
public class BeforeTests {
    protected static PropertiesManager properties = PropertiesManager.getInstance();

    @BeforeEach
    static void beforeEach() {
        DriverManager.getWebDriver().get(properties.get("home.url"));
    }

    @AfterAll
    public static void afterAll() {
        try {
            long sleepAfterTest = Long.parseLong(properties.get("sleep.after.test")) * 1000;
            Thread.sleep(sleepAfterTest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DriverManager.quit();
    }
}
