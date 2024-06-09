package org.example.raport;

import org.example.model.Task;
import org.example.utils.WriteToExcel;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Report1Generator {

    public void countHoursPerProject(List<Task> tasks, String projectName){
        List<Task> filteredTasks = tasks.stream().filter(t -> t.projectName.equals(projectName)).toList();
        BigDecimal hoursPerProject = new BigDecimal(0);
        System.out.println("===== ZADANIA z: " + projectName + " =====");
        if(!filteredTasks.isEmpty()){
            for (Task task : filteredTasks) {
                BigDecimal taskHours = task.getHours();
                System.out.println("- " + task.getName() +": "+ taskHours +"h");
                hoursPerProject = hoursPerProject.add(taskHours);
            }
            System.out.println("=================");
            System.out.println("Liczba godzin dla " + projectName + ": " + hoursPerProject +"h \n\n");
        } else {
            System.out.println("Brak zadań w projekcie");
        }
    }

    public HashMap<String, BigDecimal> countProjectHours(List<Task> tasks, List<String> projectNames){
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

    public void printReport(List<Task> tasks){
        List<String> projectNames = tasks.stream().map(Task::getProjectName).collect(Collectors.toList());
        HashMap<String, BigDecimal> projectHours = countProjectHours(tasks, projectNames);

        for (Map.Entry<String, BigDecimal> set : projectHours.entrySet()) {
            System.out.println(set.getKey() + " - " + set.getValue() + "h");
        }
    }


    public void writeXls(WriteToExcel writeToExcel, List<Task> tasks, List<String> projectNames) {
        for (Map.Entry<String, BigDecimal> entry : countProjectHours(tasks, projectNames).entrySet()) {
            String key = entry.getKey();
            BigDecimal value = entry.getValue();

            writeToExcel.addRow();
            writeToExcel.addCell(0, key);
            writeToExcel.addCell(1, value);
        }
    }
}
