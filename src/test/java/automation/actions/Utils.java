package automation.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Base64;
import java.util.Random;

public class Utils extends Action {

    Action action = new Action();
    public ExcelReader excelReader = new ExcelReader();

    protected Utils() {
    }

    Random random = new Random();

    private int randomNumber() {
        int number = random.nextInt(1000);
        number += 1;
        return number;
    }

    public void loginAsAdmin() throws Exception {
        getDriver();
        open("https://opensource-demo.orangehrmlive.com");
//        get admin's creds from excel
        String userID = excelReader.getInfoFromColumn("AdminLogin", "UserID", 1).getStringCellValue();
        String password = excelReader.getInfoFromColumn("AdminLogin", "Password", 1).getStringCellValue();

        action.setText("Populate field [Username] with "  + userID +"'", By.id("txtUsername"), userID);
        action.setText("Populate field [Password] with "  + password + "'", By.id("txtPassword"), password);
        action.click("Click [Login]", By.id("btnLogin"));
    }

    public String addNewEmployee() throws Exception {
//        navigate to [Add Employee]
        action.click("Click [PIM]", By.id("menu_pim_viewPimModule"));
        action.click("Click [Add Employee]", By.id("menu_pim_addEmployee"));

//        locate first unregistered user
        int usersRowNumber = excelReader.getRequiredRow("NewEmployees", "PersonalNumber", "not registered");

//        get unregistered's employee name and surname
        String firstName = excelReader.getInfoFromColumn("NewEmployees", "FirstName", usersRowNumber).getStringCellValue();
        String lastName = excelReader.getInfoFromColumn("NewEmployees", "LastName", usersRowNumber).getStringCellValue();

//        print employee's name and surname into required fields
        action.setText("Populate field [First Name] with '" + firstName + "'", By.id("firstName"), firstName);
        action.setText("Populate field [Last Name] with '" + lastName + "'", By.id("lastName"), lastName);

//          write employee's personal number taken from the web page to 'PersonalNumber' column in excel
        String employeeId = getDriver().findElement(By.id("employeeId")).getAttribute("value");

        int personalNumberNewEmployees = excelReader.returnColumnIndex("NewEmployees", "PersonalNumber");
        excelReader.writeToExcel("NewEmployees", usersRowNumber, personalNumberNewEmployees, employeeId);

        int personalNumberUserLogins = excelReader.returnColumnIndex("UserLogins", "PersonalNumber");
        excelReader.writeToExcel("UserLogins", usersRowNumber, personalNumberUserLogins, employeeId);

        action.click("Click [Checkbox]", By.id("chkLogin"));

//          created user ID and saves to excel
        String userId = firstName.substring(0, 1) + lastName.substring(0, 3) + randomNumber();
        action.setText("Populate [User Name] with '" + userId + "'", By.id("user_name"), userId);
        int userIdUserLogins = excelReader.returnColumnIndex("UserLogins", "UserID");
        excelReader.writeToExcel("UserLogins", usersRowNumber, userIdUserLogins, userId);

//          generate password, write to web page, encode it and save to excel
        String originalInput = "Password" + randomNumber();

        action.setText("Populate [Password] with generated password " + originalInput, By.id("user_password"), originalInput);
        action.setText("Populate [Confirm Password] with generated password " + originalInput, By.id("re_password"), originalInput);

        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        int passwordUserLogins = excelReader.returnColumnIndex("UserLogins", "Password");
        excelReader.writeToExcel("UserLogins", usersRowNumber, passwordUserLogins, encodedString);

//        save newly created user
        action.click("click [Save]", By.id("btnSave"));
        return userId;
    }

    //    TODO ITS DEAD AGAIN
    public void fillDetails(String userId) throws Exception {
        action.mouseOver("Mouse over [PIM]", By.id("menu_pim_viewPimModule"));
        action.click("Click [Employee List]", By.id("menu_pim_viewEmployeeList"));
        action.setText("Populate [User ID] with '" + userId + "'", By.id("empsearch_id"), userId);
        action.click("Click [Search]", By.id("searchBtn"));

//        find personal number by userdId
        int userIDRowNumber = excelReader.getRequiredRow("UserLogins", "UserID", userId);
        String personalNumber = excelReader.getInfoFromColumn("UserLogins", "PersonalNumber", userIDRowNumber).getStringCellValue();

        action.click("Click [" + personalNumber + "]", By.xpath("//a[text()='" + personalNumber + "']"));

//        fill personal details
        action.click("Click [Edit]", By.id("btnSave"));

//        Gender
        int userIDRowNumberPersonalDetails = excelReader.getRequiredRow("PersonalDetails", "PersonalNumber", personalNumber);
        String gender = excelReader.getInfoFromColumn("PersonalDetails", "Gender", userIDRowNumberPersonalDetails).getStringCellValue();
        action.click("Click [" + gender + " radio button]", By.xpath("//font[text()='" + gender + "']//ancestor::li/input"));

//        Nationality
        action.click("Click [Nationality]", By.id("personal_cmbNation"));
        String nationality = excelReader.getInfoFromColumn("PersonalDetails", "Nationality", userIDRowNumberPersonalDetails).getStringCellValue();
        action.click("Click [" + nationality + "]", By.xpath("//font[text() = '" + nationality + "']//ancestor::option"));

//        TODO fill contact details


//        TODO fill job info

        return;
    }

