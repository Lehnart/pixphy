package com.setoh.visual.ecs;

@FunctionalInterface
public interface System {
    void update(World world, double dt);
}
