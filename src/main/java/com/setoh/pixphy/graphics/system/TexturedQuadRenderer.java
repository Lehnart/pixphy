package com.setoh.pixphy.graphics.system;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

import com.setoh.pixphy.graphics.resource.ShaderProgram;
import com.setoh.pixphy.graphics.resource.Texture;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public final class TexturedQuadRenderer {

    private static final String VERTEX_SHADER = """
        #version 330 core
        layout (location = 0) in vec2 aPosition;
        layout (location = 1) in vec2 aTexCoord;

        out vec2 vTexCoord;
        uniform vec4 uRect;
        uniform vec2 uViewportSize;

        void main() {
            vTexCoord = aTexCoord;
            vec2 pixelPos = uRect.xy + aPosition * uRect.zw;
            vec2 ndc = vec2(
                (pixelPos.x / uViewportSize.x) * 2.0 - 1.0,
                1.0 - (pixelPos.y / uViewportSize.y) * 2.0
            );
            gl_Position = vec4(ndc, 0.0, 1.0);
        }
        """;

    private static final String FRAGMENT_SHADER = """
        #version 330 core
        in vec2 vTexCoord;

        uniform sampler2D uTexture;

        out vec4 fragColor;

        void main() {
            fragColor = texture(uTexture, vTexCoord);
        }
        """;

    private final ShaderProgram shaderProgram;
    private final float viewportWidth;
    private final float viewportHeight;
    private final int vao;
    private final int vbo;
    private final int ebo;

    public TexturedQuadRenderer(int viewportWidth, int viewportHeight) {
        shaderProgram = new ShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;

        float[] vertices = {
            0.0f, 0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f
        };

        int[] indices = {
            0, 1, 2,
            2, 3, 0
        };

        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ebo = glGenBuffers();

        glBindVertexArray(vao);

        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        IntBuffer indexBuffer = MemoryUtil.memAllocInt(indices.length);
        indexBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 4 * Float.BYTES, 0L);
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 4 * Float.BYTES, 2L * Float.BYTES);

        glBindVertexArray(0);

        MemoryUtil.memFree(vertexBuffer);
        MemoryUtil.memFree(indexBuffer);
    }

    public void draw(Texture texture, float x, float y, float w, float h) {
        shaderProgram.use();
        shaderProgram.setInt("uTexture", 0);
        shaderProgram.setVec2("uViewportSize", viewportWidth, viewportHeight);
        shaderProgram.setVec4("uRect", x, y, w, h);

        texture.bind(0);

        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0L);
        glBindVertexArray(0);
    }

    public void destroy() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);
        shaderProgram.destroy();
    }
}
