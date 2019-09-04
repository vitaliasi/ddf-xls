package automation.actions;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.util.Base64;
import java.util.Random;

public class Utils extends Action {

    Action action = new Action();
    ExcelReader excelReader = new ExcelReader();
    HSSFWorkbook workbook = new HSSFWorkbook(excelReader.setPath());

    public Utils() throws Exception {
    }

    Random rand = new Random();

    public int randomNumber() throws Exception {
    int number = rand.nextInt(1000);

        number += 1;
        return number;
    }

    public void login() throws Exception {
        getDriver();
        System.out.println("getDriver");
        open("https://opensource-demo.orangehrmlive.com");
        System.out.println("open");
        //TODO wrong creds
        action.setText("Populate field [Username] with 'Admin'", By.id("txtUsername"), "Admin");
        action.setText("Populate field [Password] with 'admin123'", By.id("txtPassword"), "admin123");
        action.click("Click [Login]", By.id("btnLogin"));
    }

    public void addNewEmployee(String name) throws Exception {
        //TODO uncomment when creds work
        action.mouseOver("Mouse over [PIM]", By.id("menu_pim_viewPimModule"));
        action.click("Click [Add Employee]", By.id("menu_pim_addEmployee"));

        //>-------------------
        //excel part
        int usersRowNumber = excelReader.getUnregisteredUser("NewEmployees","PersonalNumber");

        String firstName =  excelReader.getInfoFromColumn("NewEmployees","FirstName", usersRowNumber).getStringCellValue();
        System.out.println(firstName+" - this is from method");
        String lastName =  excelReader.getInfoFromColumn("NewEmployees","LastName", usersRowNumber).getStringCellValue();
        System.out.println(lastName+" - this is from method");

        action.setText("Populate field [First Name] with '"  +firstName+"'", By.id("firstName"), firstName);
        action.setText("Populate field [Last Name] with '"  +lastName+"'", By.id("lastName"), lastName);

//        TODO save to personalNumber
        String employeeId = getDriver().findElement(By.id("employeeId")).getAttribute("value");
        System.out.println(employeeId);
//        excelReader.change();
//        excelReader.read();
        excelReader.writeTo();

        action.click("Click [Checkbox]", By.id("chkLogin"));

//        TODO save to userID
        String userId = firstName.substring(0,1) + lastName.substring(0,3) + randomNumber();
        System.out.println(userId);
        action.setText("Populate [User Name] with '"  + userId +"'", By.id("user_name"), userId);

//        TODO save to password
        String originalInput = "test input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        System.out.println(encodedString);

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);

    }

}