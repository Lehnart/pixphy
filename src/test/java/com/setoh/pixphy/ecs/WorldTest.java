package com.setoh.pixphy.ecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

final class WorldTest {
    static final class Position implements Component {
        double x;
        double y;

        Position(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Position position)) {
                return false;
            }
            return Double.compare(position.x, x) == 0
                && Double.compare(position.y, y) == 0;
        }

        @Override
        public int hashCode() {
            long bits = Double.doubleToLongBits(x);
            long bits2 = Double.doubleToLongBits(y);
            return (int) (bits ^ (bits >>> 32) ^ bits2 ^ (bits2 >>> 32));
        }
    }

    static final class Velocity implements Component {
        double dx;
        double dy;

        Velocity(double dx, double dy) {
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Velocity velocity)) {
                return false;
            }
            return Double.compare(velocity.dx, dx) == 0
                && Double.compare(velocity.dy, dy) == 0;
        }

        @Override
        public int hashCode() {
            long bits = Double.doubleToLongBits(dx);
            long bits2 = Double.doubleToLongBits(dy);
            return (int) (bits ^ (bits >>> 32) ^ bits2 ^ (bits2 >>> 32));
        }
    }

    @Test
    void addGetRemoveComponent() {
        World world = new World();
        Entity entity = world.createEntity();

        Position position = new Position(1.0, 2.0);
        world.addComponent(entity, position);

        assertEquals(position, world.getComponent(entity, Position.class));
        assertTrue(world.hasComponent(entity, Position.class));

        world.removeComponent(entity, Position.class);
        assertNull(world.getComponent(entity, Position.class));
        assertFalse(world.hasComponent(entity, Position.class));
    }

    @Test
    void queryComponents() {
        World world = new World();
        Entity a = world.createEntity();
        Entity b = world.createEntity();

        world.addComponent(a, new Position(0.0, 0.0));
        world.addComponent(a, new Velocity(1.0, 0.0));
        world.addComponent(b, new Position(5.0, 5.0));

        List<EntityComponents> results = (List<EntityComponents>) world.getEntitiesWithComponents(List.of(Position.class, Velocity.class));
        assertEquals(1, results.size());
        EntityComponents result = results.get(0);
        assertEquals(a, result.entity());
        assertEquals(
            List.of(new Position(0.0, 0.0), new Velocity(1.0, 0.0)),
            result.components()
        );
    }

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
                position.x += velocity.dx * dt;
                position.y += velocity.dy * dt;
            }
        };

        SystemRunner.runSystems(world, List.of(movementSystem), 0.5);

        Position updated = world.getComponent(entity, Position.class);
        assertEquals(new Position(1.0, -0.5), updated);
    }
}
