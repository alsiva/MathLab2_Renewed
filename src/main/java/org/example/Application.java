package org.example;
import org.example.MathStuff.Equation;
import org.example.MathStuff.Math;
import org.example.MathStuff.MathSystem;
import org.example.SolverStuff.EquationSolver;

import java.util.Locale;
import java.util.Scanner;

public class Application {

    private final static Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    private final static Graphic graphic = new Graphic();

    public static void main(String[] args) {
        System.out.println("Nonlinear equation [1]");
        System.out.println("System of nonlinear equation [2]");

        try {
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
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

    }

    private static double readAccuracy() {

        double valueDefault = 0.0001;
        while (true) {
            System.out.println("Set accuracy in interval [0, 1] or leave empty for default value: " + valueDefault);

            try {
                String input = readFromConsole();

                if (input.isBlank()) {
                    return valueDefault;
                }

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
        Equation equation = Math.chooseEquation(index-1);
        solveNonLinearEquation(equation);
    }

    private static void solveNonLinearEquation(Equation equation) {
        double accuracy = readAccuracy();
        EquationSolver solver = new EquationSolver(accuracy);

        graphic.setEquation(equation);
        new Thread(graphic).start();


        System.out.print("Write a = ");
        double a = scanner.nextDouble();

        System.out.print("Write b = ");
        double b = scanner.nextDouble();


        //?????????? ?????????????????????? ??????????????
        System.out.println("Bisection method solution");
        Object[] bisectionResult = solver.solveByBisection(equation.getFunction(), a, b);
        System.out.println("x = " + bisectionResult[0]);
        System.out.println("??x = " + bisectionResult[1]);
        System.out.println("iters = " + bisectionResult[2]);

        System.out.println("--------------------------------------");

        //?????????? ????????????????
        System.out.println("Iteration method solution");
        Object[] iterationResult = solver.renewedIteration(equation.getFunction(), a, b);
        System.out.println("x = " + iterationResult[0]);
        System.out.println("??x = " + iterationResult[1]);
        System.out.println("iters = " + iterationResult[2]);


    }

    private static void systemOfEquations() {
        Math.writeSystemsChoice();
        int index = scanner.nextInt();
        scanner.nextLine();
        MathSystem mathSystem = Math.chooseSystem(index-1);

        solveSystemOfEquations(mathSystem);
    }

    private static void solveSystemOfEquations(MathSystem mathSystem) {
        double accuracy = readAccuracy();
        EquationSolver solver = new EquationSolver(accuracy);

        graphic.setMathSystem(mathSystem);
        new Thread(graphic).start();

        System.out.println("Newton method solution");
        Object[][] newtonResult = solver.solveByNewton(0.5, 0.5, mathSystem.getXFunction() , mathSystem.getYFunction());
        System.out.println("x = " + newtonResult[0][0]);
        System.out.println("??x = " + newtonResult[0][1]);
        System.out.println("y = " + newtonResult[1][0]);
        System.out.println("??y = " + newtonResult[1][1]);
        System.out.println("iters = " + newtonResult[2][0]);
    }

    private static String readFromConsole() {
        return scanner.nextLine().trim().toLowerCase();
    }


}
