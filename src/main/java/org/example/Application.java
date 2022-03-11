package org.example;

import java.util.Arrays;
import java.util.Scanner;

public class Application {
    private final static Scanner scanner = new Scanner(System.in);

    private final EquationSolver solver = new EquationSolver();

    public static void main(String[] args) {
        new Application().run();
        new Graphic().run();
    }

    private void run() {
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
    }

    private double readAccuracy() {
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

    private void nonLinearEquation() {

        System.out.println("Write a or b");
        System.out.println("[a] " + Arrays.toString(Math.EQUATION_A));
        System.out.println("[b] " + Arrays.toString(Math.EQUATION_B);
        System.out.println("[c] " + Arrays.toString(Math.EQUATION_C));
        System.out.println("[d] " + Arrays.toString(Math.EQUATION_D);

        //todo rewrite to while true
        switch (readFromConsole()) {
            case "a":
                solveNonLinearEquation(Math.EQUATION_A);
                break;
            case "b":
                solveNonLinearEquation(1);
                break;
            case "c":
                solveNonLinearEquation(2);
                break;
            case "d":
                solveNonLinearEquation(3);
                break;
            default:
                throw new RuntimeException("You should select a, b, c, d");
        }
    }

    private void solveNonLinearEquation(Function[] functions) {
        setAccuracy();

        System.out.print("Write a = ");
        double a = scanner.nextDouble();
        System.out.print("Write b = ");
        double b = scanner.nextDouble();

        Graphic.setData(0, equations);

        //Метод половинного деления
        System.out.println("Bisection method solution");
        Object[] bisectionResult = solver.solveByBisection(functions[0], a, b);
        System.out.println("x = " + bisectionResult[0]);
        System.out.println("Δx = " + bisectionResult[1]);
        System.out.println("iters = " + bisectionResult[2]);

        //Метод итераций
        System.out.println("Iteration method solution");
        Object[] iterationResult = solver.solveByIteration(functions[1], a, b);
        System.out.println("x = " + iterationResult[0]);
        System.out.println("Δx = " + iterationResult[1]);
        System.out.println("iters = " + iterationResult[2]);

    }

    private void systemOfEquations() {
        System.out.println("[a]" + Math.SYSTEM[0][0] + " :::::: " + Math.SYSTEM[0][1]);
        System.out.println("[b]" + Math.SYSTEM[1][0] + " :::::: " + Math.SYSTEM[1][1]);

        switch (readFromConsole()) {
            case "a": solveSystemOfEquations(0);
                break;
            case "b": solveSystemOfEquations(1);
                break;
            default: throw new RuntimeException();
        }
    }

    private void solveSystemOfEquations(int system) {
        Function[] systems = Math.SYSTEMS[system];

        setAccuracy();

        Graphic.setData(1, system);

        System.out.println("Newton method solution");
        Object[][] newtonResult = solver.solveByNewton(0.5, 0.5, systems[0], systems[1]);
        System.out.println("x = " + newtonResult[0][0]);
        System.out.println("Δx = " + newtonResult[0][1]);
        System.out.println("y = " + newtonResult[1][0]);
        System.out.println("Δy = " + newtonResult[1][1]);
        System.out.println("iters = " + newtonResult[2][0]);

//        graphic.run()
    }

    private String readFromConsole() {
        return scanner.nextLine().trim().toLowerCase();
    }

}
