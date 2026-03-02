package com.setoh.pixphy.plot.component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.setoh.pixphy.ecs.Component;
import com.setoh.pixphy.physics.component.ParticleStorageComponent;

public class PlotComponent implements Component{

    public static record Point(int x, int y){}

    private List<Point> points = new ArrayList<>();
    private List<Point> hBars = List.of();
    private List<Point> vBars = List.of();

    private int entityId; 
    private Function<ParticleStorageComponent, List<Point>> pointMapping;
    private Function<ParticleStorageComponent, List<Point>> hBarMapping;
    private Function<ParticleStorageComponent, List<Point>> vBarMapping;

    public PlotComponent(
        int entityId, Function<ParticleStorageComponent, List<Point>> pointMapping
    ){
        this(entityId, pointMapping, psv -> List.of(), psv -> List.of());
    }
    
    public PlotComponent(
        int entityId, 
        Function<ParticleStorageComponent, List<Point>> pointMapping, 
        Function<ParticleStorageComponent, List<Point>> hBarMapping,
        Function<ParticleStorageComponent, List<Point>> vBarMapping
    ){
        this.entityId = entityId;
        this.pointMapping = pointMapping;
        this.hBarMapping = hBarMapping;
        this.vBarMapping = vBarMapping;
    }

    public List<Point> points(){
        return points;
    }

    public void setPoints(List<Point> points){
        this.points = points;
    }

    public int entityId(){
        return entityId;
    }

    public void setHBars(List<Point> hBarsTopLeft){
        hBars = new ArrayList<>(hBarsTopLeft);
    }

    public void setVBars(List<Point> vBarsTopLeft){
        vBars = new ArrayList<>(vBarsTopLeft);
    }

    public List<Point> hBars(){
        return hBars;
    }

    public List<Point> vBars(){
        return vBars;
    }

    public Function<ParticleStorageComponent, List<Point>> pointMapping(){
        return this.pointMapping;
    }

    public Function<ParticleStorageComponent, List<Point>> hBarMapping(){
        return this.hBarMapping;
    }

        public Function<ParticleStorageComponent, List<Point>> vBarMapping(){
        return this.vBarMapping;
    }
}

