package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.ParticleComponent;
import com.setoh.pixphy.physics.component.Vector2D;

final class ParticleComponentTest {

    @Test
    void constructorExposesProvidedReferences() {
        Vector2D position = new Vector2D(1.0, 2.0);
        Vector2D velocity = new Vector2D(3.0, 4.0);
        Vector2D acceleration = new Vector2D(5.0, 6.0);

        ParticleComponent particle = new ParticleComponent(position, velocity, acceleration);

        assertSame(position, particle.getPosition());
        assertSame(velocity, particle.getVelocity());
        assertSame(acceleration, particle.getAcceleration());
    }

    @Test
    void gettersReflectMutationsOnUnderlyingVectors() {
        Vector2D position = new Vector2D(0.0, 0.0);
        Vector2D velocity = new Vector2D(0.0, 0.0);
        Vector2D acceleration = new Vector2D(0.0, 0.0);

        ParticleComponent particle = new ParticleComponent(position, velocity, acceleration);

        particle.getPosition().setX(10.5);
        particle.getPosition().setY(-8.25);
        particle.getVelocity().setX(2.75);
        particle.getVelocity().setY(-1.5);
        particle.getAcceleration().setX(0.3);
        particle.getAcceleration().setY(-0.6);

        assertEquals(10.5, position.x());
        assertEquals(-8.25, position.y());
        assertEquals(2.75, velocity.x());
        assertEquals(-1.5, velocity.y());
        assertEquals(0.3, acceleration.x());
        assertEquals(-0.6, acceleration.y());
    }
}
