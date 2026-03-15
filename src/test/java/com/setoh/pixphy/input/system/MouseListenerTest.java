package com.setoh.pixphy.input.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Store;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Window;
import com.setoh.pixphy.input.system.MouseListener.MousePosition;

final class MouseListenerTest {

    @Test
    void testUpdateStoresMousePositionInWorldStore() {
        World world = new World();
        Window window = new Window(100, 100, "PixPhy Test");
        MouseListener mouseListener = new MouseListener(window);
        try {
            glfwSetCursorPos(window.handle(), 21.0, 42.0);
            mouseListener.update(world, 0.0);

            MousePosition mousePosition = (MousePosition) world.getStore().get(Store.MOUSE_POSITION);
            assertNotNull(mousePosition);
            assertEquals(21, mousePosition.x());
            assertEquals(42, mousePosition.y());
        } finally {
            window.destroy();
        }
    }
}
