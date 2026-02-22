package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.VelocityComponent;

final class VelocityTest {
    @Test
    void testConstructorAndFields() {
        VelocityComponent velocity = new VelocityComponent(0.5, -1.75);
        assertEquals(0.5, velocity.getDx());
        assertEquals(-1.75, velocity.getDy());
    }

    @Test
    void testEqualsSameInstance() {
        VelocityComponent velocity = new VelocityComponent(1.0, 2.0);
        assertSame(velocity, velocity);
        assertEquals(velocity, velocity);
    }

    @Test
    void testEqualsSameValues() {
        VelocityComponent a = new VelocityComponent(3.0, 4.0);
        VelocityComponent b = new VelocityComponent(3.0, 4.0);
        assertNotSame(a, b);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testEqualsDifferentValues() {
        VelocityComponent a = new VelocityComponent(3.0, 4.0);
        VelocityComponent b = new VelocityComponent(3.0, 5.0);
        VelocityComponent c = new VelocityComponent(6.0, 4.0);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, c);
    }

    @Test
    void testEqualsOtherType() {
        VelocityComponent velocity = new VelocityComponent(1.0, 2.0);
        assertNotEquals("Velocity(1,2)", velocity);
        assertNotEquals(velocity, null);
    }
}
