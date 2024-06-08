package org.example.Data;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class ExcelReader {
    public void readFile() throws IOException {
        String filePath = "/var/home/student/Desktop/dane/2012/01/Kowalski_Jan.xls";

        Workbook workbook = WorkbookFactory.create(new File(filePath));

        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            Iterator<Cell> cellIterator = currentRow.cellIterator();

            var data = currentRow.getCell(0).getDateCellValue();
            var task = currentRow.getCell(1).getStringCellValue();
            var hours = currentRow.getCell(2).getNumericCellValue();

            System.out.println(data + " " + task + " " + hours);
            cellIterator.next();
//            while (cellIterator.hasNext()) {
//                Cell cell = cellIterator.next();
//
//
////                System.out.println(date + " = " + task + " == " + hours);
//                System.out.println(cell.getStringCellValue());
//                System.out.println("dasda");
//            }
        }
        System.out.println("");
    }

}
