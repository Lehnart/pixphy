package com.setoh.pixphy.physics.component;

import java.util.function.Function;

import com.setoh.pixphy.ecs.Component;

public class TemporalPositionComponent implements Component {

    private Function<Double, Vector2D> temporalFunction;

    public TemporalPositionComponent(Function<Double, Vector2D> temporalFunction) {
        this.temporalFunction = temporalFunction;
    }

    public Vector2D getPosition(double t) {
        return temporalFunction.apply(t);
        
    }
}

