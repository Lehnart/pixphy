package com.setoh.pixphy.physics.component;

import java.util.function.Function;

import com.setoh.pixphy.ecs.Component;

public class ParticleComponent implements Component {

    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private Function<Vector2D, Vector2D> positionalForce;
    private double mass = 1.;
    

    public ParticleComponent(Vector2D pos, Vector2D vel, Vector2D acc) {
        this(pos, vel, acc, v -> new Vector2D(0., 0.));
    }

    public ParticleComponent(Vector2D pos, Vector2D vel, Vector2D acc, Function<Vector2D, Vector2D> force) {
        this.position = pos;
        this.velocity = vel;
        this.acceleration = acc;
        this.positionalForce = force;
    }

    public Vector2D getPosition() {
        return position;    
    }

    public Vector2D getVelocity() {
        return velocity;    
    }
    
    public Vector2D getAcceleration() {
        return acceleration;    
    }

    public Function<Vector2D, Vector2D> getPositionalForce() {
        return positionalForce;    
    }

    public double getMass(){
        return mass;
    }
}

