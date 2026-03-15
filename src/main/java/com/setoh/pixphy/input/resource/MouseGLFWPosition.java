package com.setoh.pixphy.input.resource;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.system.MemoryStack;

import com.setoh.pixphy.graphics.resource.Window;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;

public final class MouseGLFWPosition {
    private final long windowHandle;
    private final GLFWCursorPosCallback callback;
    private double x;
    private double y;
    private boolean destroyed;

    public MouseGLFWPosition(long windowHandle) {
        this.windowHandle = windowHandle;
        this.callback = GLFWCursorPosCallback.create((window, xPos, yPos) -> {
            x = xPos;
            y = yPos;
        });

        glfwSetCursorPosCallback(windowHandle, callback);
        refresh();
    }

    public MouseGLFWPosition(Window window) {
        this(window.handle());
    }

    public void refresh() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            DoubleBuffer xBuffer = stack.mallocDouble(1);
            DoubleBuffer yBuffer = stack.mallocDouble(1);
            glfwGetCursorPos(windowHandle, xBuffer, yBuffer);
            x = xBuffer.get(0);
            y = yBuffer.get(0);
        }
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public void destroy() {
        if (destroyed) {
            return;
        }

        glfwSetCursorPosCallback(windowHandle, null);
        callback.free();
        destroyed = true;
    }
}
