package com.setoh.pixphy.physics.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.setoh.pixphy.ecs.Component;

public class ParticleStorageComponent implements Component {

    private final Vector2D[] positionBuffer;
    private final Vector2D[] velocityBuffer;
    private final Vector2D[] accelerationBuffer;

    private int currentIndex = -1;
    private int currentSize = 0;
    private int maxSize = 0;

    public ParticleStorageComponent(int bufferSize) {
        if (bufferSize <= 0) {
            throw new IllegalArgumentException("bufferSize must be > 0");
        }
        maxSize = bufferSize;
        this.positionBuffer = new Vector2D[bufferSize];
        this.velocityBuffer = new Vector2D[bufferSize];
        this.accelerationBuffer = new Vector2D[bufferSize];
    }

    public void addState(Vector2D position, Vector2D velocity, Vector2D acceleration) {
        currentIndex = (currentIndex+1) % maxSize;
        if (currentSize < maxSize) {
            currentSize++;
        }
        positionBuffer[currentIndex] = new Vector2D(position);
        velocityBuffer[currentIndex] = new Vector2D(velocity);
        accelerationBuffer[currentIndex] = new Vector2D(acceleration);        
    }

    public int currentSize() {
        return currentSize;
    }

    public int maxSize() {
        return maxSize;
    }

    public List<Vector2D> getPositionHistory() {
        return getHistory(positionBuffer);
    }

    public List<Vector2D> getVelocityHistory() {
        return getHistory(velocityBuffer);
    }

    public List<Vector2D> getAccelerationHistory() {
        return getHistory(accelerationBuffer);
    }

    private List<Vector2D> getHistory(Vector2D[] buffer) {
        List<Vector2D> history = new ArrayList<>(currentSize);
        if(currentSize >= maxSize){
            for (int i = 0; i < currentSize; i++) {
                int index = (currentIndex + 1 + i) % maxSize;
                history.add(buffer[index]);
            }
        }
        else{
            history = Arrays.asList(Arrays.copyOf(buffer, currentSize));
        }
        return history;
    }
}
