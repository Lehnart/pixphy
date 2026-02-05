package com.setoh.pixphy.physics.components;

import com.setoh.pixphy.ecs.Component;

public class Position implements Component {
    public double x;
    public double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Position position)) {
            return false;
        }
        return Double.compare(position.x, x) == 0
            && Double.compare(position.y, y) == 0;
    }

    @Override
    public int hashCode() {
        long bits = Double.doubleToLongBits(x);
        long bits2 = Double.doubleToLongBits(y);
        return (int) (bits ^ (bits >>> 32) ^ bits2 ^ (bits2 >>> 32));
    }
}

