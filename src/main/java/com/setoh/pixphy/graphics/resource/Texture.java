package com.setoh.pixphy.graphics.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public final class Texture {

    private final int id;
    private final int width;
    private final int height;

    public Texture(String resourcePath) {
        ByteBuffer encodedImage = null;
        ByteBuffer pixels = null;

        try {
            encodedImage = readClasspathResource(resourcePath);

            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer widthBuffer = stack.mallocInt(1);
                IntBuffer heightBuffer = stack.mallocInt(1);
                IntBuffer channelsBuffer = stack.mallocInt(1);
                
                pixels = STBImage.stbi_load_from_memory(encodedImage, widthBuffer, heightBuffer, channelsBuffer, 4);
                if (pixels == null) {
                    throw new IllegalStateException("Failed to decode image '" + resourcePath + "': " + STBImage.stbi_failure_reason());
                }

                width = widthBuffer.get(0);
                height = heightBuffer.get(0);
            }

            int textureId = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureId);
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
            glBindTexture(GL_TEXTURE_2D, 0);

            id = textureId;
        } finally {
            if (pixels != null) {
                STBImage.stbi_image_free(pixels);
            }
            if (encodedImage != null) {
                MemoryUtil.memFree(encodedImage);
            }
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void bind(int textureUnit) {
        glActiveTexture(GL_TEXTURE0 + textureUnit);
        bind();
    }

    public void destroy() {
        glDeleteTextures(id);
    }

    public int id() {
        return id;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    private static ByteBuffer readClasspathResource(String resourcePath) {
        String normalizedPath = resourcePath.startsWith("/") ? resourcePath.substring(1) : resourcePath;

        try (InputStream input = Texture.class.getClassLoader().getResourceAsStream(normalizedPath)) {
            if (input == null) {
                throw new IllegalArgumentException("Resource not found in classpath: " + resourcePath);
            }

            byte[] data = input.readAllBytes();
            ByteBuffer buffer = MemoryUtil.memAlloc(data.length);
            buffer.put(data).flip();
            return buffer;
        } catch (IOException ex) {
            throw new RuntimeException("Unable to read resource: " + resourcePath, ex);
        }
    }
}