    public String getFirstNameByUserId(String userId)throws Exception {
//        find personal number by userdId
        int userIDRowNumber = excelReader.getRequiredRow("UserLogins", "UserID", userId);
        String personalNumber = excelReader.getInfoFromColumn("UserLogins", "PersonalNumber", userIDRowNumber).getStringCellValue();

//        locate users row by personal number
        int usersRowNumber = excelReader.getRequiredRow("NewEmployees", "PersonalNumber", personalNumber);

//        get employee's name
        String firstName = excelReader.getInfoFromColumn("NewEmployees", "FirstName", usersRowNumber).getStringCellValue();

        return firstName;
    }

    public String getLastNameByUserId(String userId)throws Exception {
//    find personal number by userdId
        int userIDRowNumber = excelReader.getRequiredRow("UserLogins", "UserID", userId);
        String personalNumber = excelReader.getInfoFromColumn("UserLogins", "PersonalNumber", userIDRowNumber).getStringCellValue();

//        locate users row by personal number
        int usersRowNumber = excelReader.getRequiredRow("NewEmployees", "PersonalNumber", personalNumber);

//        get employee's name and surname by personalNumber
        String lastName = excelReader.getInfoFromColumn("NewEmployees", "LastName", usersRowNumber).getStringCellValue();
        return lastName;
    }

    //    TODO test/finish
    public void assignLeave(String userId, String firstName, String lastName) throws Exception {
        action.mouseOver("Mouse over [Leave]", By.id("menu_leave_viewLeaveModule"));
        action.click("Click [Assign Leave]", By.id("menu_leave_assignLeave"));

        action.setText("Populate [Employee Name] with "  +firstName + lastName+ "'", By.id("assignleave_txtEmployee_empName"), firstName + lastName);

//        find personal number by userdId
        int userIDRowNumber = excelReader.getRequiredRow("UserLogins", "UserID", userId);
        String personalNumber = excelReader.getInfoFromColumn("UserLogins", "PersonalNumber", userIDRowNumber).getStringCellValue();

//        locate users row by personal number
        int usersRowNumber = excelReader.getRequiredRow("NewEmployees", "PersonalNumber", personalNumber);
//        save name to excel assign leave sheet
        int fistNameAssignLeave = excelReader.returnColumnIndex("AssignLeave", "FistName");
        excelReader.writeToExcel("AssignLeave", usersRowNumber, fistNameAssignLeave, firstName);

        int lastNameeAssignLeave = excelReader.returnColumnIndex("AssignLeave", "LastName");
        excelReader.writeToExcel("AssignLeave", usersRowNumber, fistNameAssignLeave, lastName);

//        save leave type to excel
        String leaveType = action.getText(By.xpath("//select[@name='assignleave[txtLeaveType]']//option[@selected='selected']"));
        int leaveTypeAssignLeave = excelReader.returnColumnIndex("AssignLeave", "LeaveType");
        excelReader.writeToExcel("AssignLeave", usersRowNumber, leaveTypeAssignLeave, leaveType);

//        save dates to excel
        String startDate = getDriver().findElement(By.id("assignleave_txtFromDate")).getAttribute("value");
        int startDateAssignLeave = excelReader.returnColumnIndex("AssignLeave", "StartDate");
        excelReader.writeToExcel("AssignLeave", usersRowNumber, startDateAssignLeave, startDate);

        String toDate = getDriver().findElement(By.id("assignleave_txtToDate")).getAttribute("value");
        int toDateAssignLeave = excelReader.returnColumnIndex("AssignLeave", "ToDate");
        excelReader.writeToExcel("AssignLeave", usersRowNumber, toDateAssignLeave, toDate);

        action.click("Click [Assign]", By.id("assignBtn"));
        return;
    }

    //    TODO test/finish
    public void verifyLeave() throws Exception {

        return;
    }

    //    TODO test/finish
    public String getEncodedPassword(String userId) throws Exception {
//        locate row by userID
        int usersRowNumber = excelReader.getRequiredRow("UserLogins", "UserID", userId);

//        get password by userID provided
        String encodedString =  excelReader.getInfoFromColumn("UserLogins","Password", usersRowNumber).getStringCellValue();
//        decode password
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
//        login into system with user
        return decodedString;
    }

    //    TODO test/finish
    public void loginAsUser(String userId, String password) throws Exception {
        action.setText("Populate field [Username] with '" + userId + "'", By.id("txtUsername"), userId);
        action.setText("Populate field [Password] with '" + password + "'", By.id("txtPassword"), password);
        action.click("Click [Login]", By.id("btnLogin"));
    }

    public void logout() throws Exception {
        action.click("Click [Welcome]", By.id("welcome"));
        action.jSclick("Click [Logout]", By.xpath("//a[text()='Logout']"));
    }
}