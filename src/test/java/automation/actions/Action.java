package automation.actions;

//import org.openqa.selenium.interactions.Actions;
import browsers.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class Action extends Driver {

    private Actions actions = new Actions(getDriver());

    public Action() {
    }

    public void implicitWait(By by) throws Exception {
      getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
    }

    public byte[] click(String action, By by) throws Exception {
        WebElement element = getDriver().findElement(by);
        implicitWait(by);
        element.click();
        System.out.println(action);
        return null;
    }

    public Object setText(String action, By by, String value) throws Exception {
        WebElement input = getDriver().findElement(by);
        implicitWait(by);
        input.clear();
        input.sendKeys(value);
        System.out.println(action);
        return null;
    }

    public void mouseOver(String action, By by) throws Exception {
        WebElement element = getDriver().findElement(by);
        actions.moveToElement(element).perform();
    }
}
