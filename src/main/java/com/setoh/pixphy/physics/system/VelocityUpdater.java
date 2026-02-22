package com.setoh.pixphy.physics.system;

import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.AccelerationComponent;
import com.setoh.pixphy.physics.component.VelocityComponent;

public class VelocityUpdater implements ECSSystem {
    
    @Override
    public void update(World world, double dt) {
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(AccelerationComponent.class, VelocityComponent.class));
        for(EntityComponents components : entityComponents) {
            AccelerationComponent accelerationComponent = (AccelerationComponent) components.components().get(0);
            VelocityComponent velocityComponent = (VelocityComponent) components.components().get(1);
            velocityComponent.setDx(velocityComponent.getDx() + (accelerationComponent.getDDx()*dt));
            velocityComponent.setDy(velocityComponent.getDy() + (accelerationComponent.getDDy()*dt));            
        }    
    }
}
