package org.burlingame.ftc.lib.OI;

import java.util.function.DoubleSupplier;

public class Stick {

    private DoubleSupplier sourceX, sourceY;

    public Stick(DoubleSupplier x, DoubleSupplier y) {
        this.sourceX = x;
        this.sourceY = y;
    }

    public double getX() {
        return sourceX.getAsDouble();
    }

    public double getY() {
        return sourceY.getAsDouble();
    }

}
