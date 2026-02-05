package com.setoh.pixphy.physics.component;

import com.setoh.pixphy.ecs.Component;

public class Position implements Component {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.setX(x);
        this.setY(y);
    }

    public double getY() {
        return y;
        
    }

    public void setY(double y) {
        this.y = y;
        
    }

    public double getX() {
        return x;
        
    }

    public void setX(double x) {
        this.x = x;
        
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position position)) {
            return false;
        }
        return Double.compare(position.getX(), getX()) == 0
            && Double.compare(position.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(getX());
        long bits2 = Double.doubleToLongBits(getY());
        return (int) (bits ^ (bits >>> 32) ^ bits2 ^ (bits2 >>> 32));
    }
}

