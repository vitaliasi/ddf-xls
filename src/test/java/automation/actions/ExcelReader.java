package automation.actions;

import com.google.common.io.ByteStreams;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.logging.Logger;

public class ExcelReader {

    /*
    Method sets path for different OS systems
     */
    public static FileInputStream setPath() throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            FileInputStream path = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/ddf.xls");
            System.out.println(path);
            return path;
        } else {
            FileInputStream path = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\ddf.xls");
            return path;
        }
    }

//    example of unused excel reader
    public static void readFromFile(String sheetName, int rowIndex, int columnInt) throws Exception {
//            set path
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);

//            sheets
        HSSFSheet sheet = workbook.getSheet(sheetName);

//           rows
        Row row = sheet.getRow(rowIndex);

//            cells
        Cell cell = row.getCell(columnInt);

        System.out.println(cell+ "1");

    }

    public Row getDefaultRaw(String sheetName) throws Exception {
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
        HSSFSheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(0);
        return row;
    }
//  TODO in progress
//    public String getDefaultRaw(String sheetName) throws Exception {
//        FileInputStream path = setPath();
//        HSSFWorkbook workbook = new HSSFWorkbook(path);
//        HSSFSheet sheet = workbook.getSheet(sheetName);
//        Row row = sheet.getRow(0);
////        return row;
//    }

    public Object getDefaultCell(String sheetName) throws Exception {
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
        HSSFSheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(0);
        Cell cell = row.getCell(0);
        return cell;
    }

    /*
    Returns index of required column in required sheet
     */
    public int returnColumnIndex(String sheetName, String columnName) throws Exception {
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
        HSSFSheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(0);
//          checks all raws for required columnName and returns its location
        int coefficient = 0;
        int cellNum = row.getPhysicalNumberOfCells();
        for (int i = 0; i < cellNum; i++) {
            if ((row.getCell(i).toString()).equals(columnName)) {
                coefficient = i;
                System.out.println(coefficient);
            }
        }
        return coefficient;
    }

    //    finds cell location with set keywords (returns row)
    public int getRequiredRow(String sheetName, String columnName, String locator) throws Exception{
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
        HSSFSheet sheet = workbook.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();
//      keywords to find unregistered user

        int indexOfColumn = returnColumnIndex(sheetName, columnName);

//      loop through column and finds cell with set keyword
        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                Cell cell = row.getCell(indexOfColumn);
                if (cell != null) {
//                    Checks text in required column
                    String text = formatter.formatCellValue(cell).toLowerCase();
//                    Checks if text matches keyword
                    if (locator.equals(text)) {
                        CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
                        cellRef.getRow();
                        System.out.println("row number" + cellRef.getRow());
                        return cellRef.getRow();
                    }
                }
            }
        }
        System.out.println("bad getUnregisteredUser");
        return 0;
    }

    public HSSFCell getInfoFromColumn(String sheetName, String columnName, int usersRowNumber) throws Exception {
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
        HSSFSheet sheet = workbook.getSheet(sheetName);

//        gets row number of unregistered employee
        int rowIndex = usersRowNumber;
        System.out.println(rowIndex+"rowIndex");
//        gets name of required column
        int column = returnColumnIndex(sheetName, columnName);
        System.out.println(column+"column");
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(column);
        System.out.println(cell);
        HSSFCell value = workbook.getSheet(sheetName).getRow(rowIndex).getCell(column);
        return value;
    }

    public String getExcelStringPath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            String pathToExcel = System.getProperty("user.dir")+"/src/test/resources/ddf.xls";
            return pathToExcel;
        } else {
            String pathToExcel = System.getProperty("user.dir")+"\\src\\test\\resources\\ddf.xls";
            return pathToExcel;
        }
    }

