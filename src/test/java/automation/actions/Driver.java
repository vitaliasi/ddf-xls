package automation.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Driver {

//    private static WebDriver driver = openChrome();
    private static WebDriver driver = openEdge();
//    private static WebDriver driver = openFirefox();

    protected static WebDriver openChrome() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        if (os.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver");
            WebDriver driver = new ChromeDriver();
            return driver;
        } else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
            System.out.println(System.getProperty("user.dir") + "\\src\\test\\resources\\chromedriver.exe");
            WebDriver driver = new ChromeDriver();
            return driver;
        }
    }

    protected static WebDriver openEdge() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        if (os.contains("mac")) {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/src/test/resources/msedgedriver");
            WebDriver driver = new EdgeDriver();
            return driver;
        } else {
            System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\MicrosoftWebDriver.exe");
            WebDriver driver = new EdgeDriver();
            return driver;
        }
    }

    protected static WebDriver openFirefox() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println(os);
        if (os.contains("mac")) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/test/resources/geckodriver");
//            DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//            capabilities.setCapability("marionette",true);
            WebDriver driver = new FirefoxDriver();
            return driver;
        } else {
            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\src\\test\\resources\\geckodriver.exe");
            WebDriver driver = new FirefoxDriver();
            return driver;
        }
    }

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