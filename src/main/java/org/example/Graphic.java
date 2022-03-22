package org.example;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.example.MathStuff.Equation;
import org.example.MathStuff.MathSystem;

public class Graphic extends javafx.application.Application implements Runnable {

    private static Equation equation;
    private static MathSystem mathSystem;

    public void setEquation(Equation equation) {
        Graphic.equation = equation;
    }

    public void setMathSystem(MathSystem mathSystem) {
        Graphic.mathSystem = mathSystem;
    }

    @Override
    public void run() {
        launch();
    }

    @FXML
    public LineChart<Number, Number> lineChart;

    @Override
    public void start(Stage stage) {
        lineChart = new LineChart<>(
            new NumberAxis(-10, 10, 2),
            new NumberAxis(-10, 10, 2)
        );

        Scene scene  = new Scene(lineChart, 900, 600);

        if (equation != null) {
            drawFunction(equation.getFunction());
        } else if (mathSystem != null) {
            drawFunction(mathSystem.getXFunction());
            drawFunction(mathSystem.getYFunction());
        }

        stage.setScene(scene);
        stage.show();
    }

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
