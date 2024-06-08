package org.example.Data;

import org.example.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReaderFacade {
    public List<Task> readEmployeeTasks(String filename) throws IOException {
        List<Task> tasks = new ArrayList<>();

        ExcelReader excelReader = new ExcelReader(filename);
        List<String> projects = excelReader.readProjectNames();
        for (String project: projects) {
            List<Task> projectTasks = excelReader.readProjectTasks(project);
            tasks.addAll(projectTasks);
        }

        return tasks;
    }
}
