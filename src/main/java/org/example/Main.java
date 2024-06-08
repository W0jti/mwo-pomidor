package org.example;

import org.example.Data.ExcelReaderFacade;
import org.example.model.Task;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ////// ustaw ścieżkę do swojego pliku z danymi /////

        String filePath = "D:\\reporter-dane\\2012\\01\\Kowalski_Jan.xls";
        ExcelReaderFacade excelReader = new ExcelReaderFacade();

        List<Task> employeeTasks = excelReader.readEmployeeTasks(filePath);

        Report1Generator report1Generator = new Report1Generator();
        report1Generator.countHoursPerProject(employeeTasks, new String[]{"Projekt1", "Projekt2", "Projekt3"});
    }
}