package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.setoh.pixphy.physics.component.ParticleStorageComponent;
import com.setoh.pixphy.physics.component.Vector2D;

final class ParticleStorageComponentTest {

    @Test
    void constructorRejectsNonPositiveBufferSize() {
        assertThrows(IllegalArgumentException.class, () -> new ParticleStorageComponent(0));
        assertThrows(IllegalArgumentException.class, () -> new ParticleStorageComponent(-1));
    }

    @Test
    void constructorSetsCapacityAndInitialSize() {
        ParticleStorageComponent storage = new ParticleStorageComponent(3);

        assertEquals(3, storage.maxSize());
        assertEquals(0, storage.currentSize());
        assertEquals(List.of(), storage.getPositionHistory());
        assertEquals(List.of(), storage.getVelocityHistory());
        assertEquals(List.of(), storage.getAccelerationHistory());
    }

    @Test
    void addStateStoresSynchronizedTriplesInChronologicalOrder() {
        ParticleStorageComponent storage = new ParticleStorageComponent(3);

        Vector2D p1 = new Vector2D(1.0, 1.0);
        Vector2D v1 = new Vector2D(2.0, 2.0);
        Vector2D a1 = new Vector2D(3.0, 3.0);

        Vector2D p2 = new Vector2D(10.0, 10.0);
        Vector2D v2 = new Vector2D(20.0, 20.0);
        Vector2D a2 = new Vector2D(30.0, 30.0);

        storage.addState(p1, v1, a1);
        storage.addState(p2, v2, a2);

        assertEquals(2, storage.currentSize());
        assertEquals(List.of(p1, p2), storage.getPositionHistory());
        assertEquals(List.of(v1, v2), storage.getVelocityHistory());
        assertEquals(List.of(a1, a2), storage.getAccelerationHistory());
    }

    @Test
    void addStateWhenFullEvictsOldestAndKeepsOrderOldestToNewest() {
        ParticleStorageComponent storage = new ParticleStorageComponent(2);

        Vector2D p1 = new Vector2D(1.0, 1.0);
        Vector2D v1 = new Vector2D(2.0, 2.0);
        Vector2D a1 = new Vector2D(3.0, 3.0);

        Vector2D p2 = new Vector2D(4.0, 4.0);
        Vector2D v2 = new Vector2D(5.0, 5.0);
        Vector2D a2 = new Vector2D(6.0, 6.0);

        Vector2D p3 = new Vector2D(7.0, 7.0);
        Vector2D v3 = new Vector2D(8.0, 8.0);
        Vector2D a3 = new Vector2D(9.0, 9.0);

        storage.addState(p1, v1, a1);
        storage.addState(p2, v2, a2);
        storage.addState(p3, v3, a3);

        assertEquals(2, storage.currentSize());
        assertEquals(List.of(p2, p3), storage.getPositionHistory());
        assertEquals(List.of(v2, v3), storage.getVelocityHistory());
        assertEquals(List.of(a2, a3), storage.getAccelerationHistory());
    }

    @Test
    void historiesKeepReferenceIdentity() {
        ParticleStorageComponent storage = new ParticleStorageComponent(2);

        Vector2D p = new Vector2D(1.0, 2.0);
        Vector2D v = new Vector2D(3.0, 4.0);
        Vector2D a = new Vector2D(5.0, 6.0);

        storage.addState(p, v, a);

        assertSame(p, storage.getPositionHistory().get(0));
        assertSame(v, storage.getVelocityHistory().get(0));
        assertSame(a, storage.getAccelerationHistory().get(0));
    }
}
