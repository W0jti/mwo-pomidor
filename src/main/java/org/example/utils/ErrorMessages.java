package org.example.utils;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessages {
    private static ErrorMessages instance;
    public  List<String> errorMessages;

    private ErrorMessages() {
        errorMessages = new ArrayList<>();
    }

    public static ErrorMessages getInstance() {
        if(instance == null) {
            instance = new ErrorMessages();
        }

        return instance;
    }

    public void addErrorMessage(String message){
        this.errorMessages.add(message);
    }

    public void show() {
        System.out.println("\n\n");
        System.out.println("Report generated with " +  errorMessages.size() + " error(s):");
        if (errorMessages.size() > 0) {
            for (String message: errorMessages) {
                System.out.println(message );
            }
        }
    }
}
