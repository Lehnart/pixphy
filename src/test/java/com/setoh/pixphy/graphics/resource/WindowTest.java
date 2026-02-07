package com.setoh.pixphy.graphics.resource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import org.junit.jupiter.api.Test;

final class WindowTest {
    @Test
    void testWindowShouldCloseDefaultsToFalse() {
        Window window = new Window(100, 100, "PixPhy Test");
        try {
            assertFalse(window.shouldClose());
        } finally {
            window.destroy();
        }
    }

    @Test
    void testWindowShouldCloseAfterSet() {
        Window window = new Window(100, 100, "PixPhy Test");
        try {
            glfwSetWindowShouldClose(window.handle(), true);
            assertTrue(window.shouldClose());
        } finally {
            window.destroy();
        }
    }
}
