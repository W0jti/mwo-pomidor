package org.example;

import org.example.Data.ExcelReader;
import org.example.model.Task;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        ExcelReader excelReader = new ExcelReader();
        List<Task> tasks = excelReader.readFile();

        for (Task task: tasks) {
            System.out.println(task);
        }
    }
}