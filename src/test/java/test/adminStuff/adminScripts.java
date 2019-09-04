package test.adminStuff;

import automation.actions.Utils;
import org.junit.After;
import org.junit.Test;


public class adminScripts extends Utils {

    private adminUtils utils = new adminUtils();

    public adminScripts() throws Exception {
    }

    @After
    public void quitDriver() {
//        quit();
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
