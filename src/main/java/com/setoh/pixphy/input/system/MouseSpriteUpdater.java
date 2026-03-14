package com.setoh.pixphy.input.system;

import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.Store;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.component.Sprite;
import com.setoh.pixphy.input.component.MouseComponent;
import com.setoh.pixphy.input.system.MouseListener.MousePosition;

public class MouseSpriteUpdater implements ECSSystem {

    @Override
    public void update(World world, double dt) {
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(MouseComponent.class, Sprite.class));
        for(EntityComponents components : entityComponents) {
            Sprite sprite = (Sprite) components.components().get(1);
            MousePosition mousePos = (MousePosition) world.getStore().get(Store.MOUSE_POSITION);
            if (mousePos == null){
                return;
            }
            sprite.setX(mousePos.x()/2);
            sprite.setY(mousePos.y()/2);
        }    
    }
}
