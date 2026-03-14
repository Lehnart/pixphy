package com.setoh.pixphy.graphics.resource;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.input.resource.MouseGLFWPosition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

final class MousePositionTest {
    @Test
    void testCapturesMousePositionFromWindow() {
        Window window = new Window(160, 120, "PixPhy Mouse Test");
        MouseGLFWPosition mousePosition = new MouseGLFWPosition(window);

        try {
            glfwSetCursorPos(window.handle(), 42.5, 17.25);
            window.update();

            assertEquals(42.5, mousePosition.x(), 0.0001);
            assertEquals(17.25, mousePosition.y(), 0.0001);
        } finally {
            mousePosition.destroy();
            window.destroy();
        }
    }
}
