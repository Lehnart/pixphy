package com.setoh.pixphy.ecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.components.Position;
import com.setoh.pixphy.physics.components.Velocity;

final class WorldTest {
    @Test
    void testGetComponent() {
        World world = new World();
        Entity entity = world.createEntity();

        Position position = new Position(1.0, 2.0);
        world.addComponent(entity, position);

        assertEquals(position, world.getComponent(entity, Position.class));
        assertTrue(world.hasComponent(entity, Position.class));
    }

    @Test
    void testGetNonExistingComponent() {
        World world = new World();
        Entity entity = world.createEntity();

        Position position = new Position(1.0, 2.0);
        world.addComponent(entity, position);

        assertNull(world.getComponent(entity, Velocity.class));
    }

    @Test
    void testGetComponentFromNonExistingEntity() {
        World world = new World();
        assertNull(world.getComponent(new Entity(5), Velocity.class));
    }

    @Test
    void testRemoveComponent() {
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
    void testRemoveNonExistingComponent() {
        World world = new World();
        Entity entity = world.createEntity();

        Position position = new Position(1.0, 2.0);
        world.addComponent(entity, position);

        world.removeComponent(entity, Velocity.class);
        assertNull(world.getComponent(entity, Velocity.class));
        assertFalse(world.hasComponent(entity, Velocity.class));
        
        assertEquals(position, world.getComponent(entity, Position.class));
        assertTrue(world.hasComponent(entity, Position.class));
    }

        @Test
    void testRemoveNonExistingEntity() {
        World world = new World();
        Entity entity = world.createEntity();
        Position position = new Position(1.0, 2.0);
        world.addComponent(entity, position);
        world.removeComponent(new Entity(5), Position.class);
        assertEquals(position, world.getComponent(entity, Position.class));
        assertTrue(world.hasComponent(entity, Position.class));
    }

    @Test
    void testGetEntitiesWithComponents() {
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
    void testGetEntitiesWithNoComponents() {
        World world = new World();
        Entity a = world.createEntity();
        world.addComponent(a, new Position(0.0, 0.0));

        List<EntityComponents> results = (List<EntityComponents>) world.getEntitiesWithComponents(List.of(Position.class, Velocity.class));
        assertEquals(0, results.size());
    }

        @Test
    void testGetEntitiesWithComponentsWithEmptyList() {
        World world = new World();
        Entity a = world.createEntity();
        world.addComponent(a, new Position(0.0, 0.0));

        List<EntityComponents> results = (List<EntityComponents>) world.getEntitiesWithComponents(List.of());
        assertEquals(0, results.size());
    }
}
