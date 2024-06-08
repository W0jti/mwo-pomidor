package org.example;

import org.apache.commons.cli.*;
import org.example.Data.ExcelReaderFacade;
import org.example.Data.FileSearcher;
import org.example.model.Task;
import org.example.raport.ReportFacade;

import java.io.IOException;
import java.util.List;

public class Main {

    private final static Option ARG_PATH = Option.builder("p")
            .argName("path")
            .longOpt("path")
            .hasArg()
            .desc("Folder/file path")
            .build();

    private final static Option ARG_REPORT_TYPE = Option.builder("r")
            .argName("reportType")
            .longOpt("report-type")
            .desc("Report type: 1/2/3")
            .hasArg()
            .build();

    private final static Option ARG_HELP = Option.builder("h")
            .argName("help")
            .longOpt("usage")
            .desc("How to use")
            .build();

    public static void main(String[] args) throws IOException, ParseException {

        Options options = new Options();
        options.addOption(ARG_PATH);
        options.addOption(ARG_REPORT_TYPE);
        options.addOption(ARG_HELP);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")){
            usage(options);
        }

        if (cmd.hasOption("r") && cmd.hasOption("p")) {
            generateReport(cmd.getOptionValue("p"), cmd.getOptionValue("r"));
        }
    }


    public static void usage(Options options) {
        String header = "Report generator\n";
        String footer = "\nPlease report issues at https://github.com/W0jti/mwo-pomidor";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ReportGenerator", header, options, footer, true);
    }


    public static void generateReport(String folderPath, String reportNumber) throws IOException {
        ExcelReaderFacade excelReader = new ExcelReaderFacade();
        List<String> filePaths = FileSearcher.searchXlsFiles(folderPath);
        List<Task> tasks = excelReader.readTasksFromMultipleFiles(filePaths);
        ReportFacade reportGenerator = new ReportFacade(tasks);

        switch (reportNumber){
            case "1":
                reportGenerator.report1();
                break;

            case "2":
                reportGenerator.report2();
                break;

            case "3":
                reportGenerator.report3();
                break;
        }
    }
}