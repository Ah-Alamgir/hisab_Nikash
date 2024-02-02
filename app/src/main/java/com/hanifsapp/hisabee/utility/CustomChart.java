package com.hanifsapp.hisabee.utility;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.SingleValueDataSet;
import com.anychart.charts.CircularGauge;
import com.anychart.core.axes.Circular;
import com.anychart.core.gauge.pointers.Bar;
import com.anychart.enums.Anchor;
import com.anychart.graphics.vector.Fill;
import com.anychart.graphics.vector.SolidFill;
import com.anychart.graphics.vector.text.HAlign;
import com.anychart.graphics.vector.text.VAlign;

import java.util.Arrays;

public class CustomChart {
    public static CircularGauge showChart(String[] cost, String[] category) {

        double maxCost = Arrays.stream(cost).mapToDouble(Double::parseDouble).max().getAsDouble();
        String[] percentageCost = Arrays.stream(cost).map(c -> String.valueOf(Double.parseDouble(c) / maxCost * 100)).toArray(String[]::new);

        CircularGauge circularGauge = AnyChart.circular();
        circularGauge.data(new SingleValueDataSet(percentageCost));
        circularGauge.fill("#fff")
                .stroke(null)
                .padding(0d, 0d, 0d, 0d)
                .margin(100d, 100d, 100d, 100d);
        circularGauge.startAngle(0d);
        circularGauge.sweepAngle(270d);

        Circular xAxis = circularGauge.axis(0)
                .radius(100d)
                .width(1d)
                .fill((Fill) null);
        xAxis.scale()
                .minimum(0d)
                .maximum(100d);
        xAxis.ticks("{ interval: 1 }")
                .minorTicks("{ interval: 1 }");
        xAxis.labels().enabled(false);
        xAxis.ticks().enabled(false);
        xAxis.minorTicks().enabled(false);

        String[] colors = {"#64b5f6", "#1976d2", "#ef6c00", "#64b5f6"};
        for (int i = 0; i < category.length; i++) {
            double radius = 100d - i * 20d;
            circularGauge.label((double) i)
                    .text(category[i] + ", <span style=\"\">" + cost[i] + "</span>")
                    .useHtml(true)
                    .hAlign(HAlign.CENTER)
                    .vAlign(VAlign.MIDDLE);
            circularGauge.label((double) i)
                    .anchor(Anchor.RIGHT_CENTER)
                    .padding(0d, 10d, 0d, 0d)
                    .height(17d / 2d + "%")
                    .offsetY(radius + "%")
                    .offsetX(0d);
            Bar bar = circularGauge.bar((double) i);
            bar.dataIndex((double) i);
            bar.radius(radius);
            bar.width(17d);
            bar.fill(new SolidFill(colors[i], 1d));
            bar.stroke(null);
            bar.zIndex(5d);
            Bar barBg = circularGauge.bar(100d + i);
            barBg.dataIndex(5d);
            barBg.radius(radius);
            barBg.width(17d);
            barBg.fill(new SolidFill("#F5F4F4", 1d));
            barBg.stroke("1 #e5e4e4");
            barBg.zIndex(4d);
        }

        circularGauge.margin(50d, 50d, 50d, 50d);

        return circularGauge;
    }
}
