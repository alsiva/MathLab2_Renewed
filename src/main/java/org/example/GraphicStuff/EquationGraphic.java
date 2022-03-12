package org.example.GraphicStuff;

import org.example.MathStuff.Equation;

public class EquationGraphic extends Graphic {

    private final Equation equation;

    public EquationGraphic(Equation equation) {
        super();
        this.equation = equation;
    }

    @Override
    void draw() {
        drawFunction(equation.getFunction());
        drawFunction(equation.getPhiFunction());
        drawFunction(equation.getX());
    }
}
