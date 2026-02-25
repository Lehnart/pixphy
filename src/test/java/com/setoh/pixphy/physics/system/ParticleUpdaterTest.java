package com.setoh.pixphy.physics.system;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.ParticleComponent;
import com.setoh.pixphy.physics.component.PositionComponent;
import com.setoh.pixphy.physics.component.Vector2D;

final class ParticleUpdaterTest {

    @Test
    void updateAppliesAccelerationThenMovesPosition() {
        World world = new World();
        Entity entity = world.createEntity();

        ParticleComponent particle = new ParticleComponent(
            new Vector2D(0.0, 0.0),
            new Vector2D(1.0, -2.0),
            new Vector2D(2.0, 4.0)
        );
        world.addComponent(entity, particle);

        new ParticleUpdater().update(world, 0.5);

        assertEquals(1.0, particle.getVelocity().x());
        assertEquals(-2.0, particle.getVelocity().y());
        assertEquals(0.5, particle.getPosition().x());
        assertEquals(-1.0, particle.getPosition().y());
    }

    @Test
    void updateHandlesMultipleParticles() {
        World world = new World();

        Entity e1 = world.createEntity();
        ParticleComponent p1 = new ParticleComponent(new Vector2D(0.0, 0.0), new Vector2D(0.0, 1.0), new Vector2D(0.0, 1.0));
        world.addComponent(e1, p1);

        Entity e2 = world.createEntity();
        ParticleComponent p2 = new ParticleComponent(new Vector2D(5.0, 5.0), new Vector2D(-1.0, 0.0), new Vector2D(0.0, -2.0));
        world.addComponent(e2, p2);

        new ParticleUpdater().update(world, 1.0);

        assertEquals(0.0, p1.getVelocity().x());
        assertEquals(1.0, p1.getVelocity().y());
        assertEquals(0.0, p1.getPosition().x());
        assertEquals(1.0, p1.getPosition().y());

        assertEquals(-1.0, p2.getVelocity().x());
        assertEquals(0.0, p2.getVelocity().y());
        assertEquals(4.0, p2.getPosition().x());
        assertEquals(5.0, p2.getPosition().y());
    }

    @Test
    void updateSkipsEntitiesWithoutParticleComponent() {
        World world = new World();
        Entity entity = world.createEntity();
        PositionComponent positionOnly = new PositionComponent(10.0, 20.0);
        world.addComponent(entity, positionOnly);

        assertDoesNotThrow(() -> new ParticleUpdater().update(world, 1.0));
        assertEquals(new PositionComponent(10.0, 20.0), positionOnly);
    }
}
