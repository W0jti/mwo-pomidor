package org.example.Data;

import org.example.filter.FilterQuery;
import org.example.model.Task;

import java.io.IOException;
import java.util.List;

public interface IExcelReader {
    public List<String> readProjectNames() throws IOException;
    public List<Task> readProjectTasks(String projectName, FilterQuery filterQuery) throws IOException;
}
