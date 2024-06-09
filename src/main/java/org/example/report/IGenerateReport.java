package org.example.report;

import org.example.model.Task;
import org.example.utils.ExcelExport;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface IGenerateReport {
    public HashMap<String, BigDecimal> getReportData(List<Task> tasks);

    public void writeXls(ExcelExport excelExport, List<Task> tasks, boolean isDetailed);
    public HashMap<String, HashMap<String, BigDecimal>> getDetailedReportData(List<Task> tasks);
}
