package test.adminStuff;

import automation.actions.Utils;
import org.junit.Test;

public class adminUtils extends Utils {


    public adminUtils() throws Exception {
    }

    public void tc001_helper() throws Exception {
            login();
            addNewEmployee("");
        }
    }
