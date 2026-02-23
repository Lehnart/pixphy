package com.setoh.pixphy.physics.component;

import com.setoh.pixphy.ecs.Component;

public class ParticleComponent implements Component {

    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;

    public ParticleComponent(Vector2D pos, Vector2D vel, Vector2D acc) {
        this.position = pos;
        this.velocity = vel;
        this.acceleration = acc;
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

}

