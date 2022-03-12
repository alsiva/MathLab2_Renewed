package org.example;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.example.MathStuff.Equation;
import org.example.MathStuff.Math;
import org.example.MathStuff.MathSystem;

import java.util.Locale;
import java.util.Scanner;

public class Application extends javafx.application.Application {
    private static Equation equation;
    private static MathSystem mathSystem;

    private final static Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        System.out.println("Nonlinear equation [1]");
        System.out.println("System of nonlinear equation [2]");

        switch (readFromConsole()) {
            case "1":
                nonLinearEquation();
                break;
            case "2":
                systemOfEquations();
                break;
            default:
                throw new RuntimeException("You should choose either 1 or 2");
        }

        launch(args);
    }

    private static double readAccuracy() {
        while (true) {
            System.out.println("Set accuracy in interval [0, 1]");

            try {
                String input = readFromConsole();
                double accuracy = Double.parseDouble(input);

                if (0 <= accuracy && accuracy <= 1) {
                    return accuracy;
                }
            } catch (NumberFormatException e) {
                System.err.println("Wrong format");
            } catch (NullPointerException npe) {
                System.err.println("Empty input");
            }
        }
    }

    private static void nonLinearEquation() {
        Math.writeEquationsChoice();
        int index = scanner.nextInt();
        scanner.nextLine();
        //todo handleException or rewrite that way you couldn't choose wrong index
        Equation equation = Math.chooseEquation(index-1);
        solveNonLinearEquation(equation);
    }

    private static void solveNonLinearEquation(Equation equation) {
        double accuracy = readAccuracy();
        EquationSolver solver = new EquationSolver(accuracy);

        System.out.print("Write a = ");
        double a = scanner.nextDouble();

        System.out.print("Write b = ");
        double b = scanner.nextDouble();


        //Метод половинного деления
        System.out.println("Bisection method solution");
        Object[] bisectionResult = solver.solveByBisection(equation.getFunction(), a, b);
        System.out.println("x = " + bisectionResult[0]);
        System.out.println("Δx = " + bisectionResult[1]);
        System.out.println("iters = " + bisectionResult[2]);

        System.out.println("--------------------------------------");

        //Метод итераций
        System.out.println("Iteration method solution");
        Object[] iterationResult = solver.solveByIteration(equation.getPhiFunction(), a, b);
        System.out.println("x = " + iterationResult[0]);
        System.out.println("Δx = " + iterationResult[1]);
        System.out.println("iters = " + iterationResult[2]);

        Application.equation = equation;
    }

    private static void systemOfEquations() {
        Math.writeSystemsChoice();
        int index = scanner.nextInt();
        MathSystem mathSystem = Math.chooseSystem(index-1);
        solveSystemOfEquations(mathSystem);
    }

    private static void solveSystemOfEquations(MathSystem mathSystem) {
        double accuracy = readAccuracy();
        EquationSolver solver = new EquationSolver(accuracy);

        Application.mathSystem = mathSystem;

        System.out.println("Newton method solution");
        Object[][] newtonResult = solver.solveByNewton(0.5, 0.5, mathSystem.getXFunction() , mathSystem.getYFunction());
        System.out.println("x = " + newtonResult[0][0]);
        System.out.println("Δx = " + newtonResult[0][1]);
        System.out.println("y = " + newtonResult[1][0]);
        System.out.println("Δy = " + newtonResult[1][1]);
        System.out.println("iters = " + newtonResult[2][0]);
    }

    private static String readFromConsole() {
        return scanner.nextLine().trim().toLowerCase();
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
            drawFunction(equation.getPhiFunction());
            drawFunction(equation.getX());
        } else {
            drawFunction(mathSystem.getXFunctionRounded());
            drawFunction(mathSystem.getYFunctionRounded());
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
