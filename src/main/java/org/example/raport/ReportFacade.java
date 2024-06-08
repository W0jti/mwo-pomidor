package org.example.raport;

import org.example.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportFacade {
    public List<Task> tasks = new ArrayList<>();

    public ReportFacade( List<Task> tasks) throws IOException {
        this.tasks = tasks;
    }

    public  void report1(){
        Report1Generator report1Generator = new Report1Generator();
        report1Generator.printReport(tasks);
    }

    public  void report2(){

    }

    public  void report3(){
        Report3Generator report3Generator = new Report3Generator();
        report3Generator.printReport(tasks);
    }
}
