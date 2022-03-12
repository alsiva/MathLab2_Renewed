package org.example.GraphicStuff;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.example.Function;
import org.example.MathStuff.Equation;
import org.example.MathStuff.Math;
import org.example.MathStuff.MathSystem;

public abstract class Graphic extends Application {

    @FXML
    public LineChart<Number, Number> lineChart;

    public void run() {
        launch();
    }

    @Override
    public void start(Stage stage) {

        lineChart = new LineChart<Number, Number>(
            new NumberAxis(-10, 10, 2),
            new NumberAxis(-10, 10, 2)
        );

        Scene scene  = new Scene(lineChart, 900, 600);

        draw();

        stage.setScene(scene);
        stage.show();

    }

    abstract void draw();

    protected void drawFunction(Function function) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("y=" + function.toString());
        for (double point = -10; point <= 10; point += 0.01) {
            series.getData().add(new XYChart.Data<Number, Number>(point, function.apply(point)));
        }
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(series);
    }

}
