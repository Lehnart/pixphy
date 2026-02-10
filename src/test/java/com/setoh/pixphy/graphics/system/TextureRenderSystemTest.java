package com.setoh.pixphy.graphics.system;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Window;

final class TextureRenderSystemTest {

    @Test
    void testConstructorThrowsWhenTextureResourceDoesNotExist() {
        Window window = new Window(100, 100, "PixPhy TextureRenderSystem Test");
        try {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new TextureRenderSystem("textures/does-not-exist.png", 100, 100)
            );

            assertTrue(exception.getMessage().startsWith("Resource not found in classpath:"));
        } finally {
            window.destroy();
        }
    }

    @Test
    void testUpdateDoesNotThrowWhenSystemIsActive() {
        Window window = new Window(100, 100, "PixPhy TextureRenderSystem Test");
        TextureRenderSystem system = null;
        try {
            World world = new World();
            system = new TextureRenderSystem("textures/space_backgrond.png", 100, 100);

            TextureRenderSystem renderSystem = system;
            assertDoesNotThrow(() -> renderSystem.update(world, 0.016));
        } finally {
            if (system != null) {
                system.destroy();
            }
            window.destroy();
        }
    }

    @Test
    void testDestroyIsIdempotentAndUpdateAfterDestroyDoesNotThrow() {
        Window window = new Window(100, 100, "PixPhy TextureRenderSystem Test");
        TextureRenderSystem system = null;
        try {
            World world = new World();
            system = new TextureRenderSystem("textures/space_backgrond.png", 100, 100);

            TextureRenderSystem renderSystem = system;
            assertDoesNotThrow(renderSystem::destroy);
            assertDoesNotThrow(renderSystem::destroy);
            assertDoesNotThrow(() -> renderSystem.update(world, 0.016));
        } finally {
            if (system != null) {
                system.destroy();
            }
            window.destroy();
        }
    }
}
