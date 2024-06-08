package org.example;

import org.apache.commons.cli.*;
import org.example.Data.ExcelReaderFacade;
import org.example.Data.FileSearcher;
import org.example.model.Task;
import org.example.raport.ReportFacade;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        CommandLine cmd = parser.parse(options, args);

        options.addOption("source", true, "Source of the files");//-source resources
        options.addOption("reptype", true, "Type of the report");//-reptype 1
        options.addOption("datefilter", true, "Filter by dates");//-datefilter 2024/04/10-2024/05/20
        options.addOption("employeefilter", true, "Filter by employee");//-employeefilter Kowalski_Jan
        options.addOption("taskfilter", true, "Filter bys task");//-taskfilter spotkanie
        options.addOption("help", false, "Help");//-help

        String source = cmd.getOptionValue("source");
        String repType = cmd.getOptionValue("reptype");
        String dateFilter = cmd.getOptionValue("datefilter");
        String employeeFilter = cmd.getOptionValue("employeefilter");
        String taskFilter = cmd.getOptionValue("taskfilter");
        String help = cmd.getOptionValue("help");

        if (cmd.hasOption("source")) {
            //pull data from that source
            System.out.println("Report is using source files");
        } else {
            //throw an error
        }

        if (cmd.hasOption("repType")) {
            //select particular report
            System.out.println("Report has selected type");
        } else {
            //throw an error
        }

        if (cmd.hasOption("datafilter")){
            //show data for selected dates
            System.out.println("Report is datefiltered");
        } else {
            //throw an error
        }

        if (cmd.hasOption("employeefilter")){
            //show data for selected employee
            System.out.println("Report is employee filtered");
        } else {
            //throw an error
        }

        if (cmd.hasOption("taskfilter")){
            //show data for that task
            System.out.println("Report is taskfiltered");
        } else {
            //throw an error
        }

        if (cmd.hasOption("help")){
            //show helper
            System.out.println("Showing help");
        }


        String folderPath = "src/resources/reporter-dane/reporter-dane/2012/01/Kowalski_Jan.xls";

        ExcelReaderFacade excelReader = new ExcelReaderFacade();
        List<String> filePaths = FileSearcher.searchXlsFiles(folderPath);
        List<Task> tasks = excelReader.readTasksFromMultipleFiles(filePaths);

        ReportFacade reportGenerator = new ReportFacade(tasks);
        reportGenerator.report2();

    }

}