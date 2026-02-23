package com.setoh.pixphy.physics.system;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.physics.component.ParticleComponent;
import com.setoh.pixphy.physics.component.Vector2D;

final class ParticleSpriteUpdaterTest {

    @Test
    void updateCopiesParticlePositionIntoSpriteCoordinates() {
        World world = new World();
        Entity entity = world.createEntity();

        ParticleComponent particle = new ParticleComponent(
            new Vector2D(10.9, -3.2),
            new Vector2D(0.0, 0.0),
            new Vector2D(0.0, 0.0)
        );
        Sprite sprite = new Sprite(0, 0, "textures/asteroid.png");

        world.addComponent(entity, particle);
        world.addComponent(entity, sprite);

        new ParticleSpriteUpdater().update(world, 123.0);

        assertEquals(10, sprite.getX());
        assertEquals(-3, sprite.getY());
    }

    @Test
    void updateSkipsEntitiesMissingRequiredComponents() {
        World world = new World();

        Entity fullEntity = world.createEntity();
        ParticleComponent fullParticle = new ParticleComponent(
            new Vector2D(7.0, 8.0),
            new Vector2D(0.0, 0.0),
            new Vector2D(0.0, 0.0)
        );
        Sprite fullSprite = new Sprite(0, 0, "textures/full.png");
        world.addComponent(fullEntity, fullParticle);
        world.addComponent(fullEntity, fullSprite);

        Entity spriteOnlyEntity = world.createEntity();
        Sprite spriteOnly = new Sprite(100, 200, "textures/sprite-only.png");
        world.addComponent(spriteOnlyEntity, spriteOnly);

        Entity particleOnlyEntity = world.createEntity();
        ParticleComponent particleOnly = new ParticleComponent(
            new Vector2D(500.0, 600.0),
            new Vector2D(0.0, 0.0),
            new Vector2D(0.0, 0.0)
        );
        world.addComponent(particleOnlyEntity, particleOnly);

        assertDoesNotThrow(() -> new ParticleSpriteUpdater().update(world, 0.016));

        assertEquals(7, fullSprite.getX());
        assertEquals(8, fullSprite.getY());
        assertEquals(100, spriteOnly.getX());
        assertEquals(200, spriteOnly.getY());
    }
}
