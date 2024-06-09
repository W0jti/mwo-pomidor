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
import java.util.Objects;

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

    private final static Option ARG_EXPORT_TYPE = Option.builder("e")
            .argName("export")
            .longOpt("Export type")
            .desc("Export pdf or excel")
            .hasArg()
            .build();

//    private final static Option ARG_EXPORT = Option.builder("e")
//            .argName("export")
//            .longOpt("Export type")
//            .desc("Export pdf")
//            .hasArg()
//            .build();

    private final static Option ARG_PATH_EXPORT = Option.builder("pe")
            .argName("pathExport")
            .longOpt("Export path")
            .desc("Export path")
            .hasArg()
            .build();

    private final static Option ARG_EXPORT_FILENAME = Option.builder("efn")
            .argName("exportFilename")
            .longOpt("Export filename")
            .desc("Export filename")
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
            List<Task> tasks,
            boolean isDetailed
    ){
        ExcelExport excelExport = new ExcelExport(filePathExport, HEADERS);

        reportGenerator.writeXls(
                excelExport,
                tasks,
                isDetailed
        );

        excelExport.saveToFile();
    }

    public static void main(String[] args) throws IOException, ParseException {

        Options options = new Options();
        options.addOption(ARG_PATH);
        options.addOption(ARG_REPORT_TYPE);
        options.addOption(ARG_HELP);
        options.addOption(ARG_EXPORT_TYPE);
        options.addOption(ARG_PATH_EXPORT);
        options.addOption(ARG_EXPORT_FILENAME);
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
            boolean hasExportType = cmd.hasOption("e");
            boolean hasPathExport = cmd.hasOption("pe");
            boolean hasExportFilename = cmd.hasOption("efn");

            List<String> filePaths = FileSearcher.searchXlsFiles(path);
            List<Task> tasks = ExcelReader.readTasksFromMultipleFiles(filePaths);

            IExporter exporterPdf;
            IGenerateReportDetailed reportGenerator;
            String exportFilename = hasExportFilename
                                        ? cmd.getOptionValue("efn")
                                        : "report";
            String filePathExport = hasPathExport
                                        ? cmd.getOptionValue("pe")
                                        : "C:\\Users\\sebas\\Desktop\\"+exportFilename+".xlsx";
            String[] HEADERS = new String[0];

            switch (reportOption) {
                case "1":
                    reportGenerator = new Report1Generator();

                    HEADERS = new String[]{"Nazwa projektu", "Liczba godzin"};

                    break;

                case "2":
                    reportGenerator = new Report2Generator();

                    HEADERS = new String[]{"Nazwa projektu", "Liczba godzin"};

                    break;

                case "3":
                    reportGenerator = new Report3Generator();

                    HEADERS = new String[]{"Nazwa projektu", "Nazwa zadania", "Liczba godzin"};

                    break;

                default:
                    throw new IllegalArgumentException("Invalid report type: " + reportOption);
            }

            if(detailed){
                HashMap<String, HashMap<String, BigDecimal>> data = reportGenerator.getDetailedReportData(tasks);
                ReportPrinter.printDetailed(data);
                exporterPdf = new PdfExport(null, data);
            } else {
                HashMap<String, BigDecimal> data = reportGenerator.getReportData(tasks);
                ReportPrinter.print(data);
                exporterPdf = new PdfExport(data, null);
            }

            if (hasExportType) {

                String exportType =  cmd.getOptionValue("e"); // pdf or excel
                System.out.println("========: " + exportType + " ++++: " + Objects.equals(exportType, "excel"));
                if (detailed){
                    if (Objects.equals(exportType, "pdf")){
                        exporterPdf.exportDetailed(filePathExport);
                    } else if (Objects.equals(exportType, "excel")){
                        writeDataToExcel(
                                reportGenerator,
                                filePathExport,
                                HEADERS,
                                tasks,
                                true
                        );
                    } else {
                        System.out.println("nie ma takiego typu exportu");
                    }
                } else {
                    if (Objects.equals(exportType, "pdf")){
                        exporterPdf.export(filePathExport);
                    } else if (Objects.equals(exportType, "excel")){
                        writeDataToExcel(
                                reportGenerator,
                                filePathExport,
                                HEADERS,
                                tasks,
                                false
                        );
                    } else {
                        System.out.println("nie ma takiego typu exportu");
                    }
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