package com.example.tommy.cg24.Models;

public class ModelPlayer {

    private String _summonname,_mostplayed, _region;
    private int  _id;

    public ModelPlayer(int id, String summonname, String region, String mostplayed) {
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

    public String getMostplayed() {
        return _mostplayed;
    }

    public int getId() {
        return _id;
    }
}