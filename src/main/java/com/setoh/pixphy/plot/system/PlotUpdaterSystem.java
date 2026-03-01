package com.setoh.pixphy.plot.system;

import java.util.Comparator;
import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.Entity;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.ParticleStorageComponent;
import com.setoh.pixphy.plot.component.PlotComponent;

public class PlotUpdaterSystem implements ECSSystem{


    @Override
    public void update(World world, double dt) {
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(PlotComponent.class));
        for(EntityComponents components : entityComponents) {
            PlotComponent plot = (PlotComponent) components.components().get(0);
            int entityId = plot.entityId();
            ParticleStorageComponent psc = world.getComponent(new Entity(entityId), ParticleStorageComponent.class);
            plot.setPoints(plot.mapping().apply(psc));;
        }    
    }
    
}
