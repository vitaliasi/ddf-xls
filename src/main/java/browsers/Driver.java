package browsers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class Driver {


    protected static WebDriver getDriver() {
        String os = System.getProperty("os.name").toLowerCase();
        WebDriver driver = new ChromeDriver();
        if (os.contains("mac")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("C:\\Users\\vitalija.silvestruk\\IdeaProjects\\ddf-xls\\src\\test\\resources\\chromedriver.exe"));
//            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
        }
//        System.setProperty("webdriver.chrome.driver", "C:\Users\Ampacattu\Downloads\chromedriver_win32\chromedriver.exe");
        return driver;
    }


//    private static WebDriver driver = new ChromeDriver();
    //TODO add edge and firefox

//    protected static WebDriver getDriver() {
//        return driver;
//    }

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