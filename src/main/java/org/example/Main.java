package org.example;

import org.example.Data.ExcelReaderFacade;
import org.example.Data.FileSearcher;
import org.example.model.Task;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        String filePath = "src/resources/reporter-dane/reporter-dane/2012/01/Kowalski_Jan.xls";

        ExcelReaderFacade excelReader = new ExcelReaderFacade();

        String folderPath = "/var/home/student/Desktop/dane/2012";

        FileSearcher fileSearcher = new FileSearcher();
        List<String> filePaths = fileSearcher.searchXlsFile(folderPath);
        for (String filepath: filePaths) {
            System.out.println("Reading file: " + filepath);
            List<Task> employeeTasks = excelReader.readEmployeeTasks(filepath);
            for (Task task: employeeTasks) {
                System.out.println(task);
            }
            System.out.println("\n\n");
        }
    }
}