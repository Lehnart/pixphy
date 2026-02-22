package com.setoh.pixphy.physics.component;

import com.setoh.pixphy.ecs.Component;

public class AccelerationComponent implements Component {
    private double ddx;
    private double ddy;

    public AccelerationComponent(double ddx, double ddy) {
        this.setDDx(ddx);
        this.setDDy(ddy);
    }

    public double getDDy() {
        return ddy;
        
    }

    public void setDDy(double ddy) {
        this.ddy = ddy;
        
    }

    public double getDDx() {
        return ddx;
        
    }

    public void setDDx(double ddx) {
        this.ddx = ddx;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AccelerationComponent acceleration)) {
            return false;
        }
        return Double.compare(acceleration.getDDx(), getDDx()) == 0
            && Double.compare(acceleration.getDDy(), getDDy()) == 0;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(getDDx());
        long bits2 = Double.doubleToLongBits(getDDy());
        return (int) (bits ^ (bits >>> 32) ^ bits2 ^ (bits2 >>> 32));
    }
}