package org.example.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Task {
    public String name;
    public Date date;
    public BigDecimal hours;
    public String projectName;
    public String employee;

    public Task(String employee, String name, Date date, BigDecimal hours, String projectName) {
        this.employee = employee;
        this.name = name;
        this.date = date;
        this.hours = hours;
        this.projectName = projectName;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public String getProjectName() {
        return projectName;
    }
    public String getEmployee(){return employee;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(date, task.date) &&
                Objects.equals(hours, task.hours) &&
                Objects.equals(projectName, task.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, date, hours, projectName);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", hours=" + hours +
                ", employee=" + employee +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
