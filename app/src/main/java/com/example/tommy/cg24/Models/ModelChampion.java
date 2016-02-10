package com.example.tommy.cg24.Models;

public class ModelChampion {
    private String _name, _key, _title;
    private int _id;

    public ModelChampion(int id, String key, String name, String title) {
        _id = id;
        _key = key;
        _name = name;
        _title = title;
    }

    public int getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }

    public String getKey(){
        return _key;
    }

    public String getTitle(){
        return _title;
    }
}
