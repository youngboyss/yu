package com.example.toutiao.model;

import java.util.HashMap;

public class ViewObject {
    private HashMap<String,Object> objs=new HashMap<String,Object>();

    public void set(String key,Object value)
    {
        objs.put(key,value);
    }

    public Object get(String key)
    {
        return objs.get(key);
    }
}
