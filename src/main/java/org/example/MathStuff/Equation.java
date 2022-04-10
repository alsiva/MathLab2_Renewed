package org.example.MathStuff;

import org.example.Function;

public class Equation {

    private final Function function;


    public Equation(String function) {
        this.function = new Function(function);
    }

    public String getFunctionAsString() {
        return function.toString();
    }

    public Function getFunction() {
        return this.function;
    }



}
