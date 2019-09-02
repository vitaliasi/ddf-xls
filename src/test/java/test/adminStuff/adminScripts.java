package test.adminStuff;

//TODO fix description
//import org.junit.Description;
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
//    @Description("Admin user login")
    public void TC001() throws Exception {
    utils.tc001_helper();
}


}
