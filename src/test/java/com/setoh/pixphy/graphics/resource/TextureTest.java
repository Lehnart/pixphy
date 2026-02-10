package com.setoh.pixphy.graphics.resource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BINDING_2D;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL13.GL_ACTIVE_TEXTURE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

import org.junit.jupiter.api.Test;

final class TextureTest {

    @Test
    void testLoadTextureAndExposeMetadata() {
        Window window = new Window(100, 100, "PixPhy Texture Test");
        try {
            Texture texture = new Texture("textures/space_backgrond.png");
            try {
                assertTrue(texture.id() > 0);
                assertTrue(texture.width() > 0);
                assertTrue(texture.height() > 0);
            } finally {
                texture.destroy();
            }
        } finally {
            window.destroy();
        }
    }

    @Test
    void testBindBindsTextureToCurrentUnit() {
        Window window = new Window(100, 100, "PixPhy Texture Test");
        try {
            Texture texture = new Texture("textures/space_backgrond.png");
            try {
                texture.bind();
                assertEquals(texture.id(), glGetInteger(GL_TEXTURE_BINDING_2D));
            } finally {
                texture.destroy();
            }
        } finally {
            window.destroy();
        }
    }

    @Test
    void testBindWithTextureUnitActivatesRequestedUnitAndBindsTexture() {
        Window window = new Window(100, 100, "PixPhy Texture Test");
        try {
            Texture texture = new Texture("textures/space_backgrond.png");
            try {
                texture.bind(3);
                assertEquals(GL_TEXTURE0 + 3, glGetInteger(GL_ACTIVE_TEXTURE));
                assertEquals(texture.id(), glGetInteger(GL_TEXTURE_BINDING_2D));
            } finally {
                texture.destroy();
            }
        } finally {
            window.destroy();
        }
    }

    @Test
    void testDestroyDoesNotThrow() {
        Window window = new Window(100, 100, "PixPhy Texture Test");
        try {
            Texture texture = new Texture("textures/space_backgrond.png");
            assertDoesNotThrow(texture::destroy);
        } finally {
            window.destroy();
        }
    }

    @Test
    void testConstructorThrowsWhenResourceDoesNotExist() {
        Window window = new Window(100, 100, "PixPhy Texture Test");
        try {
            IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Texture("textures/does-not-exist.png")
            );

            assertTrue(exception.getMessage().startsWith("Resource not found in classpath:"));
        } finally {
            window.destroy();
        }
    }
}
