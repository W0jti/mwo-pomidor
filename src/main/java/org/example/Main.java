package org.example;

import org.apache.commons.cli.*;
import org.example.Data.ExcelReader;
import org.example.Data.FileSearcher;
import org.example.export.IExporter;
import org.example.export.PdfExport;
import org.example.filter.FilterQuery;
import org.example.model.Task;
import org.example.report.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class Main {

    private final static Option ARG_PATH = Option.builder("p")
            .argName("path")
            .longOpt("path")
            .hasArg()
            .desc("Folder/file path")
            .build();

    private final static Option ARG_REPORT_OPTION = Option.builder("r")
            .argName("reportOption")
            .longOpt("report-option")
            .desc("Report type: 1/2/3")
            .hasArg()
            .build();

    private final static Option ARG_REPORT_TYPE = Option.builder("d")
            .argName("reportType")
            .longOpt("report-type")
            .desc("Report type: detailed")
            .build();

    private final static Option ARG_HELP = Option.builder("h")
            .argName("help")
            .longOpt("help")
            .desc("How to use")
            .build();

    private final static Option ARG_EXPORT = Option.builder("e")
            .argName("export")
            .longOpt("Export type")
            .desc("Export pdf")
            .hasArg()
            .build();

    private final static Option ARG_CHART = Option.builder("c")
            .argName("chart")
            .longOpt("chart")
            .desc("Generate chart image")
            .build();

    private final static Option ARG_FROM = Option.builder("f")
            .argName("from")
            .longOpt("from")
            .desc("Date from")
            .hasArg()
            .build();

    private final static Option ARG_TO = Option.builder("t")
            .argName("to")
            .longOpt("to")
            .desc("Date to")
            .hasArg()
            .build();

    private final static Option ARG_EMPLOYEE = Option.builder("emp")
            .argName("employee")
            .longOpt("employee")
            .desc("Employeee")
            .hasArg()
            .build();

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {

        Options options = new Options();
        options.addOption(ARG_PATH);
        options.addOption(ARG_REPORT_TYPE);
        options.addOption(ARG_HELP);
        options.addOption(ARG_EXPORT);
        options.addOption(ARG_CHART);
        options.addOption(ARG_REPORT_OPTION);
        options.addOption(ARG_FROM);
        options.addOption(ARG_TO);
        options.addOption(ARG_EMPLOYEE);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String from = cmd.hasOption("f") ? cmd.getOptionValue("f") : null;
        String to = cmd.hasOption("t") ? cmd.getOptionValue("t") : null;
        String employee = cmd.hasOption("emp") ? cmd.getOptionValue("emp") : null;
        FilterQuery filterQuery = new FilterQuery(from,to,employee);

        if (cmd.hasOption("h")){
            usage(options);
        }

        if (cmd.hasOption("r") && cmd.hasOption("p")) {
            String path = cmd.getOptionValue("p");
            String reportOption = cmd.getOptionValue("r");
            boolean detailed = cmd.hasOption("d");

            List<String> filePaths = FileSearcher.searchXlsFiles(path);
            List<Task> tasks = ExcelReader.readTasksFromMultipleFiles(filePaths, filterQuery);

            IExporter exporter;
            IGenerateReport reportGenerator = ReportManager.getReportGenerator(reportOption);


            if(detailed){
                HashMap<String, HashMap<String, BigDecimal>> data = reportGenerator.getDetailedReportData(tasks);
                ReportPrinter.printDetailed(data);
                exporter = new PdfExport(null, data);
            } else {
                HashMap<String, BigDecimal> data = reportGenerator.getReportData(tasks);
                ReportPrinter.print(data);
                exporter = new PdfExport(data, null);
            }

            if (cmd.hasOption("e")) {
                String fileName =  cmd.getOptionValue("e");
                if (detailed){
                    exporter.exportDetailed(fileName);
                }
               else {
                    exporter.export(fileName);
                }
            }
        }
    }

    public static void usage(Options options) {
        String header = "Report generator\n";
        String footer = "\nPlease report issues at https://github.com/W0jti/mwo-pomidor";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ReportGenerator", header, options, footer, true);
    }
}