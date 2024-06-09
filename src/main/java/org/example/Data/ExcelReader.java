package org.example.Data;

import org.apache.poi.ss.usermodel.*;
import org.example.filter.FilterQuery;
import org.example.model.Task;
import org.example.utils.ErrorMessages;
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

    public List<Task> readProjectTasks(String projectName, FilterQuery filterQuery) throws IOException {
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
                ErrorMessages.getInstance().addErrorMessage(file.getName() + " - Row " + currentRow.getRowNum() + " contains blank cells");
                continue;
            }

            if (currentRow.getCell(0).getCellType() != CellType.NUMERIC) {
                ErrorMessages.getInstance().addErrorMessage(file.getName() + " - Date in row " + currentRow.getRowNum() + " is not valid date format");
                continue;
            }

            if (currentRow.getCell(1).getCellType() != CellType.STRING) {
                ErrorMessages.getInstance().addErrorMessage(file.getName() + " - Task name in  row " + currentRow.getRowNum() + " is not string");
                continue;
            }

            if (currentRow.getCell(2).getCellType() != CellType.NUMERIC ) {
                ErrorMessages.getInstance().addErrorMessage(file.getName() + " - Time in row " + currentRow.getRowNum() + " is not valid");
                continue;
            }

            if (currentRow.getCell(2).getNumericCellValue() < 0 ) {
                ErrorMessages.getInstance().addErrorMessage(file.getName() + " - Time in row " + currentRow.getRowNum() + " is negative value");
                continue;
            }

            Date date = currentRow.getCell(0).getDateCellValue();
            String name = currentRow.getCell(1).getStringCellValue();
            BigDecimal hours = BigDecimal.valueOf(currentRow.getCell(2).getNumericCellValue());

            if (filterQuery.fromDate != null && date.before(filterQuery.fromDate)) {
                continue;
            }

            if (filterQuery.toDate != null && date.after(filterQuery.toDate)){
                continue;
            }

            if (filterQuery.employee != null & !employee.equalsIgnoreCase(filterQuery.employee)) {
                continue;
            }

            Task task = new Task(employee, name, date, hours, projectName);
            tasks.add(task);
            cellIterator.next();
        }

        return tasks;
    }

    public static List<Task> readEmployeeTasksFromFile(String filename, FilterQuery filterQuery) throws IOException {
        List<Task> tasks = new ArrayList<>();

        ExcelReader excelReader = new ExcelReader(filename);
        List<String> projects = excelReader.readProjectNames();
        for (String project: projects) {
            List<Task> projectTasks = excelReader.readProjectTasks(project, filterQuery);
            tasks.addAll(projectTasks);
        }

        return tasks;
    }

    public static List<Task> readTasksFromMultipleFiles(List<String> filePaths, FilterQuery filterQuery) throws IOException {
        List<Task> tasks = new ArrayList<>();
        for (String filepath: filePaths) {
            List<Task> employeeTasks = readEmployeeTasksFromFile(filepath, filterQuery);
            tasks.addAll(employeeTasks);
        }

        return tasks;
    }


}
