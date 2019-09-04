//package automation.actions;
//
//import org.apache.poi.EncryptedDocumentException;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.*;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class test {
//
//    public static void again() {
//        String excelFilePath = "JavaBooks.xls";
//
//        try {
//            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
//            Workbook workbook = WorkbookFactory.create(inputStream);
//
//            Sheet sheet = workbook.getSheetAt(0);
////            Cell cell2Update = sheet.getRow(1).getCell(3);
////            cell2Update.setCellValue(49);
//            Object bookData = "hrllo";
//
//
//
//
//            }
//
//            inputStream.close();
//
//            FileOutputStream outputStream = new FileOutputStream("JavaBooks.xls");
//            workbook.write(outputStream);
//            outputStream.close();
//
//
//        } catch (IOException | EncryptedDocumentException | InvalidFormatException ex) {
//            ex.printStackTrace();
//        }
//    }
//}
