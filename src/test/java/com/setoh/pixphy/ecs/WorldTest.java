package com.setoh.pixphy.ecs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.PositionComponent;
import com.setoh.pixphy.physics.component.Velocity;

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
        Entity entity = world.createEntity();

        PositionComponent position = new PositionComponent(1.0, 2.0);
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
        Entity entity = world.createEntity();

        PositionComponent position = new PositionComponent(1.0, 2.0);
        world.addComponent(entity, position);

        world.removeComponent(entity, Velocity.class);
        assertNull(world.getComponent(entity, Velocity.class));
        assertFalse(world.hasComponent(entity, Velocity.class));
        
        assertEquals(position, world.getComponent(entity, PositionComponent.class));
        assertTrue(world.hasComponent(entity, PositionComponent.class));
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
        world.addComponent(a, new Velocity(1.0, 0.0));
        world.addComponent(b, new PositionComponent(5.0, 5.0));

        List<EntityComponents> results = (List<EntityComponents>) world.getEntitiesWithComponents(List.of(PositionComponent.class, Velocity.class));
        assertEquals(1, results.size());
        EntityComponents result = results.get(0);
        assertEquals(a, result.entity());
        assertEquals(
            List.of(new PositionComponent(0.0, 0.0), new Velocity(1.0, 0.0)),
            result.components()
        );
    }

    @Test
    void testGetEntitiesWithNoComponents() {
        World world = new World();
        Entity a = world.createEntity();
        world.addComponent(a, new PositionComponent(0.0, 0.0));

        List<EntityComponents> results = (List<EntityComponents>) world.getEntitiesWithComponents(List.of(PositionComponent.class, Velocity.class));
        assertEquals(0, results.size());
    }

    @Test
    void testGetEntitiesWithComponentsWithEmptyList() {
        World world = new World();
        Entity a = world.createEntity();
        world.addComponent(a, new PositionComponent(0.0, 0.0));

        List<EntityComponents> results = (List<EntityComponents>) world.getEntitiesWithComponents(List.of());
        assertEquals(0, results.size());
    }

    @Test
    void testRunSystems(){
        World world = new World();
        Entity entity = world.createEntity();
        world.addComponent(entity, new PositionComponent(0.0, 0.0));
        world.addComponent(entity, new Velocity(2.0, -1.0));

        ECSSystem movementSystem = (ecsWorld, dt) -> {
            for (EntityComponents result : ecsWorld.getEntitiesWithComponents(List.of(PositionComponent.class, Velocity.class))) {
                PositionComponent position = (PositionComponent) result.components().get(0);
                Velocity velocity = (Velocity) result.components().get(1);
                position.setX(position.getX() + velocity.getDx() * dt);
                position.setY(position.getY() + velocity.getDy() * dt);
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
