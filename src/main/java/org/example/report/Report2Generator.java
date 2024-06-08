package org.example.report;

import org.example.model.Task;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class Report2Generator implements IGenerateReportDetailed{

    public HashMap<String, BigDecimal> getReportData(List<Task> tasks) {
        HashMap<String, BigDecimal> projectDataMap = new HashMap<String, BigDecimal>();

        for (Task task: tasks) {
            String employee = task.getEmployee();
            BigDecimal hours = task.getHours();

            if (projectDataMap.containsKey(employee)) {
                hours = projectDataMap.get(employee).add(hours);
                projectDataMap.put(employee, hours);
            } else {
                projectDataMap.put(employee, hours);
            }
        }

        return projectDataMap;
    }

    public HashMap<String, HashMap<String, BigDecimal>> getDetailedReportData(List<Task> tasks) {
        HashMap<String, HashMap<String, BigDecimal>> projectDataMap = new HashMap<>();

        for (Task task : tasks) {
            String projectName = task.getProjectName();
            String employee = task.getEmployee();
            BigDecimal hours = task.getHours();

            projectDataMap.putIfAbsent(employee, new HashMap<>());
            HashMap<String, BigDecimal> emplyeeProjectMap = projectDataMap.get(employee);

            emplyeeProjectMap.put(projectName, emplyeeProjectMap.getOrDefault(projectName, BigDecimal.ZERO).add(hours));
        }

        return projectDataMap;
    }
}
