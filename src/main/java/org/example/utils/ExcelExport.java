package org.example.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class ExcelExport {
    private final String fileName;
    private final String[] headerss;
    private final Workbook workbook;
    private final Sheet sheet;
    private int rowsCount = 1;
    private Row lastRow;

    public ExcelExport(
            String fileName,
            String[] headers
    ) {
        this.fileName = fileName;
        this.headerss = headers;

        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Tasks");

        // Nagłówki kolumn
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headerss.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headerss[i]);
            sheet.setColumnWidth(i, 6000);
        }
    }

    public void addRow() {
        lastRow = sheet.createRow(rowsCount);
        rowsCount++;
    }

    public void addCell(int columnIndex, String value) {
        Cell cell = lastRow.createCell(columnIndex);
        cell.setCellValue(value);
    }

    public void addCell(int columnIndex, BigDecimal value) {
        Cell cell = lastRow.createCell(columnIndex);
        cell.setCellValue(value.doubleValue());
    }

    public void saveToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Wyexportowano raport do pliku: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd exportu pliku: " + fileName);
        }
    }
}
