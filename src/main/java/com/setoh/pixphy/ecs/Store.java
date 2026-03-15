package com.setoh.pixphy.ecs;

import java.util.HashMap;
import java.util.Map;

public class Store {
    
    public static final String MOUSE_POSITION = "mouse position";

    private final Map<String, Object> storeMap;

    public Store(){
        storeMap = new HashMap<>();
    }

    public void set(String key, Object obj){
        storeMap.put(key, obj);
    }

    public Object get(String key){
        return storeMap.get(key);
    }
}
