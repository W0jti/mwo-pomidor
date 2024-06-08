package org.example;

import org.example.Data.ExcelReaderFacade;
import org.example.model.Task;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/var/home/student/Desktop/dane/2012/01/Kowalski_Jan.xls";
        ExcelReaderFacade excelReader = new ExcelReaderFacade();

        List<Task> employeeTasks = excelReader.readEmployeeTasks(filePath);
        for (Task task: employeeTasks) {
            System.out.println(task);
        }

    }
}