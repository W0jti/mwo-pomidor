package org.example.report;

import java.math.BigDecimal;
import java.util.HashMap;

public interface ExampleChart<T> {
    T getChart(HashMap<String, BigDecimal> data);
}
