package test.hrPortal;

import automation.actions.Utils;

public class hrPortalUtils extends Utils {


    public hrPortalUtils() throws Exception {
    }

//  due to poor performance of portal, login method tested first
    public void tc001_helper() throws Exception {
        loginAsAdmin();
    }

    public void tc002_helper() throws Exception {
        loginAsAdmin();
        String userId = addNewEmployee();
//        this mehod suppose to get users name by user ID from excel
//        String firstName = getFirstNameByUserId(userId);
//        this mehod suppose to get users last by user ID from excel
//        String lastName = getLastNameByUserId(userId);
//        this method suppose to fill all required details from excel to web
//        fillDetails();
//        this method suppose to assign leave and save data to excel
//        assignLeave(userId, firstName, lastName);
//        logout();
//        this method suppose to get encoded password of particular user
//        String password = getEncodedPassword(userId);
//        this method suppose to login user with required details
//        loginAsUser(userId, password);
//        this method suppose to verify leave
//        verifyLeave();
    }
}
