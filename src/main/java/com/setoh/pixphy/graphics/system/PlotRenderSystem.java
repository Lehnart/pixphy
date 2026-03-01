package com.setoh.pixphy.graphics.system;

import java.util.List;

import com.setoh.pixphy.ecs.ECSSystem;
import com.setoh.pixphy.ecs.EntityComponents;
import com.setoh.pixphy.ecs.World;
import com.setoh.pixphy.graphics.resource.Texture;
import com.setoh.pixphy.graphics.resource.TexturedQuadRenderer;
import com.setoh.pixphy.plot.component.PlotComponent;
import com.setoh.pixphy.plot.component.PlotComponent.Point;

public final class PlotRenderSystem implements ECSSystem {

    private final TexturedQuadRenderer renderer;
    private final Texture markerTexture; 

    public PlotRenderSystem(Texture markerTexture, int viewportWidth, int viewportHeight) {
        renderer = new TexturedQuadRenderer(viewportWidth, viewportHeight);
        this.markerTexture = markerTexture;
    }

    @Override
    public void update(World world, double dt) {
        List<EntityComponents> entityComponents = world.getEntitiesWithComponents(List.of(PlotComponent.class));
        for(EntityComponents components : entityComponents) {
            PlotComponent plot = (PlotComponent) components.components().get(0);
            for(Point p : plot.points()){
                renderer.draw(markerTexture, p.x()*2.f,  p.y()*2.f, markerTexture.width()*2.f, markerTexture.height()*2.f);
            }
        }    
    }

}
