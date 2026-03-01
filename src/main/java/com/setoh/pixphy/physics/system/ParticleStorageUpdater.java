package com.setoh.pixphy.physics.system;

import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.ParticleComponent;
import com.setoh.pixphy.physics.component.ParticleStorageComponent;
import com.setoh.pixphy.physics.component.Vector2D;

public class ParticleStorageUpdater implements ECSSystem {
    private double delay = 0.05;
    private double delayCount = 0;

    @Override
    public void update(World world, double dt) {
        delayCount += dt;
        if(delayCount <= delay) return;
        delayCount = 0; 
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(ParticleComponent.class, ParticleStorageComponent.class));
        for(EntityComponents components : entityComponents) {
            ParticleComponent p = (ParticleComponent) components.components().get(0);
            ParticleStorageComponent ps = (ParticleStorageComponent) components.components().get(1);
            
            Vector2D vel = p.getVelocity();
            Vector2D acc = p.getAcceleration();
            Vector2D pos = p.getPosition();
            
            ps.addState(pos, vel, acc);
        }    
    }
}
