package com.setoh.pixphy.plot.system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.ParticleStorageComponent;
import com.setoh.pixphy.physics.component.Vector2D;
import com.setoh.pixphy.plot.component.PlotComponent;
import com.setoh.pixphy.plot.component.PlotComponent.Point;

final class PlotUpdaterSystemTest {

    @Test
    void updateRecomputesPointsFromMappedParticleStorage() {
        World world = new World();

        Entity sourceEntity = world.createEntity();
        ParticleStorageComponent storage = new ParticleStorageComponent(5);
        storage.addState(new Vector2D(1.0, 1.0), new Vector2D(0.1, 0.1), new Vector2D(0.01, 0.01));
        storage.addState(new Vector2D(2.0, 2.0), new Vector2D(0.2, 0.2), new Vector2D(0.02, 0.02));
        world.addComponent(sourceEntity, storage);

        Entity plotEntity = world.createEntity();
        List<Point> initialPoints = new ArrayList<>(List.of(new Point(99, 99)));
        PlotComponent plot = new PlotComponent(
            initialPoints,
            0,
            0,
            sourceEntity.id(),
            psc -> List.of(new Point(psc.currentSize(), psc.maxSize()))
        );
        world.addComponent(plotEntity, plot);

        new PlotUpdaterSystem().update(world, 0.016);

        assertEquals(List.of(new Point(2, 5)), plot.points());
    }

    @Test
    void updateProcessesAllPlotEntitiesIndependently() {
        World world = new World();

        Entity sourceA = world.createEntity();
        ParticleStorageComponent storageA = new ParticleStorageComponent(4);
        storageA.addState(new Vector2D(1.0, 1.0), new Vector2D(0.1, 0.1), new Vector2D(0.01, 0.01));
        world.addComponent(sourceA, storageA);

        Entity sourceB = world.createEntity();
        ParticleStorageComponent storageB = new ParticleStorageComponent(6);
        storageB.addState(new Vector2D(1.0, 1.0), new Vector2D(0.1, 0.1), new Vector2D(0.01, 0.01));
        storageB.addState(new Vector2D(2.0, 2.0), new Vector2D(0.2, 0.2), new Vector2D(0.02, 0.02));
        storageB.addState(new Vector2D(3.0, 3.0), new Vector2D(0.3, 0.3), new Vector2D(0.03, 0.03));
        world.addComponent(sourceB, storageB);

        Entity plotEntityA = world.createEntity();
        PlotComponent plotA = new PlotComponent(List.of(), 0, 0, sourceA.id(), psc -> List.of(new Point(psc.currentSize(), 1)));
        world.addComponent(plotEntityA, plotA);

        Entity plotEntityB = world.createEntity();
        PlotComponent plotB = new PlotComponent(List.of(), 0, 0, sourceB.id(), psc -> List.of(new Point(psc.currentSize(), 2)));
        world.addComponent(plotEntityB, plotB);

        new PlotUpdaterSystem().update(world, 0.033);

        assertEquals(List.of(new Point(1, 1)), plotA.points());
        assertEquals(List.of(new Point(3, 2)), plotB.points());
    }
}
