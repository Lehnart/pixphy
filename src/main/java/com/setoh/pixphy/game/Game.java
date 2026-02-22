package com.setoh.pixphy.game;

import java.util.HashMap;
import java.util.Map;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.graphics.resource.Texture;
import com.setoh.pixphy.graphics.resource.Window;
import com.setoh.pixphy.graphics.system.TextureRenderSystem;
import com.setoh.pixphy.graphics.system.WindowUpdater;
import com.setoh.pixphy.physics.component.Position;
import com.setoh.pixphy.physics.component.TemporalPositionComponent;
import com.setoh.pixphy.physics.system.TemporalPositionSpriteUpdater;

public final class Game {

    public static final String ASTEROID_TEXTURE = "textures/asteroid.png";
    public static final String BACKGROUND_TEXTURE = "textures/space_background.png";

    private final World world;
    private final Entity asteroid;

    public Game() {
        this(540, 960);
    }

    public Game(int viewportWidth, int viewportHeight) {
        world = new World();

        int centerX = viewportWidth / 4;
        int centerY = viewportHeight / 4;

        asteroid = world.createEntity();
        world.addComponent(asteroid, new TemporalPositionComponent( t -> new Position(centerX - 15 + (100.* Math.cos(t)), centerY - 15)));
        world.addComponent(asteroid, new Sprite(centerX, centerY, ASTEROID_TEXTURE));

        Window window = new Window(viewportWidth, viewportHeight, "PixPhy");

        Map<String, Texture> textureMap = new HashMap<>();
        textureMap.put(ASTEROID_TEXTURE, new Texture(ASTEROID_TEXTURE));
        textureMap.put(BACKGROUND_TEXTURE, new Texture(BACKGROUND_TEXTURE));
        world.addSystem(new WindowUpdater(window));
        world.addSystem(new TextureRenderSystem(textureMap, viewportWidth, viewportHeight, BACKGROUND_TEXTURE));
        world.addSystem(new TemporalPositionSpriteUpdater());
        
    }

    public void run() {
        double previousTime = System.nanoTime() / 1_000_000_000.0;
        while (world.isAlive()) {
            double currentTime = System.nanoTime() / 1_000_000_000.0;
            double dt = currentTime - previousTime;
            previousTime = currentTime;
            world.runSystems(dt);
        }
    }

    public World world() {
        return world;
    }

    public Entity asteroid() {
        return asteroid;
    }

    public static void main(String[] args) {
        new Game().run();
    }
}
