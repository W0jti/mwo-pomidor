package org.example;

import org.apache.commons.cli.*;
import org.example.Data.ExcelReader;
import org.example.Data.FileSearcher;
import org.example.export.IExporter;
import org.example.export.PdfExport;
import org.example.model.Task;
import org.example.report.*;
import org.example.utils.ExcelExport;

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
            .longOpt("usage")
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


    private static void writeDataToExcel(
            IGenerateReportDetailed reportGenerator,
            String filePathExport,
            String[] HEADERS,
            List<Task> tasks
    ){
        ExcelExport excelExport = new ExcelExport(filePathExport, HEADERS);

        reportGenerator.writeXls(
                excelExport,
                tasks
        );

        excelExport.saveToFile();
    }

    public static void main(String[] args) throws IOException, ParseException {

        Options options = new Options();
        options.addOption(ARG_PATH);
        options.addOption(ARG_REPORT_TYPE);
        options.addOption(ARG_HELP);
        options.addOption(ARG_EXPORT);
        options.addOption(ARG_CHART);
        options.addOption(ARG_REPORT_OPTION);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")){
            usage(options);
        }

        if (cmd.hasOption("r") && cmd.hasOption("p")) {
            String path = cmd.getOptionValue("p");
            String reportOption = cmd.getOptionValue("r");
            boolean detailed = cmd.hasOption("d");

            List<String> filePaths = FileSearcher.searchXlsFiles(path);
            List<Task> tasks = ExcelReader.readTasksFromMultipleFiles(filePaths);

            IExporter exporter;
            IGenerateReportDetailed reportGenerator;
            switch (reportOption) {
                case "1":
                    reportGenerator = new Report1Generator();

                    String filePathExport = "C:\\Users\\sebas\\Desktop\\report1.xlsx";
                    String[] HEADERS = {"Nazwa projektu", "Liczba godzin"};

                    writeDataToExcel(
                            reportGenerator,
                            filePathExport,
                            HEADERS,
                            tasks
                    );

                    break;

                case "2":
                    reportGenerator = new Report2Generator();
                    break;

                case "3":
                    reportGenerator = new Report3Generator();
                    break;

                default:
                    throw new IllegalArgumentException("Invalid report type: " + reportOption);
            }

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