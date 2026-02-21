package com.setoh.pixphy.physics.system;

import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.physics.component.Position;
import com.setoh.pixphy.physics.component.TemporalPositionComponent;

public class TemporalPositionSpriteUpdater implements ECSSystem {
    
    private double t = 0.;

    @Override
    public void update(World world, double dt) {
        t += dt;
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(TemporalPositionComponent.class, Sprite.class));
        for(EntityComponents components : entityComponents) {
            TemporalPositionComponent temporalPositionComponent = (TemporalPositionComponent) components.components().get(0);
            Sprite sprite = (Sprite) components.components().get(1);
            Position pos = temporalPositionComponent.getPosition(t);
            sprite.setX(pos.x());
            sprite.setY(pos.y());
        }    
    }
}
