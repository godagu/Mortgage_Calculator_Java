package gui;

import javafx.collections.ObservableList;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import logic.PaymentRow;

public class PaymentChart extends VBox {
    private StackedAreaChart<Number, Number> paymentChart;

    public PaymentChart(ObservableList<PaymentRow> paymentRows, int startMonth, int endMonth) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Month");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Payment Amount");

        paymentChart = new StackedAreaChart<>(xAxis, yAxis);
        paymentChart.setTitle("Principal and interest over time");

        XYChart.Series<Number, Number> principalSeries = new XYChart.Series<>();
        principalSeries.setName("Principal");

        XYChart.Series<Number, Number> interestSeries = new XYChart.Series<>();
        interestSeries.setName("Interest");

        for(int month = startMonth; month < endMonth; ++month) {
            double principal = paymentRows.get(month - 1).getPrincipal();
            double interest = paymentRows.get(month - 1).getInterest();

            principalSeries.getData().add(new XYChart.Data<>(month, principal));
            interestSeries.getData().add(new XYChart.Data<>(month, interest));
        }

        paymentChart.getData().addAll(principalSeries, interestSeries);
        this.getChildren().add(paymentChart);

    }
}
