package com.setoh.pixphy.plot.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.ParticleStorageComponent;
import com.setoh.pixphy.physics.component.Vector2D;
import com.setoh.pixphy.plot.component.PlotComponent;
import com.setoh.pixphy.plot.component.PlotComponent.Point;

final class PlotComponentTest {

    @Test
    void constructorAndAccessorsExposeConfiguredValues() {
        List<Point> initialPoints = new ArrayList<>(List.of(new Point(1, 2), new Point(3, 4)));
        PlotComponent component = new PlotComponent(
            initialPoints,
            10,
            20,
            42,
            storage -> List.of(new Point(storage.currentSize(), storage.maxSize()))
        );

        assertSame(initialPoints, component.points());
        assertEquals(10, component.x0());
        assertEquals(20, component.y0());
        assertEquals(42, component.entityId());
    }

    @Test
    void setPointsReplacesPointsReference() {
        PlotComponent component = new PlotComponent(new ArrayList<>(), 0, 0, 1, storage -> List.of());
        List<Point> replacement = new ArrayList<>(List.of(new Point(9, 8)));

        component.setPoints(replacement);

        assertSame(replacement, component.points());
        assertEquals(List.of(new Point(9, 8)), component.points());
    }

    @Test
    void mappingReturnsConfiguredFunctionAndCanBeApplied() {
        ParticleStorageComponent storage = new ParticleStorageComponent(3);
        storage.addState(new Vector2D(1.0, 1.0), new Vector2D(0.1, 0.1), new Vector2D(0.01, 0.01));
        storage.addState(new Vector2D(2.0, 2.0), new Vector2D(0.2, 0.2), new Vector2D(0.02, 0.02));

        PlotComponent component = new PlotComponent(
            List.of(),
            0,
            0,
            7,
            psc -> List.of(new Point(psc.currentSize(), psc.maxSize()))
        );

        List<Point> mapped = component.mapping().apply(storage);

        assertEquals(List.of(new Point(2, 3)), mapped);
    }
}
