package com.setoh.pixphy.graphics.system;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Window;

public class WindowUpdater implements ECSSystem{

    private final Window window;
    public WindowUpdater(Window window){
        this.window = window;
    }

    @Override
    public void update(World world, double dt) {
        window.update();
        if(window.shouldClose()){
            window.destroy();
            world.kill();
        }
    }

    
}
