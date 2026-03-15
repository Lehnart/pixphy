package com.setoh.pixphy.input.component;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Component;

final class MouseComponentTest {

    @Test
    void testMouseComponentImplementsComponent() {
        MouseComponent mouseComponent = new MouseComponent();
        assertInstanceOf(Component.class, mouseComponent);
    }
}
