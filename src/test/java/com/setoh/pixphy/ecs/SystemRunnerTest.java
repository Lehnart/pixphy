package com.setoh.pixphy.ecs;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.components.Position;
import com.setoh.pixphy.physics.components.Velocity;

class SystemRunnerTest{
    @Test
    void systemRunner() {
        World world = new World();
        Entity entity = world.createEntity();
        world.addComponent(entity, new Position(0.0, 0.0));
        world.addComponent(entity, new Velocity(2.0, -1.0));

        System movementSystem = (ecsWorld, dt) -> {
            for (EntityComponents result : ecsWorld.getEntitiesWithComponents(List.of(Position.class, Velocity.class))) {
                Position position = (Position) result.components().get(0);
                Velocity velocity = (Velocity) result.components().get(1);
                position.setX(position.getX() + velocity.getDx() * dt);
                position.setY(position.getY() + velocity.getDy() * dt);
            }
        };

        SystemRunner.runSystems(world, List.of(movementSystem), 0.5);

        Position updated = world.getComponent(entity, Position.class);
        assertEquals(new Position(1.0, -0.5), updated);
    }
}