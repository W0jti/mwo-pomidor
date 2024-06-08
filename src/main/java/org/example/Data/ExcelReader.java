package org.example.Data;

import org.apache.poi.ss.usermodel.*;
import org.example.model.Task;
import org.example.utils.StringExtensions;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExcelReader implements IExcelReader{

    public String filepath;
    public String getFilepath() {
        return filepath;
    }

    public ExcelReader(String filepath) {
        this.filepath = filepath;
    }


    public List<String> readProjectNames() throws IOException {
        List<String> projectNames = new ArrayList<String>();
        File file = new File(filepath);
        String employee = file.getName();

        Workbook workbook = WorkbookFactory.create(file);

        int sheetCount = workbook.getNumberOfSheets();
        for (int i=0 ; i<sheetCount ; i++){
            projectNames.add(workbook.getSheetName(i));
        }
        return projectNames;
    }

    public List<Task> readProjectTasks(String projectName) throws IOException {
        List<Task> tasks = new ArrayList<Task>();

        File file = new File(filepath);
        String employee = StringExtensions.getEmployeeNameFromFilepath(file.getName());
        Workbook workbook = WorkbookFactory.create(file);
        Sheet sheet = workbook.getSheet(projectName);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row currentRow = rowIterator.next();
            Iterator<Cell> cellIterator = currentRow.cellIterator();

            //handle blank cells - for now it skips whole row
            List<CellType> cellTypes = new ArrayList<>();
            for (int i=0 ; i<3 ;i++){
                Cell cell = currentRow.getCell(i);
                if (cell == null){
                    cellTypes.add(CellType.BLANK);
                    continue;
                }
                cellTypes.add(cell.getCellType());
            }

            if (cellTypes.contains(CellType.BLANK)){
                continue;
            }

            Date date = currentRow.getCell(0).getDateCellValue();
            String name = currentRow.getCell(1).getStringCellValue();
            BigDecimal hours = BigDecimal.valueOf(currentRow.getCell(2).getNumericCellValue());


            Task task = new Task(employee, name, date, hours, projectName);
            tasks.add(task);
            cellIterator.next();
        }

        return tasks;
    }

}
