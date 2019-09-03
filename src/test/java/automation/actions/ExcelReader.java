package automation.actions;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileInputStream;
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

//    returns only numbers from string as string
    public static String getAllNumbersFromString(String input) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

//    finds cell location with set keywords (returns row)
//    public int getUnregisteredUser(String sheetName, String columnName) throws Exception{
//        FileInputStream path = setPath();
//        HSSFWorkbook workbook = new HSSFWorkbook(path);
//        HSSFSheet sheet = workbook.getSheet(sheetName);
//        DataFormatter formatter = new DataFormatter();
////      keywords to find unregistered user
//        String locator = "not registered";
//        String locator0 = "notregistered";
//
////      sets column's location (should be 'Status')
//        int indexOfColumn = returnColumnIndex(sheetName, columnName);
//
////      loop through column and finds cell with set keyword
//        for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
//            Row row = sheet.getRow(rowIndex);
//            if (row != null) {
//                Cell cell = row.getCell(indexOfColumn);
//                if (cell != null) {
////                    Checks text in required column
//                    String text = formatter.formatCellValue(cell).toLowerCase();
////                    Checks if text matches keyword
//                    if (locator.equals(text) || locator0.equals(text)) {
//                        CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
//                        System.out.println("actual cell of  " + cellRef.formatAsString());
//                        String number = getAllNumbersFromString(cellRef.formatAsString());
//                        int result = Integer.parseInt(number);
//                        System.out.println(result);
//                        return result;
//                    }
//                }
//            }
//        }
//        System.out.println("bad getUnregisteredUser");
//        return 0;
//    }

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
//        int rowIndex = getUnregisteredUser(sheetName, "Status");
        System.out.println(rowIndex+"rowIndex");
//        gets name of required column
        int column = returnColumnIndex(sheetName, columnName);
        System.out.println(column+"column");
        System.out.println("blank found here");
        Row row = sheet.getRow(rowIndex);
        System.out.println("blank found there");
        System.out.println(row);
        System.out.println("blank found1");
        Cell cell = row.getCell(column);
        System.out.println("blank found2");
        System.out.println(cell);
        HSSFCell value = workbook.getSheet(sheetName).getRow(rowIndex).getCell(column);
        System.out.println("blank found 3");

        System.out.println(value);
        return value;
    }


}