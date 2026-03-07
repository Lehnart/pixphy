package com.setoh.pixphy.physics.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
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

        storage.addState(p1, v1, a1, 0., 1.);
        storage.addState(p2, v2, a2, 0.1, 1.);

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

        storage.addState(p1, v1, a1, 0.1, 1.);
        storage.addState(p2, v2, a2, 0.2, 1.);
        storage.addState(p3, v3, a3, 0.3, 1.);

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

        storage.addState(p, v, a, 0.1, 1.);

        assertEquals(p, storage.getPositionHistory().get(0));
        assertEquals(v, storage.getVelocityHistory().get(0));
        assertEquals(a, storage.getAccelerationHistory().get(0));
    }

    @Test
    void addStateCopiesInputVectorsDefensively() {
        ParticleStorageComponent storage = new ParticleStorageComponent(2);

        Vector2D p = new Vector2D(1.0, 2.0);
        Vector2D v = new Vector2D(3.0, 4.0);
        Vector2D a = new Vector2D(5.0, 6.0);

        storage.addState(p, v, a, 0.1, 1.);
        p.setX(100.0);
        v.setY(200.0);
        a.setX(300.0);

        assertEquals(new Vector2D(1.0, 2.0), storage.getPositionHistory().get(0));
        assertEquals(new Vector2D(3.0, 4.0), storage.getVelocityHistory().get(0));
        assertEquals(new Vector2D(5.0, 6.0), storage.getAccelerationHistory().get(0));
        assertNotSame(p, storage.getPositionHistory().get(0));
        assertNotSame(v, storage.getVelocityHistory().get(0));
        assertNotSame(a, storage.getAccelerationHistory().get(0));
    }

    @Test
    void getTimeHistoryReturnsChronologicalOrderBeforeAndAfterWrap() {
        ParticleStorageComponent storage = new ParticleStorageComponent(3);

        storage.addState(new Vector2D(1.0, 1.0), new Vector2D(1.0, 1.0), new Vector2D(1.0, 1.0), 0.1, 1.);
        storage.addState(new Vector2D(2.0, 2.0), new Vector2D(2.0, 2.0), new Vector2D(2.0, 2.0), 0.2, 1.);
        assertEquals(List.of(0.1, 0.2), storage.getTimeHistory());

        storage.addState(new Vector2D(3.0, 3.0), new Vector2D(3.0, 3.0), new Vector2D(3.0, 3.0), 0.3, 1.);
        storage.addState(new Vector2D(4.0, 4.0), new Vector2D(4.0, 4.0), new Vector2D(4.0, 4.0), 0.4, 1.);
        assertEquals(List.of(0.2, 0.3, 0.4), storage.getTimeHistory());
    }

    @Test
    void addStateAfterMultipleWrapsKeepsOnlyLatestValuesInOrder() {
        ParticleStorageComponent storage = new ParticleStorageComponent(3);

        for (int i = 1; i <= 6; i++) {
            double value = i;
            storage.addState(
                new Vector2D(value, value),
                new Vector2D(value * 10.0, value * 10.0),
                new Vector2D(value * 100.0, value * 100.0),
                value,
                1.
            );
        }

        assertEquals(3, storage.currentSize());
        assertEquals(List.of(new Vector2D(4.0, 4.0), new Vector2D(5.0, 5.0), new Vector2D(6.0, 6.0)), storage.getPositionHistory());
        assertEquals(List.of(4.0, 5.0, 6.0), storage.getTimeHistory());
    }
}
