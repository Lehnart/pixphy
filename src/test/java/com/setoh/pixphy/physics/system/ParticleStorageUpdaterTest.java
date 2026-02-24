package com.setoh.pixphy.physics.system;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.ParticleComponent;
import com.setoh.pixphy.physics.component.ParticleStorageComponent;
import com.setoh.pixphy.physics.component.PositionComponent;
import com.setoh.pixphy.physics.component.Vector2D;

final class ParticleStorageUpdaterTest {

    @Test
    void updateStoresParticleStateInStorage() {
        World world = new World();
        Entity entity = world.createEntity();

        Vector2D position = new Vector2D(1.0, 2.0);
        Vector2D velocity = new Vector2D(3.0, 4.0);
        Vector2D acceleration = new Vector2D(5.0, 6.0);

        ParticleComponent particle = new ParticleComponent(position, velocity, acceleration);
        ParticleStorageComponent storage = new ParticleStorageComponent(5);

        world.addComponent(entity, particle);
        world.addComponent(entity, storage);

        new ParticleStorageUpdater().update(world, 0.016);

        assertEquals(1, storage.currentSize());
        assertEquals(List.of(position), storage.getPositionHistory());
        assertEquals(List.of(velocity), storage.getVelocityHistory());
        assertEquals(List.of(acceleration), storage.getAccelerationHistory());
    }

    @Test
    void updateAppendsMultipleFramesAndKeepsFifoAtCapacity() {
        World world = new World();
        Entity entity = world.createEntity();

        Vector2D position = new Vector2D(0.0, 0.0);
        Vector2D velocity = new Vector2D(1.0, 1.0);
        Vector2D acceleration = new Vector2D(0.0, 0.0);

        ParticleComponent particle = new ParticleComponent(position, velocity, acceleration);
        ParticleStorageComponent storage = new ParticleStorageComponent(2);

        world.addComponent(entity, particle);
        world.addComponent(entity, storage);

        ParticleStorageUpdater updater = new ParticleStorageUpdater();

        updater.update(world, 0.016);
        Vector2D firstPosRef = storage.getPositionHistory().get(0);

        position = new Vector2D(10.0, 10.0);
        velocity = new Vector2D(2.0, 2.0);
        acceleration = new Vector2D(0.5, 0.5);
        world.removeComponent(entity, ParticleComponent.class);
        world.addComponent(entity, new ParticleComponent(position, velocity, acceleration));
        updater.update(world, 0.016);

        position = new Vector2D(20.0, 20.0);
        velocity = new Vector2D(3.0, 3.0);
        acceleration = new Vector2D(1.0, 1.0);
        world.removeComponent(entity, ParticleComponent.class);
        world.addComponent(entity, new ParticleComponent(position, velocity, acceleration));
        updater.update(world, 0.016);

        assertEquals(2, storage.currentSize());
        assertEquals(List.of(10.0, 20.0), storage.getPositionHistory().stream().map(Vector2D::x).toList());
        assertSame(storage.getPositionHistory().get(1), position);
        assertEquals(10.0, storage.getPositionHistory().get(0).x());
        assertEquals(20.0, storage.getPositionHistory().get(1).x());
    }

    @Test
    void updateSkipsEntitiesWithoutBothRequiredComponents() {
        World world = new World();

        Entity withBoth = world.createEntity();
        ParticleStorageComponent storage = new ParticleStorageComponent(3);
        world.addComponent(withBoth, new ParticleComponent(new Vector2D(7.0, 8.0), new Vector2D(1.0, 2.0), new Vector2D(3.0, 4.0)));
        world.addComponent(withBoth, storage);

        Entity particleOnly = world.createEntity();
        world.addComponent(particleOnly, new ParticleComponent(new Vector2D(100.0, 100.0), new Vector2D(0.0, 0.0), new Vector2D(0.0, 0.0)));

        Entity storageOnly = world.createEntity();
        world.addComponent(storageOnly, new ParticleStorageComponent(2));
        world.addComponent(storageOnly, new PositionComponent(1.0, 2.0));

        assertDoesNotThrow(() -> new ParticleStorageUpdater().update(world, 1.0));
        assertEquals(1, storage.currentSize());
    }
}
