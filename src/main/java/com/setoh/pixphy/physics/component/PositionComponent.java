package com.setoh.pixphy.physics.component;

import com.setoh.pixphy.ecs.Component;

public class PositionComponent implements Component {

    private Position position;

    public PositionComponent(double x, double y) {
        this.position = new Position(x,y);
    }

    public Position getPosition() {
        return position;
        
    }

    public double getX(){
        return position.x();
    }

    public double getY(){
        return position.y();
    }

    public void setX(double x){
        position.setX(x);
    }

    public void setY(double y){
        position.setY(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionComponent that = (PositionComponent) o;
        return position.equals(that.position);
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }
}

