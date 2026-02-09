package com.setoh.pixphy.graphics.resource;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUseProgram;

public final class ShaderProgram {

    private final int programId;

    public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
        int vertexShaderId = compileShader(vertexShaderSource, GL_VERTEX_SHADER);
        int fragmentShaderId = compileShader(fragmentShaderSource, GL_FRAGMENT_SHADER);

        int createdProgramId = glCreateProgram();
        glAttachShader(createdProgramId, vertexShaderId);
        glAttachShader(createdProgramId, fragmentShaderId);
        glLinkProgram(createdProgramId);

        if (glGetProgrami(createdProgramId, GL_LINK_STATUS) == 0) {
            String log = glGetProgramInfoLog(createdProgramId);
            glDeleteShader(vertexShaderId);
            glDeleteShader(fragmentShaderId);
            glDeleteProgram(createdProgramId);
            throw new IllegalStateException("Shader program link failed: " + log);
        }

        glDeleteShader(vertexShaderId);
        glDeleteShader(fragmentShaderId);

        programId = createdProgramId;
    }

    public void use() {
        glUseProgram(programId);
    }

    public void setInt(String uniformName, int value) {
        int location = glGetUniformLocation(programId, uniformName);
        if (location >= 0) {
            glUniform1i(location, value);
        }
    }

    public void setVec2(String uniformName, float x, float y) {
        int location = glGetUniformLocation(programId, uniformName);
        if (location >= 0) {
            glUniform2f(location, x, y);
        }
    }

    public void setVec4(String uniformName, float x, float y, float z, float w) {
        int location = glGetUniformLocation(programId, uniformName);
        if (location >= 0) {
            glUniform4f(location, x, y, z, w);
        }
    }

    public void destroy() {
        glDeleteProgram(programId);
    }

    private static int compileShader(String source, int shaderType) {
        int shaderId = glCreateShader(shaderType);
        glShaderSource(shaderId, source);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            String log = glGetShaderInfoLog(shaderId);
            glDeleteShader(shaderId);
            throw new IllegalStateException("Shader compilation failed: " + log);
        }

        return shaderId;
    }
}
