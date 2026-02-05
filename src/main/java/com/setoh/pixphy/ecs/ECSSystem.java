package com.setoh.pixphy.ecs;

@FunctionalInterface
public interface ECSSystem {
    void update(World world, double dt);
}
