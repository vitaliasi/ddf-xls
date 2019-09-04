package test.adminStuff;

import automation.actions.Utils;
import org.junit.Test;

public class adminUtils extends Utils {


    public adminUtils() throws Exception {
    }

    public void tc001_helper() throws Exception {
        login("Login with admin user", () -> {
           return null;
        });
        }

    public void tc002_helper() throws Exception {
        String userId = login("Login with admin user and create user", () -> {
            return addNewEmployee();
        });
    }
    }
