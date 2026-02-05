package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

final class VelocityTest {
    @Test
    void testConstructorAndFields() {
        Velocity velocity = new Velocity(0.5, -1.75);
        assertEquals(0.5, velocity.getDx());
        assertEquals(-1.75, velocity.getDy());
    }

    @Test
    void testEqualsSameInstance() {
        Velocity velocity = new Velocity(1.0, 2.0);
        assertSame(velocity, velocity);
        assertEquals(velocity, velocity);
    }

    @Test
    void testEqualsSameValues() {
        Velocity a = new Velocity(3.0, 4.0);
        Velocity b = new Velocity(3.0, 4.0);
        assertNotSame(a, b);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testEqualsDifferentValues() {
        Velocity a = new Velocity(3.0, 4.0);
        Velocity b = new Velocity(3.0, 5.0);
        Velocity c = new Velocity(6.0, 4.0);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
    }

    @Test
    void testEqualsOtherType() {
        Velocity velocity = new Velocity(1.0, 2.0);
        assertNotEquals("Velocity(1,2)", velocity);
        assertNotEquals(velocity, null);
    }
}
