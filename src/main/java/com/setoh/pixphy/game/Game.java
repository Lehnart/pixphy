package com.setoh.pixphy.game;

import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Window;
import com.setoh.pixphy.graphics.system.TextureRenderSystem;
import com.setoh.pixphy.graphics.system.WindowUpdater;

public class Game {
    private World world;
    private Window window;
    private TextureRenderSystem textureRenderSystem;
    private long lastLoopTime = System.nanoTime();

    public Game(){
        int windowWidth = 560;
        int windowHeight = 960;

        world = new World();
        window = new Window(windowWidth, windowHeight, "Game");
        textureRenderSystem = new TextureRenderSystem("textures/space_backgrond.png", windowWidth, windowHeight);

        world.addSystem(textureRenderSystem);
        world.addSystem(new WindowUpdater(window));
    }

    public void loop(){
        try {
            while(world.isAlive()){
                long t = System.nanoTime();
                double dt = (t - lastLoopTime)/1e9;
                world.runSystems(dt);
            }
        } finally {
            textureRenderSystem.destroy();
        }
    }

    public static void main(String[] args){
        Game game = new Game();
        game.loop();
    }
}
