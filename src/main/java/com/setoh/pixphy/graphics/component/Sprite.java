package com.setoh.pixphy.graphics.component;

import com.setoh.pixphy.ecs.Component;

public class Sprite implements Component{
    private int x;
    private int y;
    private String textureName;

    public Sprite(int x, int y, String textureName){
        this.x = x;
        this.y = y;
        this.textureName = textureName;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getTextureName(){
        return textureName;
    }

    public void setX(double x) {
        this.x = (int)x;
    }

    public void setY(double y) {
        this.y = (int)y;
    }
}
