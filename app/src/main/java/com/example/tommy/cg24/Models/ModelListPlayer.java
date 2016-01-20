package com.example.tommy.cg24.Models;

public class ModelListPlayer {

    private String _summonname, _region;
    private int _mostplayed, _id;

    public ModelListPlayer(String summonname, String region, int mostplayed, int id) {
        _summonname = summonname;
        _region = region;
        _mostplayed = mostplayed;
        _id = id;
    }

    public String getSummonname() {
        return _summonname; }

    public String getRegion(){
        return _region;
    }

    public int getMostplayed() {
        return _mostplayed;
    }

    public int getId() {
        return _id;
    }
}