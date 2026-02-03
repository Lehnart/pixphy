package com.setoh.pixphy.ecs;

@FunctionalInterface
public interface System {
    void update(World world, double dt);
}
