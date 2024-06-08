package org.example;

import org.example.model.Task;

import java.math.BigDecimal;
import java.util.List;

public class Report1Generator {

    public void countHoursPerProject(List<Task> tasks, String[] projectName){

        for (String project : projectName){
            List<Task> filteredTasks = tasks.stream().filter(t -> t.projectName.equals(project)).toList();
            BigDecimal hoursPerProject = new BigDecimal(0);

            System.out.println("===== ZADANIA z: " + project + " =====");

            if(!filteredTasks.isEmpty()){
                for (Task task : filteredTasks) {
                    BigDecimal taskHours = task.getHours();
                    System.out.println("- " + task.getName() +": "+ taskHours +"h");
                    hoursPerProject = hoursPerProject.add(taskHours);
                }

                System.out.println("=================");
                System.out.println("Liczba godzin dla " + project + ": " + hoursPerProject +"h \n\n");
            } else {
                System.out.println("Brak zadań w projekcie");
            }

        }
    }
}
