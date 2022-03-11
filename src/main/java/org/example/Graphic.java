package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class Graphic extends Application {


    private static int type, problem = 0;

    @FXML
    public LineChart lineChart;

    public static void setData(int type, int problem) {
        Graphic.type = type;
        Graphic.problem = problem;
    }

    public void run() {
        launch();
    }

    @Override
    public void start(Stage stage) {

        lineChart = new LineChart(
            new NumberAxis(-10, 10, 2),
            new NumberAxis(-10, 10, 2)
            );

        Scene scene  = new Scene(lineChart, 900, 600);

        if (Graphic.type == 0) drawFirstType();
        if (Graphic.type == 1) drawSecondType();

        stage.setScene(scene);
        stage.show();

    }


    private void drawFirstType() {
        for (int i = 0; i < Math.EQUATIONS[Graphic.problem].length; i++) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();

            series.setName(Math.GRAPHS[Graphic.problem][i]);

            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);

            for (double point = -10; point <= 10; point += 0.01) {
                series.getData().add(new XYChart.Data<>(point, Math.EQUATIONS[Graphic.problem][i].apply(point)));
            }
        }
    }

    private void drawSecondType() {
        for (int i = 2; i < Math.SYSTEMS[Graphic.problem].length; i++) {
            XYChart.Series<Double, Double> series = new XYChart.Series<>();

            series.setName(Math.GRAPH[Graphic.problem][i-2]);

            lineChart.setCreateSymbols(false);
            lineChart.getData().add(series);

            for (double point = -10; point <= 10; point += 0.01) {
                series.getData().add(new XYChart.Data<>(point, Math.SYSTEMS[Graphic.problem][i].apply(point)));
            }
        }
    }



}
