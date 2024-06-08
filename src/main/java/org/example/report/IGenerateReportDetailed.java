package org.example.report;

import org.example.model.Task;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface IGenerateReportDetailed extends IGenerateReport {
    public HashMap<String, HashMap<String, BigDecimal>> getDetailedReportData(List<Task> tasks);
}
