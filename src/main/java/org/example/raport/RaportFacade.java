package org.example.raport;

import org.example.model.Task;

import java.util.List;

public class RaportFacade {

    public  void raport1(List<Task> tasks){
        Report1Generator report1Generator = new Report1Generator();
        report1Generator.printRaport(tasks);
    }

    public  void raport2(List<Task> tasks){

    }

    public  void raport3(List<Task> tasks){

    }
}
