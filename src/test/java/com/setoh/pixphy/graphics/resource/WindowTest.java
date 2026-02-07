package com.setoh.pixphy.graphics.resource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

final class WindowTest {

    private static void assumeLwjglTestsEnabled() {
        Assumptions.assumeTrue(
            Boolean.getBoolean("pixphy.tests.lwjgl"),
            "Set -Dpixphy.tests.lwjgl=true to run LWJGL window tests"
        );
    }

    @Test
    void testWindowShouldCloseDefaultsToFalse() {
        assumeLwjglTestsEnabled();
        Window window = new Window(100, 100, "PixPhy Test");
        try {
            assertFalse(window.shouldClose());
        } finally {
            window.destroy();
        }
    }

    @Test
    void testWindowShouldCloseAfterSet() {
        assumeLwjglTestsEnabled();
        Window window = new Window(100, 100, "PixPhy Test");
        try {
            glfwSetWindowShouldClose(window.handle(), true);
            assertTrue(window.shouldClose());
        } finally {
            window.destroy();
        }
    }
}
