package com.setoh.pixphy.graphics.system;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Texture;
import com.setoh.pixphy.graphics.resource.TexturedQuadRenderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public final class TextureRenderSystem implements ECSSystem {

    private final Texture texture;
    private final TexturedQuadRenderer renderer;
    private final float spriteX;
    private final float spriteY;
    private boolean destroyed = false;

    public TextureRenderSystem(String textureResourcePath, int viewportWidth, int viewportHeight) {
        texture = new Texture(textureResourcePath);
        renderer = new TexturedQuadRenderer(viewportWidth, viewportHeight);
        spriteX = 0.0f;
        spriteY = 0.0f;
    }

    @Override
    public void update(World world, double dt) {
        if (destroyed) {
            return;
        }

        glClearColor(0.05f, 0.05f, 0.08f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        renderer.draw(texture, spriteX, spriteY, texture.width()*2.f, texture.height()*2.f);
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
