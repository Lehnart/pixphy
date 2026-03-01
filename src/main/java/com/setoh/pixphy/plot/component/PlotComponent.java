package com.setoh.pixphy.plot.component;

import java.util.List;
import java.util.function.Function;

import com.setoh.pixphy.ecs.Component;
import com.setoh.pixphy.physics.component.ParticleStorageComponent;

public class PlotComponent implements Component{

    public static record Point(int x, int y){}

    private List<Point> points;
    private int x0;
    private int y0;
    private int entityId; 
    private Function<ParticleStorageComponent, List<Point>> plotMapping;

    public PlotComponent(List<Point> points, int x0, int y0, int entityId, Function<ParticleStorageComponent, List<Point>> plotMapping){
        this.points = points;
        this.x0 = x0;
        this.y0 = y0;
        this.entityId = entityId;
        this.plotMapping = plotMapping;
    }
    
    public List<Point> points(){
        return points;
    }

    public void setPoints(List<Point> points){
        this.points = points;
    }

    public int x0(){
        return x0;
    }

    public int y0(){
        return y0;
    }

    public int entityId(){
        return entityId;
    }

    public Function<ParticleStorageComponent, List<Point>> mapping(){
        return this.plotMapping;
    }
}
