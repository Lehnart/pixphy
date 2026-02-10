package com.setoh.pixphy.graphics.resource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

final class ShaderProgramTest {

    private static final String VALID_VERTEX_SHADER = """
        #version 330 core
        layout (location = 0) in vec2 aPosition;

        uniform vec2 uViewportSize;
        uniform vec4 uRect;

        void main() {
            gl_Position = vec4(aPosition, 0.0, 1.0);
        }
        """;

    private static final String VALID_FRAGMENT_SHADER = """
        #version 330 core
        out vec4 fragColor;

        uniform sampler2D uTexture;

        void main() {
            fragColor = vec4(1.0, 1.0, 1.0, 1.0);
        }
        """;

    @Test
    void testUseSetUniformsAndDestroy() {
        Window window = new Window(100, 100, "PixPhy ShaderProgram Test");
        try {
            ShaderProgram shaderProgram = new ShaderProgram(VALID_VERTEX_SHADER, VALID_FRAGMENT_SHADER);

            assertDoesNotThrow(shaderProgram::use);
            assertDoesNotThrow(() -> shaderProgram.setInt("uTexture", 0));
            assertDoesNotThrow(() -> shaderProgram.setVec2("uViewportSize", 100.0f, 100.0f));
            assertDoesNotThrow(() -> shaderProgram.setVec4("uRect", 0.0f, 0.0f, 10.0f, 10.0f));

            assertDoesNotThrow(() -> shaderProgram.setInt("uMissing", 1));
            assertDoesNotThrow(() -> shaderProgram.setVec2("uMissing", 1.0f, 2.0f));
            assertDoesNotThrow(() -> shaderProgram.setVec4("uMissing", 1.0f, 2.0f, 3.0f, 4.0f));

            assertDoesNotThrow(shaderProgram::destroy);
        } finally {
            window.destroy();
        }
    }

    @Test
    void testConstructorThrowsOnShaderCompilationFailure() {
        Window window = new Window(100, 100, "PixPhy ShaderProgram Test");
        try {
            IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new ShaderProgram("this is not valid shader source", VALID_FRAGMENT_SHADER)
            );

            assertTrue(exception.getMessage().startsWith("Shader compilation failed:"));
        } finally {
            window.destroy();
        }
    }

    @Test
    void testConstructorThrowsOnProgramLinkFailure() {
        Window window = new Window(100, 100, "PixPhy ShaderProgram Test");
        try {
            String vertexShader = """
                #version 330 core
                layout (location = 0) in vec2 aPosition;

                out vec3 vColor;

                void main() {
                    vColor = vec3(1.0, 0.0, 0.0);
                    gl_Position = vec4(aPosition, 0.0, 1.0);
                }
                """;

            String fragmentShader = """
                #version 330 core
                in vec2 vColor;
                out vec4 fragColor;

                void main() {
                    fragColor = vec4(vColor, 0.0, 1.0);
                }
                """;

            IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> new ShaderProgram(vertexShader, fragmentShader)
            );

            assertTrue(exception.getMessage().startsWith("Shader program link failed:"));
        } finally {
            window.destroy();
        }
    }
}
