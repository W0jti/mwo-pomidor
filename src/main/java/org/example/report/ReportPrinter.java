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
