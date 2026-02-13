package com.setoh.pixphy.graphics.system;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Texture;
import com.setoh.pixphy.graphics.resource.TexturedQuadRenderer;
import com.setoh.pixphy.physics.component.Position;

public final class AsteroidRenderSystem implements ECSSystem {

    private final Entity asteroidEntity;
    private final Texture texture;
    private final TexturedQuadRenderer renderer;
    private boolean destroyed = false;

    public AsteroidRenderSystem(Entity asteroidEntity, int viewportWidth, int viewportHeight) {
        this.asteroidEntity = asteroidEntity;
        texture = new Texture("textures/asteroid.png");
        renderer = new TexturedQuadRenderer(viewportWidth, viewportHeight);
    }

    @Override
    public void update(World world, double dt) {
        if (destroyed) {
            return;
        }

        Position position = world.getComponent(asteroidEntity, Position.class);
        if (position == null) {
            return;
        }

        float drawX = (float) (position.getX() - texture.width() / 2.0);
        float drawY = (float) (position.getY() - texture.height() / 2.0);

        renderer.draw(texture, drawX, drawY, texture.width(), texture.height());
    }

    public void destroy() {
        if (destroyed) {
            return;
        }

        renderer.destroy();
        texture.destroy();
        destroyed = true;
    }
}
