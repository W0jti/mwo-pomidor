package org.example;

import org.example.Data.ExcelReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ExcelReader excelReader = new ExcelReader();
        excelReader.readFile();

    }
}