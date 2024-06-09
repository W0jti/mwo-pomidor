package org.example;

import org.apache.commons.cli.*;
import org.example.Data.ExcelReader;
import org.example.Data.FileSearcher;
import org.example.export.IExporter;
import org.example.export.PdfExport;
import org.example.filter.FilterQuery;
import org.example.model.Task;
import org.example.report.*;
import org.example.utils.ErrorMessages;
import org.example.export.ExcelExport;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.SwingWrapper;

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
            .longOpt("reportoption")
            .desc("Report type: 1/2/3")
            .hasArg()
            .build();

    private final static Option ARG_REPORT_TYPE = Option.builder("d")
            .argName("reportType")
            .longOpt("reporttype")
            .desc("Report type: detailed")
            .build();

    private final static Option ARG_HELP = Option.builder("h")
            .argName("help")
            .longOpt("help")
            .desc("How to use")
            .build();

    private final static Option ARG_EXPORT_TYPE = Option.builder("e")
            .argName("export")
            .longOpt("exporttype")
            .desc("Export pdf or excel")
            .hasArg()
            .build();

    private final static Option ARG_PATH_EXPORT = Option.builder("ep")
            .argName("exportPath")
            .longOpt("exportpath")
            .desc("Export path")
            .hasArg()
            .build();

    private final static Option ARG_EXPORT_FILENAME = Option.builder("efn")
            .argName("exportFilename")
            .longOpt("exportfilename")
            .desc("Export filename")
            .hasArg()
            .build();

    private final static Option ARG_CHART = Option.builder("c")
            .argName("chart")
            .longOpt("chart")
            .desc("Generate chart image")
            .build();

    private final static Option ARG_FROM = Option.builder("f")
            .argName("from")
            .longOpt("datefrom")
            .desc("Date from")
            .hasArg()
            .build();

    private final static Option ARG_TO = Option.builder("t")
            .argName("to")
            .longOpt("dateto")
            .desc("Date to")
            .hasArg()
            .build();

    private final static Option ARG_EMPLOYEE = Option.builder("emp")
            .argName("employee")
            .longOpt("employee")
            .desc("Employee")
            .hasArg()
            .build();

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {

        Options options = new Options();
        options.addOption(ARG_PATH);
        options.addOption(ARG_REPORT_TYPE);
        options.addOption(ARG_HELP);
        options.addOption(ARG_EXPORT_TYPE);
        options.addOption(ARG_PATH_EXPORT);
        options.addOption(ARG_EXPORT_FILENAME);
        options.addOption(ARG_CHART);
        options.addOption(ARG_REPORT_OPTION);
        options.addOption(ARG_FROM);
        options.addOption(ARG_TO);
        options.addOption(ARG_EMPLOYEE);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        String from = cmd.hasOption(ARG_FROM) ? cmd.getOptionValue(ARG_FROM) : null;
        String to = cmd.hasOption(ARG_TO) ? cmd.getOptionValue(ARG_TO) : null;
        String employee = cmd.hasOption(ARG_EMPLOYEE) ? cmd.getOptionValue(ARG_EMPLOYEE) : null;
        FilterQuery filterQuery = new FilterQuery(from,to,employee);

        if (cmd.hasOption(ARG_HELP)){
            usage(options);
        }

        if (cmd.hasOption(ARG_REPORT_OPTION) && cmd.hasOption(ARG_PATH)) {
            String path = cmd.getOptionValue(ARG_PATH);
            String reportOption = cmd.getOptionValue(ARG_REPORT_OPTION);
            boolean detailed = cmd.hasOption(ARG_REPORT_TYPE);

            boolean hasExportType = cmd.hasOption(ARG_EXPORT_TYPE);
            boolean hasPathExport = cmd.hasOption(ARG_PATH_EXPORT);
            boolean hasExportFilename = cmd.hasOption(ARG_EXPORT_FILENAME);

            List<String> filePaths = FileSearcher.searchXlsFiles(path);
            List<Task> tasks = ExcelReader.readTasksFromMultipleFiles(filePaths, filterQuery);

            IExporter exporterPdf;
            IGenerateReport reportGenerator = ReportManager.getReportGenerator(reportOption);


            if(detailed) {
                HashMap<String, HashMap<String, BigDecimal>> data = reportGenerator.getDetailedReportData(tasks);
                ReportPrinter.printDetailed(data);
                exporterPdf = new PdfExport(null, data);

            } else {
                HashMap<String, BigDecimal> data = reportGenerator.getReportData(tasks);
                ReportPrinter.print(data);
                exporterPdf = new PdfExport(data, null);


                if (cmd.hasOption(ARG_CHART)){
                    BarChart<CategoryChart> exampleChart = new Charts();
                    CategoryChart chart = exampleChart.getChart(data);
                    new SwingWrapper<CategoryChart>(chart).displayChart();
                }
            }

            if (hasExportType) {

                String exportFilename = hasExportFilename
                        ? cmd.getOptionValue(ARG_EXPORT_FILENAME)
                        : "report";

                String filePathExport = hasPathExport
                        ? cmd.getOptionValue(ARG_PATH_EXPORT) + "\\" +exportFilename
                        : exportFilename;

                filePathExport = cmd.getOptionValue(ARG_EXPORT_TYPE).equals("excel")
                        ? filePathExport+".xlsx"
                        : filePathExport+".pdf";


                System.out.println(cmd.getOptionValue(ARG_EXPORT_TYPE));

                String exportType =  cmd.getOptionValue(ARG_EXPORT_TYPE);

                if (detailed){
                    if (exportType.equals("pdf")) {
                        exporterPdf.exportDetailed(filePathExport);
                    }
                    else if (exportType.equals("excel")){
                        writeDataToExcel(
                                reportGenerator,
                                filePathExport,
                                tasks,
                                true
                        );
                    } else {
                        System.out.println("Invalid export type");
                    }

                } else {

                    if (exportType.equals("pdf")){
                        exporterPdf.export(filePathExport);
                    } else if (exportType.equals("excel")){
                        writeDataToExcel(
                                reportGenerator,
                                filePathExport,
                                tasks,
                                false
                        );
                    } else {
                        System.out.println("Invalid export type");
                    }
                }
            }
        }

        ErrorMessages.getInstance().show();
    }

    public static void usage(Options options) {
        String header = "Report generator\n";
        String footer = "\nPlease report issues at https://github.com/W0jti/mwo-pomidor";

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("ReportGenerator", header, options, footer, true);
    }

    private static void writeDataToExcel(
            IGenerateReport reportGenerator,
            String filePathExport,
            List<Task> tasks,
            boolean isDetailed
    ){
        ExcelExport excelExport = new ExcelExport(filePathExport);

        reportGenerator.writeXls(
                excelExport,
                tasks,
                isDetailed
        );

        excelExport.saveToFile();
    }
}