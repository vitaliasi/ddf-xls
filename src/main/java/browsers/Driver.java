package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class Driver {


    protected static WebDriver openChrome() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver");
            WebDriver driver = new ChromeDriver();
            return driver;
        } else {
            WebDriver driver = new ChromeDriver();
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
            return driver;
        }
//        System.setProperty("webdriver.chrome.driver", "C:\Users\Ampacattu\Downloads\chromedriver_win32\chromedriver.exe");
//        return driver;
    }



    private static WebDriver driver = openChrome();

    //TODO add edge and firefox

    protected static WebDriver getDriver() {
        return driver;
    }

    protected static void open(String url) {
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().get(url);
        getDriver().manage().window().maximize();
    }

    public static void close() {
        getDriver().close();
    }

    public static void quit() {
        getDriver().quit();
    }
}