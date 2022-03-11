package org.example;
import java.lang.Math;

public class EquationSolver {

    private double accuracy = 1E-5;
    private final int limit = 1000000; //1.000.000

    public void setAccuracy(double accuracy) {
        if (!(0 < accuracy && accuracy < 1)) {
            throw new IllegalArgumentException("Accuracy should be in [0, 1]");
        }
    }

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

    public Object[] solveByIteration(Function function, double a, double b) {
        if (a > b) {
            double change = a;
            a = b;
            b = change;
        }

        double q = derivativeSeriesMax(function, a, b);
        if (Double.isNaN(q) || q >= 1) {
            throw new RuntimeException("Necessary condition for the convergence is not satisfied");
        }

        int iterations = 0;
        double delta;
        double k = (1 - q) / q;
        double prev, root = (a + b) / 2;

        do {
            prev = root; root = function.apply(root);
            delta = root > prev ? root - prev : prev - root;
            iterations++;
        } while (delta > k * accuracy && iterations < limit);

        if (iterations == limit) {
            throw new RuntimeException("Specified accuracy is not achieved");
        }

        return new Object[] { root, delta / k, iterations };
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
