package org.example;
import org.example.MathStuff.Equation;
import org.example.MathStuff.Math;
import org.example.MathStuff.MathSystem;
import java.util.Scanner;

public class Application {
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Application().run();
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
        Math.writeEquationsChoice();
        int index = scanner.nextInt();
        //todo handleException or rewrite that way you couldn't choose wrong index
        Equation equation = Math.chooseEquation(index);
        solveNonLinearEquation(equation);
    }

    private void solveNonLinearEquation(Equation equation) {
        double accuracy = readAccuracy();
        EquationSolver solver = new EquationSolver(accuracy);

        System.out.print("Write a = ");
        double a = scanner.nextDouble();
        System.out.print("Write b = ");
        double b = scanner.nextDouble();

        Graphic graphic = new Graphic(equation);

        //Метод половинного деления
        System.out.println("Bisection method solution");
        Object[] bisectionResult = solver.solveByBisection(equation.getFunction(), a, b);
        System.out.println("x = " + bisectionResult[0]);
        System.out.println("Δx = " + bisectionResult[1]);
        System.out.println("iters = " + bisectionResult[2]);

        //Метод итераций
        System.out.println("Iteration method solution");
        Object[] iterationResult = solver.solveByIteration(equation.getPhiFunction(), a, b);
        System.out.println("x = " + iterationResult[0]);
        System.out.println("Δx = " + iterationResult[1]);
        System.out.println("iters = " + iterationResult[2]);

        graphic.run();
    }

    private void systemOfEquations() {

        Math.writeSystemsChoice();
        int index = scanner.nextInt();
        MathSystem mathSystem = Math.chooseSystem(index);
        solveSystemOfEquations(mathSystem);

    }

    private void solveSystemOfEquations(MathSystem mathSystem) {
        double accuracy = readAccuracy();
        EquationSolver solver = new EquationSolver(accuracy);

        Graphic graphic = new Graphic(mathSystem);


        System.out.println("Newton method solution");
        Object[][] newtonResult = solver.solveByNewton(0.5, 0.5, mathSystem.getXFunction() , mathSystem.getYFunction());
        System.out.println("x = " + newtonResult[0][0]);
        System.out.println("Δx = " + newtonResult[0][1]);
        System.out.println("y = " + newtonResult[1][0]);
        System.out.println("Δy = " + newtonResult[1][1]);
        System.out.println("iters = " + newtonResult[2][0]);

        graphic.run();
    }

    private String readFromConsole() {
        return scanner.nextLine().trim().toLowerCase();
    }

}
