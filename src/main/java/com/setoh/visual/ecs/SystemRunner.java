package com.setoh.visual.ecs;

import java.util.List;

public final class SystemRunner {
    private SystemRunner() {
    }

    public static void runSystems(World world, List<System> systems, double dt) {
        for (System system : systems) {
            system.update(world, dt);
        }
    }
}
