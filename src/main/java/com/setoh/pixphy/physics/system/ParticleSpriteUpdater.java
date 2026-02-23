package com.setoh.pixphy.physics.system;

import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.physics.component.ParticleComponent;

public class ParticleSpriteUpdater implements ECSSystem {

    @Override
    public void update(World world, double dt) {
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(ParticleComponent.class, Sprite.class));
        for(EntityComponents components : entityComponents) {
            ParticleComponent p = (ParticleComponent) components.components().get(0);
            Sprite sprite = (Sprite) components.components().get(1);
            sprite.setX(p.getPosition().x());
            sprite.setY(p.getPosition().y());
        }    
    }
}
