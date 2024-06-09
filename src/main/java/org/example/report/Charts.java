package org.example.report;


import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Charts implements BarChart<CategoryChart> {

    public CategoryChart getChart(HashMap<String, BigDecimal> data) {

        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Zadeklarowane godziny w podziale na projekty").xAxisTitle("Projekty").yAxisTitle("Liczba przepracowanych godzin").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setHasAnnotations(true);

        // Series
        List<String> x = new ArrayList<String>(data.keySet());
        List<BigDecimal> y = new ArrayList<BigDecimal>(data.values());
        chart.addSeries("Raport 1", x,y);

        return chart;
    }
}