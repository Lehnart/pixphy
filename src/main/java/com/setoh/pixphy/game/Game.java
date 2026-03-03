package com.setoh.pixphy.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.graphics.resource.TextureMap;
import com.setoh.pixphy.graphics.resource.Window;
import com.setoh.pixphy.graphics.system.PlotRenderSystem;
import com.setoh.pixphy.graphics.system.TextureRenderSystem;
import com.setoh.pixphy.graphics.system.WindowUpdater;
import com.setoh.pixphy.physics.component.ParticleComponent;
import com.setoh.pixphy.physics.component.ParticleStorageComponent;
import com.setoh.pixphy.physics.component.Vector2D;
import com.setoh.pixphy.physics.system.ParticleSpriteUpdater;
import com.setoh.pixphy.physics.system.ParticleStorageUpdater;
import com.setoh.pixphy.physics.system.ParticleUpdater;
import com.setoh.pixphy.physics.system.TemporalPositionSpriteUpdater;
import com.setoh.pixphy.plot.component.PlotComponent;
import com.setoh.pixphy.plot.component.PlotComponent.Point;
import com.setoh.pixphy.plot.system.PlotUpdaterSystem;

public final class Game {


    private final World world;

    public Game() {
        this(540, 960);
    }

    public Game(int viewportWidth, int viewportHeight) {
        world = new World();

        int centerX = viewportWidth / 4;
        int centerY = viewportHeight / 4;
        Entity asteroid = world.createEntity();
        world.addComponent(
            asteroid, 
           new ParticleComponent(new Vector2D(centerX-100, centerY), new Vector2D(0., 0.), new Vector2D(0., 0.), p -> new Vector2D(1.*(centerX - 15 - p.x()), 0.))
        );
        world.addComponent(asteroid, new Sprite(centerX, centerY, TextureMap.ASTEROID_TEXTURE));
        world.addComponent(
            asteroid, 
           new ParticleStorageComponent(25)
        );

        Entity positionChart = world.createEntity();
        world.addComponent(positionChart, new PlotComponent(asteroid.id(), Game::xPlot, xHBars(50., 19,55, 17), vBars(1.,17,85,12)));

        Entity velocityChart = world.createEntity();
        world.addComponent(velocityChart, new PlotComponent(asteroid.id(), Game::vPlot, vxHBars(50., 19,55, 100), vBars(1.,100,168,12)));

        Entity accelerationChart = world.createEntity();
        world.addComponent(accelerationChart, new PlotComponent(asteroid.id(), Game::aPlot, axHBars(50., 19,55, 184), vBars(1.,184,252,12)));

        Window window = new Window(viewportWidth, viewportHeight, "PixPhy");
        TextureMap textureMap = new TextureMap();
        world.addSystem(new WindowUpdater(window));
        world.addSystem(new TextureRenderSystem(textureMap, viewportWidth, viewportHeight, TextureMap.BACKGROUND_TEXTURE));
        world.addSystem(new PlotRenderSystem(textureMap, viewportWidth, viewportHeight));
        world.addSystem(new ParticleUpdater());
        world.addSystem(new ParticleSpriteUpdater());
        world.addSystem(new TemporalPositionSpriteUpdater());
        world.addSystem(new ParticleStorageUpdater(0.15));
        world.addSystem(new PlotUpdaterSystem());
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

    public static void main(String[] args) {
        new Game().run();
    }

    public static List<PlotComponent.Point> xPlot(ParticleStorageComponent psc){
        List<Double> xHistory = psc.getPositionHistory().stream().map(p -> p.x()).toList();
        double minY = xHistory.stream().min(Comparator.naturalOrder()).orElse(0.);
        double maxY = xHistory.stream().max(Comparator.naturalOrder()).orElse(1.);
        double minX = 0;
        double maxX = xHistory.size();

        List<PlotComponent.Point> xPoints = new ArrayList<>();
        for(int i = 0; i < xHistory.size(); i++){
            Double x = 17. + (i*(85-17)/(maxX-minX));
            Double y = 55. - ( (xHistory.get(i)- minY)*(55-19)/(maxY-minY));               
            xPoints.add(new PlotComponent.Point( x.intValue(), y.intValue()));
        } 
        return xPoints;
    }

    public static Function<ParticleStorageComponent, List<Point>> xHBars(double step, int yMin, int yMax, int x){
        return psc -> {
            List<Double> xHistory = psc.getPositionHistory().stream().map(p -> p.x()).toList();
            double minY = xHistory.stream().min(Comparator.naturalOrder()).orElse(0.);
            double maxY = xHistory.stream().max(Comparator.naturalOrder()).orElse(1.);

            List<Double> hBarYs = new ArrayList<>();
            double current = ((int) (maxY/step)) * step;
            while (current>minY){
                hBarYs.add(current);
                current -= step;
            }

            List<Point> ys = new ArrayList<>();
            for(double y : hBarYs){
                Double yPixel = yMax - ( (y - minY)*(yMax-yMin)/(maxY-minY));               
                ys.add(new Point(x ,yPixel.intValue()));
            } 
            return ys;
        };
    }

    public static Function<ParticleStorageComponent, List<Point>> vxHBars(double step, int yMin, int yMax, int x){
        return psc -> {
            List<Double> xHistory = psc.getVelocityHistory().stream().map(p -> p.x()).toList();
            double minY = xHistory.stream().min(Comparator.naturalOrder()).orElse(0.);
            double maxY = xHistory.stream().max(Comparator.naturalOrder()).orElse(1.);

            List<Double> hBarYs = new ArrayList<>();
            double current = ((int) (maxY/step)) * step;
            while (current>minY){
                hBarYs.add(current);
                current -= step;
            }

            List<Point> ys = new ArrayList<>();
            for(double y : hBarYs){
                Double yPixel = yMax - ( (y - minY)*(yMax-yMin)/(maxY-minY));               
                ys.add(new Point(x ,yPixel.intValue()));
            } 
            return ys;
        };
    }

    public static Function<ParticleStorageComponent, List<Point>> axHBars(double step, int yMin, int yMax, int x){
        return psc -> {
            List<Double> xHistory = psc.getAccelerationHistory().stream().map(p -> p.x()).toList();
            double minY = xHistory.stream().min(Comparator.naturalOrder()).orElse(0.);
            double maxY = xHistory.stream().max(Comparator.naturalOrder()).orElse(1.);

            List<Double> hBarYs = new ArrayList<>();
            double current = ((int) (maxY/step)) * step;
            while (current>minY){
                hBarYs.add(current);
                current -= step;
            }

            List<Point> ys = new ArrayList<>();
            for(double y : hBarYs){
                Double yPixel = yMax - ( (y - minY)*(yMax-yMin)/(maxY-minY));               
                ys.add(new Point(x ,yPixel.intValue()));
            } 
            return ys;
        };
    }

    public static Function<ParticleStorageComponent, List<Point>> vBars(double step, int xMin, int xMax, int y){
        return psc -> {
            List<Double> ts = psc.getTimeHistory();
            List<Double> xs = new ArrayList<>();
            double maxT = ts.stream().max(Comparator.naturalOrder()).get();
            double minT = ts.stream().min(Comparator.naturalOrder()).get();
            double current = ((int) (maxT/step)) * step;
            while (current>minT){
                xs.add(current);
                current -= step;
            }

            List<Point> xPixels = new ArrayList<>();
            for(double x : xs){
                Double xPixel = xMin + ((x-minT)*(xMax-xMin)/(maxT-minT));          
                xPixels.add(new Point(xPixel.intValue() , y));
            } 
            return xPixels;
        };
    }

    public static List<PlotComponent.Point> vPlot(ParticleStorageComponent psc){
        List<Double> xHistory = psc.getVelocityHistory().stream().map(p -> p.x()).toList();
        double minY = xHistory.stream().min(Comparator.naturalOrder()).orElse(0.);
        double maxY = xHistory.stream().max(Comparator.naturalOrder()).orElse(1.);
        double minX = 0;
        double maxX = xHistory.size();

        List<PlotComponent.Point> xPoints = new ArrayList<>();
        for(int i = 0; i < xHistory.size(); i++){
            Double x = 100. + (i*(168-100)/(maxX-minX));
            Double y = 55. - ( (xHistory.get(i)- minY)*(55-19)/(maxY-minY));               
            xPoints.add(new PlotComponent.Point( x.intValue(), y.intValue()));
        } 
        return xPoints;
    }

    public static List<PlotComponent.Point> aPlot(ParticleStorageComponent psc){
        List<Double> xHistory = psc.getAccelerationHistory().stream().map(p -> p.x()).toList();
        double minY = xHistory.stream().min(Comparator.naturalOrder()).orElse(0.);
        double maxY = xHistory.stream().max(Comparator.naturalOrder()).orElse(1.);
        double minX = 0;
        double maxX = xHistory.size();

        List<PlotComponent.Point> xPoints = new ArrayList<>();
        for(int i = 0; i < xHistory.size(); i++){
            Double x = 184. + (i*(252-184)/(maxX-minX));
            Double y = 55. - ( (xHistory.get(i)- minY)*(55-19)/(maxY-minY));               
            xPoints.add(new PlotComponent.Point( x.intValue(), y.intValue()));
        } 
        return xPoints;
    }


}
