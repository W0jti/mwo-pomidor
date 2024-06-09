package org.example.report;

import org.example.model.Task;
import org.example.utils.ExcelExport;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Report3Generator implements  IGenerateReport{

    public HashMap<String, HashMap<String, BigDecimal>> getDetailedReportData(List<Task> tasks) {
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

    public HashMap<String, BigDecimal> getReportData(List<Task> tasks) {
        List<String> taskNames = tasks.stream().map(Task::getName).collect(Collectors.toList());

        HashMap<String, BigDecimal> taskHourMap = new HashMap<>();

        for (String taskName: taskNames) {
            List<Task> filteredTasks = tasks.stream().filter(t -> t.name.equals(taskName)).toList();
            BigDecimal hoursPerTask = new BigDecimal(0);
            if(!filteredTasks.isEmpty()){
                for (Task task : filteredTasks) {
                    BigDecimal taskHours = task.getHours();
                    hoursPerTask = hoursPerTask.add(taskHours);
                }
                taskHourMap.put(taskName, hoursPerTask);
            }
        }

        return (HashMap<String, BigDecimal>) sortByValues(taskHourMap);
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

    public void writeXls(ExcelExport excelExport, List<Task> tasks, boolean isDetailed) {

        if(isDetailed) {
            String[] headers = new String[]{"Nazwa projektu", "Nazwa zadania", "Liczba godzin"};
            excelExport.addHeaderRow(headers);

            for (Map.Entry<String, HashMap<String, BigDecimal>> entry : getDetailedReportData(tasks).entrySet()) {
                String key = entry.getKey();

                excelExport.addRow();
                excelExport.addCell(0, key);

                for (Map.Entry<String, BigDecimal> entry2 : entry.getValue().entrySet()){
                    String key2 = entry2.getKey();
                    BigDecimal value = entry2.getValue();

                    excelExport.addRow();
                    excelExport.addCell(0, key);
                    excelExport.addCell(1, key2);
                    excelExport.addCell(2, value);
                }
                excelExport.addRow();
            }
        } else {
            String[] headers = new String[]{"Nazwa zadania", "Liczba godzin"};
            excelExport.addHeaderRow(headers);

            for (Map.Entry<String, BigDecimal> entry : getReportData(tasks).entrySet()) {
                String key = entry.getKey();
                BigDecimal value = entry.getValue();

                excelExport.addRow();
                excelExport.addCell(0, key);
                excelExport.addCell(1, value);
            }
        }
    }

}
