package com.setoh.pixphy.physics.system;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.physics.component.Vector2D;
import com.setoh.pixphy.physics.component.TemporalPositionComponent;

final class TemporalPositionSpriteUpdaterTest {

    @Test
    void updateMovesSpriteFromTemporalFunction() {
        World world = new World();
        Entity entity = world.createEntity();
        Sprite sprite = new Sprite(0, 0, "textures/asteroid.png");

        world.addComponent(entity, new TemporalPositionComponent(t -> new Vector2D(t * 10.0, t * 20.0)));
        world.addComponent(entity, sprite);

        TemporalPositionSpriteUpdater updater = new TemporalPositionSpriteUpdater();
        updater.update(world, 0.5);

        assertEquals(5, sprite.getX());
        assertEquals(10, sprite.getY());
    }

    @Test
    void updateAccumulatesTimeAcrossFrames() {
        World world = new World();
        Entity entity = world.createEntity();
        Sprite sprite = new Sprite(0, 0, "textures/asteroid.png");

        world.addComponent(entity, new TemporalPositionComponent(t -> new Vector2D(t * 10.0, -t * 10.0)));
        world.addComponent(entity, sprite);

        TemporalPositionSpriteUpdater updater = new TemporalPositionSpriteUpdater();

        updater.update(world, 0.5);
        updater.update(world, 0.75);

        assertEquals(12, sprite.getX());
        assertEquals(-12, sprite.getY());
    }

    @Test
    void updateSkipsEntitiesWithoutBothRequiredComponents() {
        World world = new World();

        Entity fullEntity = world.createEntity();
        Sprite fullSprite = new Sprite(1, 2, "textures/full.png");
        world.addComponent(fullEntity, new TemporalPositionComponent(t -> new Vector2D(7.0, 9.0)));
        world.addComponent(fullEntity, fullSprite);

        Entity spriteOnlyEntity = world.createEntity();
        Sprite spriteOnly = new Sprite(100, 200, "textures/sprite-only.png");
        world.addComponent(spriteOnlyEntity, spriteOnly);

        Entity temporalOnlyEntity = world.createEntity();
        world.addComponent(temporalOnlyEntity, new TemporalPositionComponent(t -> new Vector2D(999.0, 999.0)));

        TemporalPositionSpriteUpdater updater = new TemporalPositionSpriteUpdater();

        assertDoesNotThrow(() -> updater.update(world, 1.0));
        assertEquals(7, fullSprite.getX());
        assertEquals(9, fullSprite.getY());
        assertEquals(100, spriteOnly.getX());
        assertEquals(200, spriteOnly.getY());
    }
}
