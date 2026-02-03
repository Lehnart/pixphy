package com.setoh.pixphy.ecs;

import java.util.List;

public record EntityComponents(Entity entity, List<? extends Component> components) {
    
}
