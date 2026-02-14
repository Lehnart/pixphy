package com.setoh.pixphy.graphics.system;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.graphics.resource.Texture;
import com.setoh.pixphy.graphics.resource.Window;

import java.util.HashMap;
import java.util.Map;

final class TextureRenderSystemTest {

    @Test
    void testConstructorWithValidParameters() {
        Window window = new Window(100, 100, "Test");
        try {
            Texture mockTexture = new Texture("textures/space_background.png");
            try {
                Map<String, Texture> textureMap = new HashMap<>();
                textureMap.put("bg", mockTexture);
                
                TextureRenderSystem system = new TextureRenderSystem(textureMap, 640, 480, "bg");
                assertDoesNotThrow(() -> system);
            } finally {
                mockTexture.destroy();
            }
        } finally {
            window.destroy();
        }
    }

    @Test
    void testConstructorWithDifferentViewportSizes() {
        Window window = new Window(100, 100, "Test");
        try {
            Texture texture = new Texture("textures/space_background.png");
            try {
                Map<String, Texture> textureMap = new HashMap<>();
                textureMap.put("bg", texture);
                
                new TextureRenderSystem(textureMap, 800, 600, "bg");
                new TextureRenderSystem(textureMap, 1920, 1080, "bg");
                new TextureRenderSystem(textureMap, 320, 240, "bg");
                
                assertDoesNotThrow(() -> {
                    new TextureRenderSystem(textureMap, 640, 480, "bg");
                });
            } finally {
                texture.destroy();
            }
        } finally {
            window.destroy();
        }
    }

}
