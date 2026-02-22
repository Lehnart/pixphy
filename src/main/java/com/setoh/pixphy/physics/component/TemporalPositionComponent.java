package com.setoh.pixphy.physics.component;

import java.util.function.Function;

import com.setoh.pixphy.ecs.Component;

public class TemporalPositionComponent implements Component {

    private Function<Double, Position> temporalFunction;

    public TemporalPositionComponent(Function<Double, Position> temporalFunction) {
        this.temporalFunction = temporalFunction;
    }

    public Position getPosition(double t) {
        return temporalFunction.apply(t);
        
    }
}

