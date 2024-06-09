package org.example.report;


import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// * Basic Bar Chart
//         *
//         * Demonstrates the following:
//         * <ul>
// * <li>Integer categories as List
//        * <li>All positive values
//        * <li>Single series
//        * <li>Place legend at Inside-NW position
//        * <li>Bar Chart Annotations
//        */
public class Charts implements ExampleChart<CategoryChart> {

//    public static void main(String[] args) {
//
////        ExampleChart<CategoryChart> exampleChart = new Charts();
//        CategoryChart chart = exampleChart.getChart();
//        new SwingWrapper<CategoryChart>(chart).displayChart();
//    }

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