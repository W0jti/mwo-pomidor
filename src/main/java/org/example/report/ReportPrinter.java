package org.example.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ReportPrinter {

    public static void print(HashMap<String, BigDecimal> data){
        for (Map.Entry<String, BigDecimal> set : data.entrySet()) {
            System.out.println(set.getKey() + " - " + set.getValue() + "h");
        }
    }

//    public static void prrint2(HashMap<String, HashMap<String, BigDecimal>> data){
//        Map<String, Map<String, BigDecimal>> projectData = getRaportData(tasks);
//        Map<String, BigDecimal> employeeHours = getEmployeeHours(tasks);
//
//        System.out.println("\nCałkowita liczba godzin przepracowanych przez pracowników:");
//        for (Map.Entry<String, BigDecimal> employeeEntry : employeeHours.entrySet()) {
//            String employee = employeeEntry.getKey();
//            BigDecimal hours = employeeEntry.getValue();
//            System.out.println("  " + employee + " - " + hours + "h");
//        }
//        System.out.println("\nLiczba godzin przepracowanych przez pracowników w projektach:");
//        for (Map.Entry<String, Map<String, BigDecimal>> projectEntry : projectData.entrySet()) {
//            String project = projectEntry.getKey();
//            System.out.println( project);
//
//
//            for (Map.Entry<String, BigDecimal> employeeEntry : projectEntry.getValue().entrySet()) {
//                String employee = employeeEntry.getKey();
//                BigDecimal hours = employeeEntry.getValue();
//                System.out.println("  " + employee + " - " + hours + "h");
//            }
//        }
//    }


    public static void printDetailed(HashMap<String, HashMap<String, BigDecimal>> data) {
        for (Map.Entry<String, HashMap<String, BigDecimal>> set : data.entrySet()) {
            BigDecimal totalHours = set.getValue().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            System.out.println(set.getKey() + " = " + totalHours + " h");
            for (Map.Entry<String, BigDecimal> details : set.getValue().entrySet()) {
                System.out.println("\t" + details.getKey() + " - " + details.getValue() + " h");
            }
            System.out.println("\n");
        }
    }
}
