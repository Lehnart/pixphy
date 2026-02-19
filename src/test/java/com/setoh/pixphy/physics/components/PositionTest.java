package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.PositionComponent;

final class PositionTest {

    @Test
    void testConstructorAndFields() {
        PositionComponent position = new PositionComponent(1.5, -2.25);
        assertEquals(1.5, position.getX());
        assertEquals(-2.25, position.getY());
    }
}
