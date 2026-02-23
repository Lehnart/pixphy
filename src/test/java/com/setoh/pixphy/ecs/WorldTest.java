package com.setoh.pixphy.ecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.PositionComponent;

final class WorldTest {
    @Test
    void testGetComponent() {
        World world = new World();
        Entity entity = world.createEntity();

        PositionComponent position = new PositionComponent(1.0, 2.0);
        world.addComponent(entity, position);

        assertEquals(position, world.getComponent(entity, PositionComponent.class));
        assertTrue(world.hasComponent(entity, PositionComponent.class));
    }

    @Test
    void testGetNonExistingComponent() {
        World world = new World();
        Entity entityWithPosition = world.createEntity();
        Entity entityWithoutPosition = world.createEntity();

        PositionComponent position = new PositionComponent(1.0, 2.0);
        world.addComponent(entityWithPosition, position);

        assertNull(world.getComponent(entityWithoutPosition, PositionComponent.class));
    }

    @Test
    void testGetComponentFromNonExistingEntity() {
        World world = new World();
        assertNull(world.getComponent(new Entity(5), PositionComponent.class));
    }

    @Test
    void testRemoveComponent() {
        World world = new World();
        Entity entity = world.createEntity();

        PositionComponent position = new PositionComponent(1.0, 2.0);
        world.addComponent(entity, position);
        assertEquals(position, world.getComponent(entity, PositionComponent.class));
        assertTrue(world.hasComponent(entity, PositionComponent.class));

        world.removeComponent(entity, PositionComponent.class);
        assertNull(world.getComponent(entity, PositionComponent.class));
        assertFalse(world.hasComponent(entity, PositionComponent.class));
    }

    @Test
    void testRemoveNonExistingComponent() {
        World world = new World();
        Entity entityWithPosition = world.createEntity();
        Entity entityWithoutPosition = world.createEntity();

        PositionComponent position = new PositionComponent(1.0, 2.0);
        world.addComponent(entityWithPosition, position);

        world.removeComponent(entityWithoutPosition, PositionComponent.class);
        assertNull(world.getComponent(entityWithoutPosition, PositionComponent.class));
        assertFalse(world.hasComponent(entityWithoutPosition, PositionComponent.class));

        assertEquals(position, world.getComponent(entityWithPosition, PositionComponent.class));
        assertTrue(world.hasComponent(entityWithPosition, PositionComponent.class));
    }

    @Test
    void testRemoveNonExistingEntity() {
        World world = new World();
        Entity entity = world.createEntity();
        PositionComponent position = new PositionComponent(1.0, 2.0);
        world.addComponent(entity, position);
        world.removeComponent(new Entity(5), PositionComponent.class);
        assertEquals(position, world.getComponent(entity, PositionComponent.class));
        assertTrue(world.hasComponent(entity, PositionComponent.class));
    }

    @Test
    void testGetEntitiesWithComponents() {
        World world = new World();
        Entity a = world.createEntity();
        Entity b = world.createEntity();

        world.addComponent(a, new PositionComponent(0.0, 0.0));
        world.addComponent(b, new PositionComponent(5.0, 5.0));

        List<EntityComponents> results = world.getEntitiesWithComponents(List.of(PositionComponent.class));
        assertEquals(2, results.size());
        assertEquals(Set.of(a, b), results.stream().map(EntityComponents::entity).collect(java.util.stream.Collectors.toSet()));

        for (EntityComponents result : results) {
            assertEquals(1, result.components().size());
            assertTrue(result.components().get(0) instanceof PositionComponent);
        }
    }

    @Test
    void testGetEntitiesWithNoComponents() {
        World world = new World();
        world.createEntity();

        List<EntityComponents> results = world.getEntitiesWithComponents(List.of(PositionComponent.class));
        assertEquals(0, results.size());
    }

    @Test
    void testGetEntitiesWithComponentsWithEmptyList() {
        World world = new World();
        Entity a = world.createEntity();
        world.addComponent(a, new PositionComponent(0.0, 0.0));

        List<EntityComponents> results = world.getEntitiesWithComponents(List.of());
        assertEquals(0, results.size());
    }

    @Test
    void testRunSystems() {
        World world = new World();
        Entity entity = world.createEntity();
        world.addComponent(entity, new PositionComponent(0.0, 0.0));

        ECSSystem movementSystem = (ecsWorld, dt) -> {
            for (EntityComponents result : ecsWorld.getEntitiesWithComponents(List.of(PositionComponent.class))) {
                PositionComponent position = (PositionComponent) result.components().get(0);
                position.setX(position.getX() + 2.0 * dt);
                position.setY(position.getY() - 1.0 * dt);
            }
        };

        world.addSystem(movementSystem);
        world.runSystems(0.5);

        PositionComponent updated = world.getComponent(entity, PositionComponent.class);
        assertEquals(new PositionComponent(1.0, -0.5), updated);
    }

    @Test
    void testIsAliveDefaultsToTrue() {
        World world = new World();
        assertTrue(world.isAlive());
    }

    @Test
    void testKillSetsAliveToFalse() {
        World world = new World();
        world.kill();
        assertFalse(world.isAlive());
    }
}
