package test.hrPortal;

import automation.actions.Utils;
import org.junit.After;
import org.junit.Test;


public class hrPortalScripts extends Utils {

    private hrPortalUtils utils = new hrPortalUtils();

    public hrPortalScripts() throws Exception {
    }

    @After
    public void quitDriver() {
        quit();
        System.out.println("Browser has been closed");
    }

    @Test
    public void TC001() throws Exception {
        utils.tc001_helper();
    }

    @Test
    public void TC002() throws Exception {
    utils.tc002_helper();
    }


}
