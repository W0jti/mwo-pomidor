package org.example.raport;

import org.example.model.Task;
import org.example.utils.WriteToExcel;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;


public class Report3Generator {
    public HashMap<String, BigDecimal> getRaportData(List<Task> tasks) {
        HashMap<String, BigDecimal> projectDataMap = new HashMap<String, BigDecimal>();

        for (Task task: tasks) {
            String taskName = task.getName();
            BigDecimal hours = task.getHours();

            if (projectDataMap.containsKey(taskName)) {
                hours = projectDataMap.get(taskName).add(hours);
                projectDataMap.put(taskName, hours);
            } else {
                projectDataMap.put(taskName, hours);
            }
        }

        return projectDataMap;
    }


    private Map<String, BigDecimal> sortByValues(Map<String, BigDecimal> map) {
        List sortedByValueList = new LinkedList(map.entrySet());
        Collections.sort(sortedByValueList,
                new Comparator<Map.Entry<String, BigDecimal>>() {
                    public int compare(Entry<String, BigDecimal> o1,
                                       Entry<String, BigDecimal> o2) {
                        return ((Comparable) (o2).getValue())
                                .compareTo((o1).getValue());
                    }
                });
        Map<String, BigDecimal> sortedHashMap = new LinkedHashMap<String, BigDecimal>();
        for (Iterator it = sortedByValueList.iterator(); it.hasNext();) {
            Map.Entry<String, BigDecimal> entry = (Map.Entry) it.next();
            sortedHashMap.put(entry.getKey(), entry.getValue());
        }
        return sortedHashMap;
    }

    public void printReport(List<Task> tasks) {
        HashMap<String, BigDecimal>  projectData = getRaportData(tasks);
        Map<String, BigDecimal> projectDataSorted = sortByValues(projectData);

        for (Map.Entry<String, BigDecimal> set : projectDataSorted.entrySet()) {
            System.out.println(set.getKey() + " - " + set.getValue() + "h");
        }
    }

    public void writeXls(WriteToExcel writeToExcel, List<Task> tasks) {
        HashMap<String, BigDecimal>  projectData = getRaportData(tasks);
        Map<String, BigDecimal> projectDataSorted = sortByValues(projectData);

        for (Map.Entry<String, BigDecimal> entry : projectDataSorted.entrySet()) {
            String key = entry.getKey();
            BigDecimal value = entry.getValue();

            writeToExcel.addRow();
            writeToExcel.addCell(0, key);
            writeToExcel.addCell(1, value);
        }
    }
}
