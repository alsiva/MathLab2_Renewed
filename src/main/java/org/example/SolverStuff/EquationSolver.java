package org.example.SolverStuff;
import org.example.Function;

import java.lang.Math;

public class EquationSolver {
    private final double accuracy;

    public EquationSolver(double accuracy) {
        this.accuracy = accuracy;
    }

    private final int limit = 1000000; //1.000.000

    private double deltaX(Function[] func, double x, double y) {
        return func[0].apply(x, y) * func[1].derivativeByY(x, y, 1e-9) - func[1].apply(x, y) * func[0].derivativeByY(x, y, 1e-9);
    }

    private double deltaY(Function[] func, double x, double y) {
        return func[0].derivativeByX(x, y, 1e-9) * func[1].apply(x, y) - func[1].derivativeByX(x, y, 1e-9) * func[0].apply(x, y);
    }

    private double jacobian(Function[] func, double x, double y) {
        return func[0].derivativeByX(x, y, 1e-9) * func[1].derivativeByY(x, y, 1e-9) - func[1].derivativeByX(x, y, 1e-9) * func[0].derivativeByY(x, y, 1e-9);
    }

    public Object[][] solveByNewton(double x, double y, Function... functions) {

        if (functions.length != 2) {
            throw new IllegalArgumentException("Method is intended only for solving systems with two unknowns");
        }

        double delta = 1;
        double prevX = 0, prevY = 0;
        int iterations = 0;
        while (delta > accuracy && iterations < limit) {
            prevX = x; prevY = y;
            x = prevX - deltaX(functions, prevX, prevY) / jacobian(functions, prevX, prevY);
            y = prevY - deltaY(functions, prevX, prevY) / jacobian(functions, prevX, prevY);
            delta = Math.max(Math.abs(x - prevX), Math.abs(y - prevY));
            iterations++;
        }
        if (Double.isNaN(x) || Double.isNaN(y) || iterations == limit) {
            throw new RuntimeException("Couldn't achieve specified accuracy");
        }
        return new Object[][] {{x, x - prevX}, {y, y - prevY}, {iterations}};
    }

    public Object[] iterationRenewed(Function function, double a, double b) {

        if (a > b) {
            double change = a;
            a = b;
            b = change;
        }

        System.out.println("---Phi functions");
        System.out.println("Phi(a) = " + function.apply(a));
        System.out.println("Phi(b) = " + function.apply(b));
        System.out.println("----------------");
        double q = derivativeSeriesMax(function, a, b);

        if (function.apply(a) * function.apply(b) > 0) {
            throw new RuntimeException("Condition f(a) * f(b) < 0 is not satisfied");
        }

        double lambda =  -1/q;
        double x;
        double prevX;
        double iterations = 0;
        double delta = 0;



        if (function.derivative(a, 1e-9) > function.derivative(b, 1e-9)) {
            x = a;
        } else {
            x = b;
        }


        while (Math.abs(function.apply(x)) > accuracy) {
            prevX = x;
            iterations += 1;
            x += lambda * function.apply(x);
            delta = x > prevX ? x - prevX : prevX - x;
        }

        return new Object[] { x, delta, iterations };

    }

    private double derivativeSeriesMax(Function function, double a, double b) {
        double max = 0, delta = (b - a) / 100000;

        if (a == b) return Math.abs(function.derivative(0, 1e-9));

        for (double point = a; point <= b; point += delta) {
            max = Math.max(max, Math.abs(function.derivative(point, 1e-9)));
        }
        return max;
    }

    public Object[] solveByBisection(Function function, double a, double b) {
        if (a > b) {
            double change = a;
            a = b;
            b = change;
        }

        if (function.apply(a) * function.apply(b) > 0) {
            throw new RuntimeException("Condition f(a) * f(b) < 0 is not satisfied");
        }

        int iterations = 0;
        double root = 0;
        while (b - a > 2 * accuracy && iterations < limit) {
            root = (a + b) / 2;
            if (function.apply(a) * function.apply(root) > 0) a = root; else b = root;
            iterations++;
        }
        double delta = (b - a) / 2;
        return new Object[] { root, delta, iterations };
    }
}
