package com.example.tommy.cg24.Models;

public class ModelChampion {
    private String _name;
    private int _id;

    public ModelChampion(int id, String name) {
        _id = id;
        _name = name;
    }

    public int getId(){
        return _id;
    }

    public String getName(){
        return _name;
    }
}
