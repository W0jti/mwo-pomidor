package org.example;

import org.example.Data.ExcelReaderFacade;
import org.example.Data.FileSearcher;
import org.example.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        ExcelReaderFacade excelReader = new ExcelReaderFacade();
        Report1Generator report1Generator = new Report1Generator();

        String folderPath = "/var/home/student/Desktop/dane/2012";

        List<String> filePaths = FileSearcher.searchXlsFiles(folderPath);
        List<Task> tasks = new ArrayList<>();

        for (String filepath: filePaths) {
            List<Task> employeeTasks = excelReader.readEmployeeTasksFromFile(filepath);
            tasks.addAll(employeeTasks);
        }

        report1Generator.printRaport(tasks);

    }
}