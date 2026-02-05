package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

final class PositionTest {
    
    @Test
    void testConstructorAndFields() {
        Position position = new Position(1.5, -2.25);
        assertEquals(1.5, position.x);
        assertEquals(-2.25, position.y);
    }

    @Test
    void testEqualsSameInstance() {
        Position position = new Position(1.0, 2.0);
        assertSame(position, position);
        assertEquals(position, position);
    }

    @Test
    void testEqualsSameValues() {
        Position a = new Position(3.0, 4.0);
        Position b = new Position(3.0, 4.0);
        assertNotSame(a, b);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testEqualsDifferentValues() {
        Position a = new Position(3.0, 4.0);
        Position b = new Position(3.0, 5.0);
        Position c = new Position(6.0, 4.0);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
    }

    @Test
    void testEqualsOtherType() {
        Position position = new Position(1.0, 2.0);
        assertNotEquals(position, "Position(1,2)");
        assertNotEquals(position, null);
    }
}
