package org.example.report;

import org.example.model.Task;
import org.example.utils.ExcelExport;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Report1Generator implements IGenerateReport{

    public HashMap<String, BigDecimal> getReportData(List<Task> tasks){
        List<String> projectNames = tasks.stream().map(Task::getProjectName).collect(Collectors.toList());
        HashMap<String, BigDecimal> projectHourMap = new HashMap<String, BigDecimal>();
        for (String projectName: projectNames) {
            List<Task> filteredTasks = tasks.stream().filter(t -> t.projectName.equals(projectName)).toList();
            BigDecimal hoursPerProject = new BigDecimal(0);
            if(!filteredTasks.isEmpty()){
                for (Task task : filteredTasks) {
                    BigDecimal taskHours = task.getHours();
                    hoursPerProject = hoursPerProject.add(taskHours);
                }
                projectHourMap.put(projectName, hoursPerProject);
            }
        }

        return projectHourMap;
    }


    public  HashMap<String, HashMap<String, BigDecimal>> getDetailedReportData(List<Task> tasks) {
        HashMap<String, HashMap<String, BigDecimal>> projectSummary = new HashMap<>();

        for (Task task : tasks) {
            String projectName = task.getProjectName();
            String employeeName = task.getEmployee();
            BigDecimal hours = task.getHours();

            // Check if the project already exists in the map
            if (!projectSummary.containsKey(projectName)) {
                projectSummary.put(projectName, new HashMap<>());
            }

            HashMap<String, BigDecimal> employeeHours = projectSummary.get(projectName);

            if (!employeeHours.containsKey(employeeName)) {
                employeeHours.put(employeeName, BigDecimal.ZERO);
            }

            BigDecimal currentHours = employeeHours.get(employeeName);
            employeeHours.put(employeeName, currentHours.add(hours));
        }

        return projectSummary;
    }

    public void writeXls(ExcelExport excelExport, List<Task> tasks, boolean isDetailed) {
        if(isDetailed) {
            String[] headers = new String[]{"Nazwa projektu", "Pracownik", "Liczba godzin"};
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
            String[] headers = new String[]{"Nazwa projektu", "Liczba godzin"};
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
