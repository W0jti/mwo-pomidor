package org.example.raport;

import org.example.model.Task;

import java.math.BigDecimal;
import java.util.*;

public class Report2Generator {

    public HashMap<String, BigDecimal> getRaportData(List<Task> tasks) {
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


    public void printReport(List<Task> tasks) {
        HashMap<String, BigDecimal>  projectData = getRaportData(tasks);

        for (Map.Entry<String, BigDecimal> set : projectData.entrySet()) {
            System.out.println(set.getKey() + " - " + set.getValue() + "h");
        }
    }
}
