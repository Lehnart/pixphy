package com.setoh.pixphy.physics.components;

import com.setoh.pixphy.ecs.Component;

public class Velocity implements Component {
    public double dx;
    public double dy;

    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Velocity velocity)) {
            return false;
        }
        return Double.compare(velocity.dx, dx) == 0
            && Double.compare(velocity.dy, dy) == 0;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(dx);
        long bits2 = Double.doubleToLongBits(dy);
        return (int) (bits ^ (bits >>> 32) ^ bits2 ^ (bits2 >>> 32));
    }
}