package org.example.MathStuff;
import java.util.Arrays;
import java.util.List;


public class Math {
    private static final List<Equation> EQUATIONS = Arrays.asList(
        new Equation("sin(x)^2-x^2+1", "(sin(x)^2+1)^0.5", "x"),
        new Equation("x^2-e^x-3x+2", "log(x^2-3x+2)", "x"),
        new Equation("xe^{x^2}-sin(x)^2+3cos(x)+5", "(sin(x)^2-3cos(x)-5)/e^{x^2}", "x"),
        new Equation("x^3-17", "17^(1/3)", "x")
    );

    private static final List<MathSystem> SYSTEMS = Arrays.asList(
        new MathSystem("0.1x^2+x+0.2y^2-0.3", "0.2x^2+y-0.1xy-0.7", "((0.3-x-0.1x^2)/0.2)^0.5", "(0.2x^2-0.7)/(0.1x-1)"),
        new MathSystem("sin(2x-y)-1.2x-0.4", "0.8x^2+1.5y^2-1", "2x-asin(1.2x+0.4)", "((1-0.8x^2)/1.5)^0.5")
    );

    public static void writeEquationsChoice() {
        System.out.println("Choose the equation from 1 to " + EQUATIONS.size());

        for (int i = 0; i < EQUATIONS.size(); i++) {
            System.out.println("[" + (i+1) + "]: " + EQUATIONS.get(i).getFunctionAsString());
        }
    }

    public static Equation chooseEquation(int index) {
        return EQUATIONS.get(index);
    }

    public static void writeSystemsChoice() {
        System.out.println("Choose the system from 1 to " + SYSTEMS.size());
        for (int i = 0; i < SYSTEMS.size(); i++) {
            System.out.println("[" + (i+1) + "]: " + SYSTEMS.get(i).getXFunctionAsString() + " :::::: " + SYSTEMS.get(i).getYFunctionAsString());
        }
    }

    public static MathSystem chooseSystem(int index) {
        return SYSTEMS.get(index);
    }
}
