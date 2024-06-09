package org.example.filter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilterQuery {

    public Date fromDate;
    public Date toDate;
    public String employee;


    public FilterQuery(String fromDate, String toDate, String employee) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (fromDate != null){
            this.fromDate = sdf.parse(fromDate);
        }

        if (toDate != null) {
            this.toDate = sdf.parse(toDate);
        }

        if (employee != null) {
            this.employee = employee;
        }

    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public String getEmployee() {
        return employee;
    }
}
