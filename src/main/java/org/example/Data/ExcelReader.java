package org.example.Data;

import org.apache.poi.ss.usermodel.*;
import org.example.model.Task;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {
    public List<Task> readFile() throws IOException {
        String filePath = "/var/home/student/Desktop/dane/2012/01/Kowalski_Jan.xls";
        List<Task> tasks = new ArrayList<Task>();

        File file = new File(filePath);
        String employee = file.getName();

        Workbook workbook = WorkbookFactory.create(file);

        Sheet sheet = workbook.getSheetAt(0);
        String projectName = sheet.getSheetName();

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            Iterator<Cell> cellIterator = currentRow.cellIterator();

            Date date = currentRow.getCell(0).getDateCellValue();
            String name = currentRow.getCell(1).getStringCellValue();
            BigDecimal hours = new BigDecimal(currentRow.getCell(2).getNumericCellValue());

            Task task = new Task(employee, name, date, hours, projectName);
            tasks.add(task);
            cellIterator.next();
        }
        System.out.println("");
        return tasks;
    }

}
