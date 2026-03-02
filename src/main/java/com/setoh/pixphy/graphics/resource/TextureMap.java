package com.setoh.pixphy.graphics.resource;

import java.util.HashMap;
import java.util.Map;

public final class TextureMap{

    public static final String ASTEROID_TEXTURE = "textures/asteroid.png";
    public static final String BACKGROUND_TEXTURE = "textures/space_background.png";
    public static final String MARKER_TEXTURE = "textures/marker2.png";
    public static final String HDASH_TEXTURE = "textures/horizontal_dashes.png";
    public static final String VDASH_TEXTURE = "textures/vertical_dashes.png";
    private Map<String, Texture> textures = new HashMap<>();

    public TextureMap(){
        textures.put(ASTEROID_TEXTURE, new Texture(ASTEROID_TEXTURE));
        textures.put(BACKGROUND_TEXTURE, new Texture(BACKGROUND_TEXTURE));
        textures.put(MARKER_TEXTURE, new Texture(MARKER_TEXTURE));
        textures.put(HDASH_TEXTURE, new Texture(HDASH_TEXTURE));
        textures.put(VDASH_TEXTURE, new Texture(VDASH_TEXTURE));
    }

    public TextureMap(Map<String, Texture> textureMap){
        this.textures = textureMap;    
    }

    public Texture get(String key){
        return textures.get(key);
    }

    public void put(String key, Texture texture){
        textures.put(key, texture);
    }
}
