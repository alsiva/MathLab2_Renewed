package org.example.MathStuff;

import org.example.Function;

public class Equation {

    private final Function function;
    private final Function phiFunction;
    private final Function x;

    public Equation(String function, String phiFunction, String x) {
        this.function = new Function(function);
        this.phiFunction = new Function(phiFunction);
        this.x = new Function(x);
    }

    public String getFunctionAsString() {
        return function.toString();
    }

    public Function getFunction() {
        return this.function;
    }

    public Function getPhiFunction() {
        return this.phiFunction;
    }

    public Function getX() { return this.x; }

}
