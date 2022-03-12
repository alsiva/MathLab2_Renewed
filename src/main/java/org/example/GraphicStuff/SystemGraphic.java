package org.example.GraphicStuff;

import org.example.MathStuff.MathSystem;

public class SystemGraphic extends Graphic{

    private final MathSystem mathSystem;

    public SystemGraphic(MathSystem mathSystem) {
        super();
        this.mathSystem = mathSystem;
    }

    @Override
    void draw() {
        drawFunction(mathSystem.getXFunctionRounded());
        drawFunction(mathSystem.getYFunctionRounded());
    }
}
