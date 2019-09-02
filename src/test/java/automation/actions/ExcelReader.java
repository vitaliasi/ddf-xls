package automation.actions;

import java.io.FileInputStream;

import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.openqa.selenium.Platform;

public class ExcelReader {

//    public void readExcel(String filePath,String fileName,String sheetName) throws IOException{
//
//        //Create an object of File class to open xlsx file
//
//        File file =    new File(filePath+"\\"+fileName);
//
//        //Create an object of FileInputStream class to read excel file
//
//        FileInputStream inputStream = new FileInputStream(file);
//
//        Workbook ddf = null;
//
//        //Find the file extension by splitting file name in substring  and getting only extension name
//
//        String fileExtensionName = fileName.substring(fileName.indexOf("."));
//
//        //Check condition if the file is xlsx file
//
//        if(fileExtensionName.equals(".xlsx")){
//
//            //If it is xlsx file then create object of XSSFWorkbook class
//
//            ddf = new XSSFWorkbook(inputStream);
//
//        }
//
//        //Check condition if the file is xls file
//
//        else if(fileExtensionName.equals(".xls")){
//
//            //If it is xls file then create object of HSSFWorkbook class
//
//            ddf = new HSSFWorkbook(inputStream);
//
//        }
//
//        //Read sheet inside the workbook by its name
//
//        Sheet NewEmployees = ddf.getSheet(sheetName);
//
//        //Find number of rows in excel file
//
//        int rowCount = NewEmployees.getLastRowNum()-NewEmployees.getFirstRowNum();
//
//        //Create a loop over all the rows of excel file to read it
//
//        for (int i = 0; i < rowCount+1; i++) {
//
//            Row row = NewEmployees.getRow(i);
//
//            //Create a loop to print cell values in a row
//
//            for (int j = 0; j < row.getLastCellNum(); j++) {
//
//                //Print Excel data in console
//
//                System.out.print(row.getCell(j).getStringCellValue()+"|| ");
//
//            }
//
//            System.out.println();
//        }
//
//    }

    //How to read excel files using Apache POI
//    public static class ReadExcel {

    public static FileInputStream setPath() throws Exception {
        if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
            FileInputStream path = new FileInputStream("src/test/resources/ddf.xls");
            return path;
        } else if (Platform.getCurrent().toString().contains("WIN")) {
            FileInputStream path = new FileInputStream("\\src\\test\\resources\\ddf.xls");
            return path;
        }
       return null;
    }



    public static void readFromFile(String sheetName, int rowIndex, int cellInt) throws Exception {
//        set path
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);

//            sheets
        HSSFSheet sheet = workbook.getSheet(sheetName);

//           rows
        Row row = sheet.getRow(rowIndex);

//            Column col = sheet.getColumn();

//            cells
        Cell cell = row.getCell(cellInt);

        System.out.println(cell);

        System.out.println(sheet.getRow(rowIndex).getCell(cellInt));

        String cellval = cell.getStringCellValue();
        System.out.println(cellval);

    }
   }
