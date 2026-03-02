package com.setoh.pixphy.plot.system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.ParticleStorageComponent;
import com.setoh.pixphy.plot.component.PlotComponent;
import com.setoh.pixphy.plot.component.PlotComponent.Point;

final class PlotUpdaterSystemTest {

    @Test
    void updateRecomputesPointsFromMappedParticleStorage() {
        World world = new World();

        Entity sourceEntity = world.createEntity();
        ParticleStorageComponent storage = new ParticleStorageComponent(5);
        storage.addState(null, null, null);
        storage.addState(null, null, null);
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
        storageA.addState(null, null, null);
        world.addComponent(sourceA, storageA);

        Entity sourceB = world.createEntity();
        ParticleStorageComponent storageB = new ParticleStorageComponent(6);
        storageB.addState(null, null, null);
        storageB.addState(null, null, null);
        storageB.addState(null, null, null);
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
