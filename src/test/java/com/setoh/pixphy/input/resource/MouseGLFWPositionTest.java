package com.setoh.pixphy.input.resource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.graphics.resource.Window;

final class MouseGLFWPositionTest {

    @Test
    void testRefreshReadsCursorPositionFromWindowHandle() {
        Window window = new Window(100, 100, "PixPhy Test");
        MouseGLFWPosition mousePosition = new MouseGLFWPosition(window.handle());
        try {
            glfwSetCursorPos(window.handle(), 31.0, 47.0);
            mousePosition.refresh();

            assertEquals(31.0, mousePosition.x());
            assertEquals(47.0, mousePosition.y());
        } finally {
            mousePosition.destroy();
            window.destroy();
        }
    }

    @Test
    void testWindowConstructorReadsCursorPosition() {
        Window window = new Window(100, 100, "PixPhy Test");
        MouseGLFWPosition mousePosition = new MouseGLFWPosition(window);
        try {
            glfwSetCursorPos(window.handle(), 12.0, 9.0);
            mousePosition.refresh();

            assertEquals(12.0, mousePosition.x());
            assertEquals(9.0, mousePosition.y());
        } finally {
            mousePosition.destroy();
            window.destroy();
        }
    }

    @Test
    void testDestroyIsIdempotent() {
        Window window = new Window(100, 100, "PixPhy Test");
        MouseGLFWPosition mousePosition = new MouseGLFWPosition(window);
        try {
            mousePosition.destroy();
            assertDoesNotThrow(mousePosition::destroy);
        } finally {
            window.destroy();
        }
    }
}