public void writeToExcel(String sheetName, int rowIndex, int cellNum, String value) throws Exception {

    String pathToExcel = getExcelStringPath();
    System.out.println("CHECK PATH " + pathToExcel);
    try {
        FileInputStream inputStream = new FileInputStream(new File(pathToExcel));
        Workbook workbook = WorkbookFactory.create(inputStream);


        System.out.println(cellNum + "cell from writeToExcel");
        System.out.println(rowIndex + "row from writeToExcel");
//          find sheet
        Sheet sheetName1 = workbook.getSheet(sheetName);
        System.out.println(sheetName1 + " sheetName1");
//        get sheet's index int
        int sheet = workbook.getSheetIndex(sheetName1);
        System.out.println(sheet + " sheet index from writeToExcel");

        Row row = workbook.getSheetAt(sheet).getRow(rowIndex);
        System.out.println("done1");

        try {
//               remove second cell from first row
            row.removeCell(row.getCell(cellNum));
            System.out.println("done2");
        } catch (Exception p) {
        }

        Cell cell = row.createCell(cellNum);
        System.out.println("done3");
        cell.setCellValue(value);
        System.out.println("done4");

        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(pathToExcel);
        workbook.write(outputStream);
        outputStream.close();

    } catch (IOException | EncryptedDocumentException | InvalidFormatException i) {
        i.printStackTrace();
    }
    System.out.println("done");
}

        public void writeIntToExcel(String sheetName, int rowIndex, int cellNum, Integer value) throws Exception {

        String pathToExcel = getExcelStringPath();
        System.out.println("CHECK PATH " + pathToExcel);
        try {
            FileInputStream inputStream = new FileInputStream(new File(pathToExcel));
            Workbook workbook = WorkbookFactory.create(inputStream);

            Sheet sheet = workbook.getSheet(sheetName);

            Row row = sheet.getRow(rowIndex);
            Cell cell = row.getCell(cellNum);
            cell.setCellValue(value);

//        Object[][] bookData = {
//                {"The Passionate Programmer", "Chad Fowler", 16},
//                {"Software Craftmanship", "Pete McBreen", 26},
//                {"The Art of Agile Development", "James Shore", 32},
//                {"Continuous Delivery", "Jez Humble", 41},
//        };
//
////        int rowCount = sheet.getLastRowNum();
//
//        for (Object[] aBook : bookData) {
//            Row row = sheet.createRow(rowIndex);
//
//            int columnCount = 0;
//
//            Cell cell = row.createCell(cellNum);
//            cell.setCellValue(value);
//
//            for (Object field : aBook) {
//                cell = row.createCell(++columnCount);
//                if (field instanceof String) {
//                    cell.setCellValue((String) value);
//                } else if (field instanceof Integer) {
//                    cell.setCellValue((Integer) field);
//                }
//            }
//
//        }
            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(pathToExcel);
            workbook.write(outputStream);
            outputStream.close();

        } catch (IOException | EncryptedDocumentException | InvalidFormatException ex) {
            ex.printStackTrace();
        }
    }


    public void writeTo2 (int rowIndex, int cellNum) throws Exception {
        FileInputStream path = setPath();
        FileOutputStream os = new FileOutputStream(String.valueOf(path));
//Access the workbook
        HSSFWorkbook workbook = new HSSFWorkbook(path);
//Access the worksheet, so that we can update / modify it.
        HSSFSheet worksheet = workbook.getSheetAt(0);
// declare a Cell object
        Cell cell = null;
// Access the second cell in second row to update the value
        cell = worksheet.getRow(rowIndex).getCell(cellNum);
// Get current cell value value and overwrite the value
        cell.setCellValue("OverRide existing value");
//Close the InputStream

//Open FileOutputStream to write updates
//        try {
//        FileOutputStream output_file = new FileOutputStream((String.valueOf(path)));
//            workbook.write(output_file);
//            output_file.close(); }
//
//            catch (FileNotFoundException ex) {
////                Logger.getLogger(PruebaExcel.class.getName()).log(Level.SEVERE, null, ex);
//            }
        workbook.write(os);
        os.close();
//        //write changes
//        workbook.write(output_file);
////close the stream
//        output_file.close();
    }


public void another() throws Exception {
    FileInputStream path = setPath();
    File f = new File(System.getProperty(String.valueOf(path)));

    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet worksheet = workbook.getSheet("NewEmployees");
    HSSFRow row = worksheet.getRow(1);
    HSSFCell cell = row.getCell(1);
    cell.setCellValue("changed");

//    enter code here

    FileOutputStream output_file =new FileOutputStream(new File(String.valueOf(path)));
    workbook.write(output_file);
    output_file.close();
}
    public static void change() throws Exception {
        HSSFWorkbook workbook=null;
        HSSFSheet sheet;
        FileInputStream path = setPath();

            FileInputStream file = new FileInputStream(new File(String.valueOf(path)));

            //Create Workbook instance holding reference to .xls file
            workbook = new HSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            //Most of people make mistake by making new sheet by looking in tutorial
            sheet = workbook.getSheetAt(workbook.getActiveSheetIndex());


            //Get the count in sheet
            int rowCount = sheet.getLastRowNum()+1;
            Row row = sheet.createRow(4);
            System.out.println();
            Cell c1 = row.createCell(2);
            c1.setCellValue("one");
            Cell c2 = row.createCell(1);
            c2.setCellValue("two");
            Cell c3 = row.createCell(3);
            c3.setCellValue("three");


            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(String.valueOf(path)));
            workbook.write(out);
            out.close();
            System.out.println("Update Successfully");

    }

}