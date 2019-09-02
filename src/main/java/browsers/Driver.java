package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class Driver {

    private static WebDriver driver = new ChromeDriver();
    //TODO add edge and firefox

    protected static WebDriver getDriver() {
        return driver;
    }

    protected static void open(String url) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(url);
        driver.manage().window().maximize();
    }

    public static void close() {
        driver.close();
    }

    public static void quit() {
        driver.quit();
    }
}