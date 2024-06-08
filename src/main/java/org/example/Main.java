package org.example;
import org.apache.commons.cli.*;

public class Main {


    public static void main(String[] args) throws IOException {
        ////// ustaw ścieżkę do swojego pliku z danymi /////

        String filePath = "D:\\reporter-dane\\2012\\01\\Kowalski_Jan.xls";
        ExcelReaderFacade excelReader = new ExcelReaderFacade();

        List<Task> employeeTasks = excelReader.readEmployeeTasks(filePath);

        Report1Generator report1Generator = new Report1Generator();
        report1Generator.countHoursPerProject(employeeTasks, new String[]{"Projekt1", "Projekt2", "Projekt3"});
        String folderPath = "/var/home/student/Desktop/dane/2012";

        FileSearcher fileSearcher = new FileSearcher();
        List<String> filePaths = fileSearcher.searchXlsFile(folderPath);
        for (String filepath: filePaths) {
            System.out.println("Reading file: " + filepath);
            List<Task> employeeTasks = excelReader.readEmployeeTasks(filepath);
            for (Task task: employeeTasks) {
                System.out.println(task);
            }
            System.out.println("\n\n");
        }
    public static void main(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options options = new Options();
        CommandLine cmd = parser.parse(options, args);

        options.addOption("source", true,"Source of the files");//-source resources
        options.addOption("reptype", true,"Type of the report");//-reptype 1
        options.addOption("datefilter", true,"Filter by dates");//-datefilter 2024/04/10-2024/05/20
        options.addOption("employeefilter",true, "Filter by employee");//-employeefilter Kowalski_Jan
        options.addOption("taskfilter", true, "Filter bys task");//-taskfilter spotkanie
        options.addOption("help", false, "Help");//-help


        String source = cmd.getOptionValue("source");
        String repType = cmd.getOptionValue("reptype");
        String dateFilter = cmd.getOptionValue("datefilter");
        String employeeFilter = cmd.getOptionValue("employeefilter");
        String taskFilter = cmd.getOptionValue("taskfilter");
        String help = cmd.getOptionValue("help");

        if (cmd.hasOption("source")){
            //pull data from that source
        }
        else {
            //throw an error
        }

        if(cmd.hasOption("repType")){

        }
    }
}