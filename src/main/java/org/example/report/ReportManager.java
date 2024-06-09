package org.example.report;

public class ReportManager {
    public static IGenerateReport getReportGenerator(String option){
        switch (option) {
            case "1":
                return new Report1Generator();

            case "2":
                return new Report2Generator();

            case "3":
                return new Report3Generator();

            default:
                throw new IllegalArgumentException("Invalid report type: " + option);
        }
    }
}
