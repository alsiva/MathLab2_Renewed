package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class Function {

    private final Expression expression;
    private final String function;

    public Function(String function) {
        this.function = function;
        this.expression = new ExpressionBuilder(function)
            .variables("x", "y")
            .build();
    }

    @Override
    public String toString() {
        return this.function;
    }

    //Solve knowing x
    public double apply(double x) {
        return expression.setVariable("x", x).evaluate();
    }

    //Solve knowing x and y
    public double apply(double x, double y) {
        return expression.setVariable("x", x).setVariable("y", y).evaluate();
    }

    //Find y'(derivative)
    public double derivative(double x) {
        double delta = 1e-9;
        return (this.apply(x + delta) - this.apply(x - delta)) / (2 * delta);
    }

    //Find derivative by x (y'x)
    public double derivativeByX(double x, double y, double delta) {
        return (this.apply(x + delta, y) - this.apply(x - delta, y)) / (2 * delta);
    }

    //Find derivative by y
    public double derivativeByY(double x, double y, double delta) {
        return (this.apply(x, y + delta) - this.apply(x, y - delta)) / (2 * delta);
    }


}
