package com.setoh.visual.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class World {
    private int nextEntityId = 1;
    private final Map<Entity, List<Component>> entityToComponents = new HashMap<>();

    public Entity createEntity() {
        int entityId = nextEntityId;
        nextEntityId += 1;
        return new Entity(entityId);
    }

    public void addComponent(Entity entity, Component component) {
        entityToComponents
            .computeIfAbsent(entity, k -> new LinkedList<>())
            .add(component);
    }

    public <T extends Component> void removeComponent(Entity entity, Class<T> componentType) {
        List<Component> components = entityToComponents.get(entity);
        if (components == null) {
            return;
        }
        components.removeIf(c -> componentType.isInstance(c));
    }

    public <T extends Component> T getComponent(Entity entity, Class<T> componentType) {
        List <Component> components = entityToComponents.get(entity);
        if (components == null) {
            return null;
        }
        Optional<Component> component = components.stream().filter(c->componentType.isInstance(c)).findFirst();
        if (component.isEmpty()) {
            return null;
        }
        return componentType.cast(component.get());
    }

    public boolean hasComponent(Entity entity, Class<? extends Component> componentType) {
        List<Component> components = entityToComponents.get(entity);
        return components != null && components.stream().anyMatch(c -> componentType.isInstance(c));
    }

    public List<EntityComponents> getEntitiesWithComponents(Set<Class<? extends Component>> componentTypes) {
        if (componentTypes.size() == 0) {
            return List.of();
        }
        
        List<EntityComponents> results = new ArrayList<>();
        for(Entity e: entityToComponents.keySet()){
            List<? extends Component> entityComponents = componentTypes.stream().map(ct -> getComponent(e, ct)).toList();
            if(entityComponents.stream().allMatch(c -> c != null)){
                results.add(new EntityComponents(e, entityComponents));
            }
        }
        return results;        
    }
}
