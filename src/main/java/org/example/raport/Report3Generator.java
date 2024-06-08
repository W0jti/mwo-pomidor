package org.example.raport;

import org.example.model.Task;

import java.math.BigDecimal;
import java.util.*;

public class Report3Generator {

    public HashMap<String, HashMap<String, BigDecimal>> getRaportData(List<Task> tasks) {
        HashMap<String, HashMap<String, BigDecimal>> projectDataMap = new HashMap<>();

        for (Task task : tasks) {
            String projectName = task.getProjectName();
            String taskName = task.getName();
            BigDecimal hours = task.getHours();

            projectDataMap.putIfAbsent(projectName, new HashMap<>());
            HashMap<String, BigDecimal> taskDataMap = projectDataMap.get(projectName);

            taskDataMap.put(taskName, taskDataMap.getOrDefault(taskName, BigDecimal.ZERO).add(hours));
        }

        return projectDataMap;
    }

    private Map<String, BigDecimal> sortByValues(Map<String, BigDecimal> map) {
        List<Map.Entry<String, BigDecimal>> sortedByValueList = new LinkedList<>(map.entrySet());
        Collections.sort(sortedByValueList,
                Comparator.comparing(Map.Entry<String, BigDecimal>::getValue).reversed());

        Map<String, BigDecimal> sortedHashMap = new LinkedHashMap<>();
        for (Map.Entry<String, BigDecimal> entry : sortedByValueList) {
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public void printReport(List<Task> tasks) {
        HashMap<String, HashMap<String, BigDecimal>> projectData = getRaportData(tasks);

        // Sum up total hours per project
        HashMap<String, BigDecimal> totalHoursPerProject = new HashMap<>();
        for (Map.Entry<String, HashMap<String, BigDecimal>> entry : projectData.entrySet()) {
            String projectName = entry.getKey();
            BigDecimal totalHours = entry.getValue().values().stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalHoursPerProject.put(projectName, totalHours);
        }

        // Sort projects by total hours
        Map<String, BigDecimal> sortedProjects = sortByValues(totalHoursPerProject);

        // Print sorted projects with their tasks
        for (Map.Entry<String, BigDecimal> projectEntry : sortedProjects.entrySet()) {
            String projectName = projectEntry.getKey();
            BigDecimal totalHours = projectEntry.getValue();
            System.out.println(projectName + " - " + totalHours + "h");

            HashMap<String, BigDecimal> tasksInProject = projectData.get(projectName);
            Map<String, BigDecimal> sortedTasks = sortByValues(tasksInProject);

            for (Map.Entry<String, BigDecimal> taskEntry : sortedTasks.entrySet()) {
                System.out.println("  " + taskEntry.getKey() + " - " + taskEntry.getValue() + "h");
            }
        }
    }
}
