package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.Vector2D;

final class PositionTest {

    @Test
    void testConstructorAndAccessors() {
        Vector2D position = new Vector2D(1.5, -2.25);
        assertEquals(1.5, position.x());
        assertEquals(-2.25, position.y());
    }

    @Test
    void testSetters() {
        Vector2D position = new Vector2D(0, 0);
        position.setX(4.2);
        position.setY(-3.8);
        assertEquals(4.2, position.x());
        assertEquals(-3.8, position.y());
    }

    @Test
    void testEqualsSameInstance() {
        Vector2D pos = new Vector2D(2.0, 3.0);
        assertSame(pos, pos);
        assertEquals(pos, pos);
    }

    @Test
    void testEqualsSameValues() {
        Vector2D a = new Vector2D(5.0, 6.0);
        Vector2D b = new Vector2D(5.0, 6.0);
        assertNotSame(a, b);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testEqualsDifferentValues() {
        Vector2D a = new Vector2D(5.0, 6.0);
        Vector2D b = new Vector2D(5.0, 6.1);
        Vector2D c = new Vector2D(7.0, 6.0);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, c);
    }

    @Test
    void testEqualsOtherType() {
        Vector2D pos = new Vector2D(1.0, 2.0);
        assertNotEquals(pos, "Position(1,2)");
        assertNotEquals(pos, null);
    }
}
