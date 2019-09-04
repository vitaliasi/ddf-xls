package automation.actions;

import org.openqa.selenium.By;

import java.util.Base64;
import java.util.Random;

public class Utils extends Action {

    Action action = new Action();
    ExcelReader excelReader = new ExcelReader();

    public Utils(){
    }

    Random random = new Random();

    public int randomNumber(){
    int number = random.nextInt(1000);
    number += 1;
    return number;
    }

    public String login(String actionPerformed, ActionReturnString performAction) throws Exception {
        getDriver();
        System.out.println("getDriver");
        open("https://opensource-demo.orangehrmlive.com");
        System.out.println("open");
        action.setText("Populate field [Username] with 'Admin'", By.id("txtUsername"), "Admin");
        action.setText("Populate field [Password] with 'admin123'", By.id("txtPassword"), "admin123");
        action.click("Click [Login]", By.id("btnLogin"));
        String actionString = performAction.doAction();
        action.click("Click [Welcome]", By.id("welcome"));
        action.click("Click [Logout]", By.xpath("//a[text()='Logout']"));
        return actionString;
    }

    public String addNewEmployee() throws Exception {
//        navigate to [Add Employee]
        action.mouseOver("Mouse over [PIM]", By.id("menu_pim_viewPimModule"));
        action.click("Click [Add Employee]", By.id("menu_pim_addEmployee"));

//        locate first unregistered user
        int usersRowNumber = excelReader.getRequiredRow("NewEmployees", "PersonalNumber", "not registered");

//        get unregistered's employee name and surname
        String firstName =  excelReader.getInfoFromColumn("NewEmployees","FirstName", usersRowNumber).getStringCellValue();
        System.out.println(firstName+" - this is from method");
        String lastName =  excelReader.getInfoFromColumn("NewEmployees","LastName", usersRowNumber).getStringCellValue();
        System.out.println(lastName+" - this is from method");

//        print employee's name and surname into required fields
        action.setText("Populate field [First Name] with '"  +firstName+"'", By.id("firstName"), firstName);
        action.setText("Populate field [Last Name] with '"  +lastName+"'", By.id("lastName"), lastName);

//          write employee's personal number taken from the web page to 'PersonalNumber' column in excel
        String employeeId = getDriver().findElement(By.id("employeeId")).getAttribute("value");

        int personalNumberNewEmployees = excelReader.returnColumnIndex("NewEmployees", "PersonalNumber");
        excelReader.writeToExcel("NewEmployees", usersRowNumber, personalNumberNewEmployees, employeeId);

        int personalNumberUserLogins = excelReader.returnColumnIndex("UserLogins", "PersonalNumber");
        excelReader.writeToExcel("UserLogins", usersRowNumber, personalNumberUserLogins, employeeId);

        action.click("Click [Checkbox]", By.id("chkLogin"));


//          created user ID and saves to excel
        String userId = firstName.substring(0,1) + lastName.substring(0,3) + randomNumber();
        action.setText("Populate [User Name] with '"  + userId +"'", By.id("user_name"), userId);
        int userIdUserLogins = excelReader.returnColumnIndex("UserLogins", "UserID");
        excelReader.writeToExcel("UserLogins", usersRowNumber, userIdUserLogins, userId);

//          generate password, encodes it and saves to excel
        String originalInput = "Password" + randomNumber();
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        System.out.println(encodedString);

        int passwordUserLogins = excelReader.returnColumnIndex("UserLogins", "Password");
        excelReader.writeToExcel("UserLogins", usersRowNumber, passwordUserLogins, encodedString);

//        save newly created user
        action.click("click [Save]", By.id("btnSave"));
        return userId;
    }

    public void loginWithUser(String userId) throws Exception {

        int passwordColumn = excelReader.returnColumnIndex("UserLogins", "Password");
        int passwordRow = excelReader.getRequiredRow("UserLogins", "Password", userId);
//        String encodedString = excelReader.getInfoFromColumn("UserLogins","Password", passwordRow);
//        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//        String decodedString = new String(decodedBytes);
//        System.out.println(decodedString);
    }
}