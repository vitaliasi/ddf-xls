package automation.actions;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.util.Iterator;

//
public class Utils extends Action {

    Action action = new Action();
    ExcelReader excelReader = new ExcelReader();
    HSSFWorkbook workbook = new HSSFWorkbook(excelReader.setPath());

    public Utils() throws Exception {
    }

    public void login() throws Exception {
        getDriver();
        open("https://opensource-demo.orangehrmlive.com");
        action.setText("Populate field [Username] with 'Admin'", By.id("txtUsername"), "Admin");
        action.setText("Populate field [Password] with 'admin123'", By.id("txtPassword"), "admin123");
        action.click("Click [Login]", By.id("btnLogin"));
    }

    public void addNewEmployee(String name) throws Exception {
        action.mouseOver("Mouse over [PIM]", By.id("menu_pim_viewPimModule"));
        action.click("Click [Add Employee]", By.id("menu_pim_addEmployee"));
//        action.click("Click [Add]", By.id("btnAdd"));

//        System.out.println(excelReader.readExcelFile("NewEmployees").toString());


        //>-------------------
        //excel part
        excelReader.readFromFile("NewEmployees", 0, 0);




    }

    public String addNextEmployee() throws Exception {
        FileInputStream path = excelReader.setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
//            int colNum = -1;
        HSSFSheet sheet = workbook.getSheet("NewEmployees");
        HSSFRow row = sheet.getRow(0);
        String columnName = "Number";


//        int noOfColumns = sheet.getRow(0).getLastCellNum();
//        String[] Headers = new String[noOfColumns];
//        for (int i = 0; i < row.getLastCellNum(); i++) {
//            if(row.getCell().getStringCellValue().equals("Number")){
//                if(row.getCell(i).getStringCellValue().isEmpty()) {
//                    String wb = excelReader.workbook();
//                    Iterator<Row> rows = wb.rowIterator();
//
//                    XSSFRow row;
//                    while(rows.hasNext())
//                    {
//                        row=(XSSFRow) rows.next();
//                        XSSFCell cell=row.getCell(7);//8th cell of the row
//                        String CName= cell.getStringCellValue(); //getting the cell value as string
//
//                    }
//                }

////            Headers[i] = String.valueOf(sheet.getRow(0).getCell(i).getStringCellValue().equals("personalNumber"));
//////                if (row.getCell(i).getStringCellValue().trim().isEmpty(); {
//////                System.out.println(row.getCell(i).getStringCellValue());
//////                System.out.println(row.getCell(i).getStringCellValue());
//
//
//        }
//            }
//        }
//
//
//
//        return null;
//    }

//}
        return null;}}