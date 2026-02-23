package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.PositionComponent;
import com.setoh.pixphy.physics.component.Vector2D;

final class PositionComponentTest {

    @Test
    void testConstructorAndGetters() {
        PositionComponent comp = new PositionComponent(1.0, 2.0);
        assertEquals(1.0, comp.getX());
        assertEquals(2.0, comp.getY());
        // underlying position
        Vector2D p = comp.getPosition();
        assertEquals(1.0, p.x());
        assertEquals(2.0, p.y());
    }

    @Test
    void testSetters() {
        PositionComponent comp = new PositionComponent(0, 0);
        comp.setX(7.7);
        comp.setY(-4.4);
        assertEquals(7.7, comp.getX());
        assertEquals(-4.4, comp.getY());
    }

    @Test
    void testGetPositionReference() {
        PositionComponent comp = new PositionComponent(1.1, 2.2);
        Vector2D p = comp.getPosition();
        // mutate returned object
        p.setX(9.9);
        p.setY(-8.8);
        // component should reflect same underlying position
        assertEquals(9.9, comp.getX());
        assertEquals(-8.8, comp.getY());
    }

    @Test
    void testEqualsSameInstance() {
        PositionComponent comp = new PositionComponent(1, 1);
        assertSame(comp, comp);
        assertEquals(comp, comp);
    }

    @Test
    void testEqualsSameValues() {
        PositionComponent a = new PositionComponent(3, 3);
        PositionComponent b = new PositionComponent(3, 3);
        assertNotSame(a, b);
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void testEqualsDifferentValues() {
        PositionComponent a = new PositionComponent(3, 3);
        PositionComponent b = new PositionComponent(3, 4);
        PositionComponent c = new PositionComponent(5, 3);
        assertNotEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(b, c);
    }

    @Test
    void testEqualsOtherType() {
        PositionComponent comp = new PositionComponent(1, 2);
        assertNotEquals(comp, "something");
        assertNotEquals(comp, null);
    }
}