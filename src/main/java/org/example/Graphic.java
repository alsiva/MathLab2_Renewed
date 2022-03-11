package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.example.MathStuff.Equation;
import org.example.MathStuff.Math;
import org.example.MathStuff.MathSystem;

public class Graphic extends Application {

    @FXML
    public LineChart lineChart;

    private boolean type;
    private Equation equation;
    private MathSystem mathSystem;

    public void run() {
        launch();
    }




    public Graphic(Equation equation) {
        this.equation = equation;
    }
    public Graphic(MathSystem mathSystem) {
        this.mathSystem = mathSystem;
    }

    @Override
    public void start(Stage stage) {

        lineChart = new LineChart(
            new NumberAxis(-10, 10, 2),
            new NumberAxis(-10, 10, 2)
            );

        Scene scene  = new Scene(lineChart, 900, 600);

        if (!this.type) {
            drawEquation(equation);
        } else {
            drawNonLinearSystems(mathSystem);
        }


        stage.setScene(scene);
        stage.show();

    }


    private void drawEquation(Equation equation) {
        drawFunction(equation.getFunction());
        drawFunction(equation.getPhiFunction());
        drawFunction(equation.getX());
    }

    private void drawFunction(Function function) {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        series.setName("y=" + function.toString());
        for (double point = -10; point <= 10; point += 0.01) {
            series.getData().add(new XYChart.Data<>(point, equation.getFunction().apply(point)));
        }
        lineChart.setCreateSymbols(false);
        lineChart.getData().add(series);
    }


    private void drawNonLinearSystems(MathSystem mathSystem) {
        drawFunction(mathSystem.getXFunctionRounded());
        drawFunction(mathSystem.getYFunctionRounded());
    }

}
