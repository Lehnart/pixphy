package com.setoh.pixphy.graphics.system;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.TextureMap;
import com.setoh.pixphy.graphics.resource.Window;
import com.setoh.pixphy.plot.component.PlotComponent;
import com.setoh.pixphy.plot.component.PlotComponent.Point;

final class PlotRenderSystemTest {

    @Test
    void updateDoesNotThrowWhenWorldHasNoPlotEntities() {
        Window window = new Window(100, 100, "Test");
        try {
            PlotRenderSystem system = new PlotRenderSystem(new TextureMap(), 640, 480);
            World world = new World();

            assertDoesNotThrow(() -> system.update(world, 0.016));
        } finally {
            window.destroy();
        }
    }

    @Test
    void updateDoesNotThrowForEmptyAndPopulatedPlots() {
        Window window = new Window(100, 100, "Test");
        try {
            TextureMap textureMap = new TextureMap();
            PlotRenderSystem system = new PlotRenderSystem(textureMap, 640, 480);
            World world = new World();

            Entity sourceEntity = world.createEntity();

            Entity emptyPlotEntity = world.createEntity();
            world.addComponent(emptyPlotEntity, new PlotComponent(List.of(), 0, 0, sourceEntity.id(), storage -> List.of()));

            Entity populatedPlotEntity = world.createEntity();
            world.addComponent(
                populatedPlotEntity,
                new PlotComponent(
                    List.of(new Point(1, 2), new Point(3, 4), new Point(-1, 5)),
                    10,
                    20,
                    sourceEntity.id(),
                    storage -> List.of()
                )
            );
            assertDoesNotThrow(() -> system.update(world, 0.016));
        } finally {
            window.destroy();
        }
    }
}
