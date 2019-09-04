package automation.actions;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.math.BigDecimal;

public class ExcelReader {

    /*
    Method sets path for different OS systems
     */
    public static FileInputStream setPath() throws Exception {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            FileInputStream path = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/ddf.xls");
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
    public int getUnregisteredUser(String sheetName, String columnName) throws Exception{
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
        HSSFSheet sheet = workbook.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();
//      keywords to find unregistered user
        String locator = "not registered";
        String locator0 = "notregistered";

//      sets column's location (should be 'Status')
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
                    if (locator.equals(text) || locator0.equals(text)) {
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

    public void writeToCell(String sheetName, int rowIndex, int columnInt, String value) throws Exception {
        try {
//            set path
            FileInputStream path = setPath();
            FileOutputStream os = new FileOutputStream(String.valueOf(path));
            HSSFWorkbook workbook = new HSSFWorkbook(path);
            //            sheets
            HSSFSheet sheet = workbook.getSheet(sheetName);


//           rows
//        Row row = sheet.createRow(rowIndex);

        Row row = sheet.getRow(rowIndex);

//            cells
        Cell cell = row.createCell(columnInt);

//        Cell cell = row.getCell(columnInt);
        cell.setCellValue(value);


        workbook.write(os);
        System.out.println(cell+ "value inputted");

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public void writeTo () throws Exception {
    FileInputStream path = setPath();
    FileInputStream fsIP= new FileInputStream(new File(String.valueOf(path)));
//Access the workbook
    HSSFWorkbook wb = new HSSFWorkbook(fsIP);
//Access the worksheet, so that we can update / modify it.
    HSSFSheet worksheet = wb.getSheetAt(0);
// declare a Cell object
    Cell cell = null;
// Access the second cell in second row to update the value
    cell = worksheet.getRow(1).getCell(1);
// Get current cell value value and overwrite the value
    cell.setCellValue("OverRide existing value");
//Close the InputStream
    fsIP.close();
//Open FileOutputStream to write updates
    FileOutputStream output_file =new FileOutputStream(new File(String.valueOf(path)));
    //write changes
    wb.write(output_file);
//close the stream
    output_file.close();
}
}