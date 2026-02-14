package com.setoh.pixphy.game;

import java.util.Map;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.graphics.resource.Texture;
import com.setoh.pixphy.graphics.resource.Window;
import com.setoh.pixphy.graphics.system.TextureRenderSystem;
import com.setoh.pixphy.graphics.system.WindowUpdater;
import com.setoh.pixphy.physics.component.Position;

public final class Game {

    private static final int WINDOW_WIDTH = 540;
    private static final int WINDOW_HEIGHT = 960;

    private final World world;
    private long lastLoopTime;

    public Game() {
        world = new World();

        Window window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "PixPhy");
        Map<String, Texture> textures= Map.of( 
            "textures/asteroid.png",
            new Texture("textures/asteroid.png"),
            "textures/space_background.png",
            new Texture("textures/space_background.png")
        );

        Entity asteroid = world.createEntity();
        world.addComponent(asteroid, new Position(WINDOW_WIDTH / 2.0, WINDOW_HEIGHT / 2.0));
        world.addComponent(asteroid, new Sprite(120,245,"textures/asteroid.png"));


        TextureRenderSystem renderSystem = new TextureRenderSystem(textures, WINDOW_WIDTH, WINDOW_HEIGHT, "textures/space_background.png");
        world.addSystem(renderSystem);
        world.addSystem(new WindowUpdater(window));

        lastLoopTime = System.nanoTime();
    }

    public void run() {
        try {
            while (world.isAlive()) {
                long now = System.nanoTime();
                double dt = (now - lastLoopTime) / 1_000_000_000.0;
                lastLoopTime = now;
                world.runSystems(dt);
            }
        } finally {
        }
    }

    public static void main(String[] args) {
        new Game().run();
    }
}
