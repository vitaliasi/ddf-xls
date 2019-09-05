package automation.actions;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
            }
        }
        return coefficient;
    }

//      finds cell location with set keywords (returns row)
    public int getRequiredRow(String sheetName, String columnName, String locator) throws Exception{
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
        HSSFSheet sheet = workbook.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();

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
                        return cellRef.getRow();
                    }
                }
            }
        }
        System.out.println("bad getUnregisteredUser");
        return 0;
    }

//    read info from cell
    public HSSFCell getInfoFromColumn(String sheetName, String columnName, int usersRowNumber) throws Exception {
        FileInputStream path = setPath();
        HSSFWorkbook workbook = new HSSFWorkbook(path);
        HSSFSheet sheet = workbook.getSheet(sheetName);

//        gets row number of unregistered employee
        int rowIndex = usersRowNumber;

//        gets name of required column
        int column = returnColumnIndex(sheetName, columnName);
        Row row = sheet.getRow(rowIndex);
        Cell cell = row.getCell(column);
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
            try {
            FileInputStream inputStream = new FileInputStream(new File(pathToExcel));
            Workbook workbook = WorkbookFactory.create(inputStream);

//              find sheet
            Sheet sheetName1 = workbook.getSheet(sheetName);
//              get sheet's index int
            int sheet = workbook.getSheetIndex(sheetName1);

            Row row = workbook.getSheetAt(sheet).getRow(rowIndex);
                try {
//                  remove second cell from first row
                row.removeCell(row.getCell(cellNum));
                } catch (Exception p) {
                }
            Cell cell = row.createCell(cellNum);
            cell.setCellValue(value);

            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(pathToExcel);
            workbook.write(outputStream);
            outputStream.close();

        } catch (IOException | EncryptedDocumentException | InvalidFormatException i) {
            i.printStackTrace();
        }
}

    public void writeIntToExcel(String sheetName, int rowIndex, int cellNum, Integer value) throws Exception {

        String pathToExcel = getExcelStringPath();
        try {
            FileInputStream inputStream = new FileInputStream(new File(pathToExcel));
            Workbook workbook = WorkbookFactory.create(inputStream);
//          find sheet
            Sheet sheetName1 = workbook.getSheet(sheetName);
//        get sheet's index int
            int sheet = workbook.getSheetIndex(sheetName1);

            Row row = workbook.getSheetAt(sheet).getRow(rowIndex);

            try {
//               remove second cell from first row
                row.removeCell(row.getCell(cellNum));
            } catch (Exception p) {
            }

            Cell cell = row.createCell(cellNum);
            cell.setCellValue(value);

            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(pathToExcel);
            workbook.write(outputStream);
            outputStream.close();

        } catch (IOException | EncryptedDocumentException | InvalidFormatException ex) {
            ex.printStackTrace();
        }
    }
}