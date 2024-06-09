package org.example.raport;

import org.example.model.Task;

import java.math.BigDecimal;
import java.util.*;

public class Report2Generator {

    public Map<String, Map<String, BigDecimal>> getRaportData(List<Task> tasks) {
        Map<String, Map<String, BigDecimal>> projectDataMap = new HashMap<>();

        for (Task task : tasks) {
            String project = task.getProjectName();
            String employee = task.getEmployee();
            BigDecimal hours = task.getHours();

            projectDataMap
                    .computeIfAbsent(project, k -> new HashMap<>())
                    .merge(employee, hours, BigDecimal::add);
        }

        return projectDataMap;
    }

    public Map<String, BigDecimal> getEmployeeHours(List<Task> tasks) {
        Map<String, BigDecimal> employeeHoursMap = new HashMap<>();

        for (Task task : tasks) {
            String employee = task.getEmployee();
            BigDecimal hours = task.getHours();

            employeeHoursMap.merge(employee, hours, BigDecimal::add);
        }

        return employeeHoursMap;
    }

    public void printReport(List<Task> tasks) {
        Map<String, Map<String, BigDecimal>> projectData = getRaportData(tasks);
        Map<String, BigDecimal> employeeHours = getEmployeeHours(tasks);

        System.out.println("\nCałkowita liczba godzin przepracowanych przez pracowników:");
        for (Map.Entry<String, BigDecimal> employeeEntry : employeeHours.entrySet()) {
            String employee = employeeEntry.getKey();
            BigDecimal hours = employeeEntry.getValue();
            System.out.println("  " + employee + " - " + hours + "h");
        }
        System.out.println("\nLiczba godzin przepracowanych przez pracowników w projektach:");
        for (Map.Entry<String, Map<String, BigDecimal>> projectEntry : projectData.entrySet()) {
            String project = projectEntry.getKey();
            System.out.println( project);


            for (Map.Entry<String, BigDecimal> employeeEntry : projectEntry.getValue().entrySet()) {
                String employee = employeeEntry.getKey();
                BigDecimal hours = employeeEntry.getValue();
                System.out.println("  " + employee + " - " + hours + "h");
            }
        }
    }
}