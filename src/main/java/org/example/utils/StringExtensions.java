package org.example.utils;

public class StringExtensions {
    public static String getEmployeeNameFromFilepath(String filename){
        return filename.split("\\.")[0].replace("_", " ");
    }
}
