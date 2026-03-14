package com.setoh.pixphy.input.system;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.Store;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Window;
import com.setoh.pixphy.input.resource.MouseGLFWPosition;

public class MouseListener implements ECSSystem {
    
    private MouseGLFWPosition mousePosition;

    public MouseListener(Window window){
        mousePosition = new MouseGLFWPosition(window);
    }

    @Override
    public void update(World world, double dt) {
        mousePosition.refresh();
        world.getStore().set(Store.MOUSE_POSITION, new MousePosition((int) mousePosition.x(),(int) mousePosition.y()));
        
    }

    public static record MousePosition(int x , int y) {
    } 
}
