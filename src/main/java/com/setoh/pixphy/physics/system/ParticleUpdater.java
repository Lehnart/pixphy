package com.setoh.pixphy.physics.system;

import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.ParticleComponent;
import com.setoh.pixphy.physics.component.Vector2D;

public class ParticleUpdater implements ECSSystem {
    
    @Override
    public void update(World world, double dt) {
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(ParticleComponent.class));
        for(EntityComponents components : entityComponents) {
            ParticleComponent p = (ParticleComponent) components.components().get(0);
            Vector2D v = p.getVelocity();
            Vector2D a = p.getAcceleration();
            Vector2D pos = p.getPosition();

            v.setX(v.x() + (a.x()*dt));
            v.setY(v.y() + (a.y()*dt));
            pos.setX(pos.x() + (v.x()*dt));
            pos.setY(pos.y() + (v.y()*dt));
        }    
    }
}
