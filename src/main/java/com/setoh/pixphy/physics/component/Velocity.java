package com.setoh.pixphy.physics.component;

import com.setoh.pixphy.ecs.Component;

public class Velocity implements Component {
    private double dx;
    private double dy;

    public Velocity(double dx, double dy) {
        this.setDx(dx);
        this.setDy(dy);
    }

    public double getDy() {
        return dy;
        
    }

    public void setDy(double dy) {
        this.dy = dy;
        
    }

    public double getDx() {
        return dx;
        
    }

    public void setDx(double dx) {
        this.dx = dx;
        
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Velocity velocity)) {
            return false;
        }
        return Double.compare(velocity.getDx(), getDx()) == 0
            && Double.compare(velocity.getDy(), getDy()) == 0;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(getDx());
        long bits2 = Double.doubleToLongBits(getDy());
        return (int) (bits ^ (bits >>> 32) ^ bits2 ^ (bits2 >>> 32));
    }
}