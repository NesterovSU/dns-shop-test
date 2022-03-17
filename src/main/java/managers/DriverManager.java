package managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * @author Sergey Nesterov
 */
public class DriverManager {
    private static WebDriver webDriver;

    public static WebDriver getWebDriver() {
        return webDriver != null ? webDriver : getInstance();
    }

    private static WebDriver getInstance() {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\browser-drivers\\chromedriver98.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        return webDriver;
    }

    public static void quit() {
        if (webDriver != null) {
            webDriver.quit();
            webDriver = null;
        }
    }
}
