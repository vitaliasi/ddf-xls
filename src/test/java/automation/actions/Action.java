package automation.actions;

//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Action extends Driver {

    private Actions actions = new Actions(getDriver());

    public Action() {
    }

    public void implicitWait() throws Exception {
      getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
    }

    public byte[] click(String action, By by) throws Exception {
        WebDriverWait wait=new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement element = getDriver().findElement(by);
        element.click();
        System.out.println(action);
        return null;
    }

    public byte[] jSclick(String action, By by) throws Exception {
        WebDriverWait wait=new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement element = getDriver().findElement(by);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", element);
        return null;
    }

    public Object setText(String action, By by, String value) throws Exception {
        WebElement input = getDriver().findElement(by);
        implicitWait();
        input.clear();
        input.sendKeys(value);
        System.out.println(action);
        return null;
    }

    public void mouseOver(String action, By by) throws Exception {
        WebDriverWait wait=new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        WebElement element = getDriver().findElement(by);
        actions.moveToElement(element).perform();
    }

    public String getText(By by) throws Exception {
        String output = getDriver().findElement(by).getText();
        return output;
    }
}
