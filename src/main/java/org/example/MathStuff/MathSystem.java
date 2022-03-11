package org.example.MathStuff;

import org.example.Function;

public class MathSystem {

    private Function xFunction;
    private Function yFunction;
    private Function xFunctionRounded;
    private Function yFunctionRounded;

    public MathSystem(String xFunction, String yFunction, String xFunctionRounded, String yFunctionRounded) {
        this.xFunction = new Function(xFunction);
        this.yFunction = new Function(yFunction);
        this.xFunctionRounded = new Function(xFunctionRounded);
        this.xFunctionRounded = new Function(xFunctionRounded);
    }

    public Function getXFunction() {
        return this.xFunction;
    }

    public Function getYFunction() {
        return this.yFunction;
    }

    public Function getXFunctionRounded() {
        return this.xFunctionRounded;
    }

    public Function getYFunctionRounded() {
        return this.yFunctionRounded;
    }

    public String getXFunctionAsString() {
        return this.xFunction.toString();
    }

    public String getYFunctionAsString() {
        return this.yFunction.toString();
    }

}
