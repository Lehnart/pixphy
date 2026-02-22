package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.TemporalPositionComponent;
import com.setoh.pixphy.physics.component.Position;

import java.util.function.Function;

final class TemporalPositionComponentTest {

    @Test
    void testTemporalFunctionIsInvoked() {
        // simple linear function: x = t, y = t * 2
        Function<Double, Position> func = t -> new Position(t, t * 2);
        TemporalPositionComponent temporal = new TemporalPositionComponent(func);

        // check for some sample times
        Position p0 = temporal.getPosition(0.0);
        assertNotNull(p0);
        assertEquals(0.0, p0.x());
        assertEquals(0.0, p0.y());

        Position p1 = temporal.getPosition(1.0);
        assertEquals(1.0, p1.x());
        assertEquals(2.0, p1.y());

        Position p2 = temporal.getPosition(2.5);
        assertEquals(2.5, p2.x());
        assertEquals(5.0, p2.y());
    }

    @Test
    void testTemporalFunctionNullSafety() {
        // if someone passes a function that returns null, the component should propagate it
        TemporalPositionComponent temporal = new TemporalPositionComponent(t -> null);
        Position result = temporal.getPosition(123.456);
        assertEquals(null, result);
    }
}