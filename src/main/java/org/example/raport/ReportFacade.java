package org.example.raport;

import org.example.model.Task;
import org.example.utils.WriteToExcel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReportFacade {
    public List<Task> tasks = new ArrayList<>();
    public String filePath;

    public ReportFacade(List<Task> tasks, String filePath) throws IOException {
        this.tasks = tasks;
        this.filePath = filePath;
    }

    public  void report1(String printType){
        String[] HEADERS = {"Nazwa projektu", "Liczba godzin"};

        Report1Generator report1Generator = new Report1Generator();
        List<String> projectNames = List.of(new String[]{"Projekt1", "Projekt2"});

        if(Objects.equals(printType, "console")){
            report1Generator.printReport(tasks);

        } else if (Objects.equals(printType, "xls")){
            WriteToExcel writeToExcel = new WriteToExcel(filePath, HEADERS);

            report1Generator.writeXls(
                    writeToExcel,
                    tasks,
                    projectNames
            );

            writeToExcel.saveToFile();

        } else {
            System.out.println("Do wyboru jest tylko 'console' lub 'xls'");
        }
    }

    public  void report2(){

    }

    public  void report3(String printType){
        String[] HEADERS = {"Nazwa zadania", "Liczba godzin"};

        Report3Generator report3Generator = new Report3Generator();
//        report3Generator.printReport(tasks);
        if(Objects.equals(printType, "console")){
            report3Generator.printReport(tasks);

        } else if (Objects.equals(printType, "xls")){
            WriteToExcel writeToExcel = new WriteToExcel(filePath, HEADERS);

            report3Generator.writeXls(
                    writeToExcel,
                    tasks
            );

            writeToExcel.saveToFile();

        } else {
            System.out.println("Do wyboru jest tylko 'console' lub 'xls'");
        }
    }
}
