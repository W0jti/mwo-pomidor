package org.example;

import org.example.model.Person;
import org.example.model.Task;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Report2Generator {
    public void countHoursPerProject(List<Task> tasks) {

        List<Task> filteredTasks = tasks.stream().filter(t -> t.projectName.equals(hoursPerProject)).toList();
        BigDecimal hoursPerProject = new BigDecimal(0);
        System.out.println("===== Imię im Nazwisko " + hoursPerProject + " =====");
        if (!filteredTasks.isEmpty()) {
            for (Task task : filteredTasks) {
                BigDecimal taskHours = task.getHours();
                System.out.println("- " + task.getName() + ": " + taskHours + "h");
                hoursPerProject = hoursPerProject.add(taskHours);
            }
            System.out.println("=================");
            System.out.println("Liczba godzin dla " + projectName + ": " + hoursPerProject + "h \n\n");
        } else {
            System.out.println("Brak zadań w projekcie");
        }
    }


        public void printRaport (List < Task > tasks) {
            List<String> projectNames = tasks.stream().map(Task::getProjectName).collect(Collectors.toList());
            HashMap<String, BigDecimal> projectHours = countProjectHours(tasks, projectNames);

            for (Map.Entry<String, BigDecimal> set : projectHours.entrySet()) {
                System.out.println(set.getKey() + " - " + set.getValue() + "h");
            }
        }
    }

