package com.setoh.pixphy.input.system;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.Store;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.input.component.MouseComponent;
import com.setoh.pixphy.input.system.MouseListener.MousePosition;

final class MouseSpriteUpdaterTest {

    @Test
    void testUpdateMovesMouseSpriteFromStorePosition() {
        World world = new World();
        Entity entity = world.createEntity();
        Sprite sprite = new Sprite(0, 0, "pointer");
        world.addComponent(entity, new MouseComponent());
        world.addComponent(entity, sprite);
        world.getStore().set(Store.MOUSE_POSITION, new MousePosition(20, 10));

        new MouseSpriteUpdater().update(world, 0.0);

        assertEquals(10, sprite.getX());
        assertEquals(5, sprite.getY());
    }

    @Test
    void testUpdateLeavesSpriteUnchangedWhenMousePositionMissing() {
        World world = new World();
        Entity entity = world.createEntity();
        Sprite sprite = new Sprite(3, 4, "pointer");
        world.addComponent(entity, new MouseComponent());
        world.addComponent(entity, sprite);

        new MouseSpriteUpdater().update(world, 0.0);

        assertEquals(3, sprite.getX());
        assertEquals(4, sprite.getY());
    }

    @Test
    void testUpdateStopsWhenFirstMouseEntityHasNoPosition() {
        World world = new World();

        Entity first = world.createEntity();
        Sprite firstSprite = new Sprite(9, 9, "pointer");
        world.addComponent(first, new MouseComponent());
        world.addComponent(first, firstSprite);

        Entity second = world.createEntity();
        Sprite secondSprite = new Sprite(7, 7, "pointer");
        world.addComponent(second, new MouseComponent());
        world.addComponent(second, secondSprite);

        new MouseSpriteUpdater().update(world, 0.0);

        assertEquals(9, firstSprite.getX());
        assertEquals(9, firstSprite.getY());
        assertEquals(7, secondSprite.getX());
        assertEquals(7, secondSprite.getY());
    }
}
