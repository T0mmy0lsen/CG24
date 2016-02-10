package com.example.tommy.cg24.Logic;

public class URLs {
    public String key = "a7c084f1-8ec6-4e3e-840b-085cbde654a1";

    public String urlPlayer(String region, String username) {
        return "https://euw.api.pvp.net/api/lol/" + region + "/v1.4/summoner/by-name/" + username.replaceAll(" ", "%20") + "?api_key=" + key;
    }

    public String urlStats(String region, int id){
        return "https://" + region + ".api.pvp.net/api/lol/" + region + "/v1.3/stats/by-summoner/" + id + "/ranked?season=SEASON2015&api_key=" + key;
    }

    public String urlChampions(){
        return "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion?api_key=" + key;
    }
}
