package com.setoh.pixphy.graphics.system;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Window;

final class WindowUpdaterTest {
    @Test
    void testUpdateKeepsWorldAliveWhenWindowNotClosing() {
        World world = new World();
        Window window = new Window(100, 100, "PixPhy Test");
        WindowUpdater updater = new WindowUpdater(window);
        try {
            updater.update(world, 0.0);
            assertTrue(world.isAlive());
        } finally {
            window.destroy();
        }
    }

    @Test
    void testUpdateKillsWorldWhenWindowClosing() {
        World world = new World();
        Window window = new Window(100, 100, "PixPhy Test");
        WindowUpdater updater = new WindowUpdater(window);
        try {
            glfwSetWindowShouldClose(window.handle(), true);
            updater.update(world, 0.0);
            assertFalse(world.isAlive());
        } finally {
            window.destroy();
        }
    }
}
