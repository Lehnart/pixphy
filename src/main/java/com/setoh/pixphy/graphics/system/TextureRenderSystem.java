package com.setoh.pixphy.graphics.system;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.graphics.resource.Texture;
import com.setoh.pixphy.graphics.resource.TexturedQuadRenderer;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.util.List;
import java.util.Map;

public final class TextureRenderSystem implements ECSSystem {

    private final TexturedQuadRenderer renderer;
    private final Map<String, Texture> textureMap;
    private final String backgroundTextureName; 

    public TextureRenderSystem(Map<String, Texture> textureMap, int viewportWidth, int viewportHeight, String background) {
        this.textureMap = textureMap;
        backgroundTextureName = background;
        renderer = new TexturedQuadRenderer(viewportWidth, viewportHeight);
    }

    @Override
    public void update(World world, double dt) {
        glClearColor(0.05f, 0.05f, 0.08f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        Texture bgTexture = textureMap.get(backgroundTextureName);
        renderer.draw(bgTexture, 0,0, bgTexture.width()*2.f, bgTexture.height()*2.f);

        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(Sprite.class));
        for(EntityComponents components : entityComponents) {
                Sprite sprite = (Sprite) components.components().get(0);
                Texture texture = textureMap.get(sprite.getTextureName());
                renderer.draw(texture, sprite.getX()*2.f, sprite.getY()*2.f, texture.width()*2.f, texture.height()*2.f);
        }    
    }

}
