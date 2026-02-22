package com.setoh.pixphy.physics.system;

import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.physics.component.PositionComponent;
import com.setoh.pixphy.physics.component.VelocityComponent;

public class PositionUpdater implements ECSSystem {
    
    @Override
    public void update(World world, double dt) {
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(VelocityComponent.class, PositionComponent.class));
        for(EntityComponents components : entityComponents) {
            VelocityComponent velocityComponent = (VelocityComponent) components.components().get(0);
            PositionComponent positionComponent = (PositionComponent) components.components().get(1);
            positionComponent.setX(positionComponent.getX() + (velocityComponent.getDx()*dt));
            positionComponent.setY(positionComponent.getY() + (velocityComponent.getDy()*dt));
        }    
    }
}
