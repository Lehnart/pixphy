package com.setoh.pixphy.graphics.resource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BINDING_2D;
import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL13.GL_ACTIVE_TEXTURE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL30.GL_VERTEX_ARRAY_BINDING;

import org.junit.jupiter.api.Test;

final class TexturedQuadRendererTest {

    @Test
    void testDrawBindsTextureAndRestoresVertexArrayBinding() {
        Window window = new Window(100, 100, "PixPhy TexturedQuadRenderer Test");
        try {
            Texture texture = new Texture("textures/space_background.png");
            TexturedQuadRenderer renderer = new TexturedQuadRenderer(100, 100);
            try {
                renderer.draw(texture, 0.0f, 0.0f, 16.0f, 16.0f);

                assertEquals(GL_TEXTURE0, glGetInteger(GL_ACTIVE_TEXTURE));
                assertEquals(texture.id(), glGetInteger(GL_TEXTURE_BINDING_2D));
                assertEquals(0, glGetInteger(GL_VERTEX_ARRAY_BINDING));
            } finally {
                renderer.destroy();
                texture.destroy();
            }
        } finally {
            window.destroy();
        }
    }

    @Test
    void testDestroyDoesNotThrow() {
        Window window = new Window(100, 100, "PixPhy TexturedQuadRenderer Test");
        try {
            TexturedQuadRenderer renderer = new TexturedQuadRenderer(100, 100);
            assertDoesNotThrow(renderer::destroy);
        } finally {
            window.destroy();
        }
    }

    @Test
    void testConstructDrawAndDestroyDoesNotThrow() {
        Window window = new Window(100, 100, "PixPhy TexturedQuadRenderer Test");
        try {
            Texture texture = new Texture("textures/space_background.png");
            TexturedQuadRenderer renderer = new TexturedQuadRenderer(100, 100);
            try {
                assertDoesNotThrow(() -> renderer.draw(texture, 8.0f, 4.0f, 32.0f, 24.0f));
            } finally {
                assertDoesNotThrow(renderer::destroy);
                texture.destroy();
            }
        } finally {
            window.destroy();
        }
    }
}
