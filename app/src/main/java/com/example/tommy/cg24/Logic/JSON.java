package com.example.tommy.cg24.Logic;

import com.example.tommy.cg24.Models.ModelChampion;
import com.example.tommy.cg24.Models.Sort;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSON {

    List<Sort<Integer, Integer>> list = new ArrayList<Sort<Integer, Integer>>();
    int position_most_played = 0, pre_number_played = 0, number_played = 0;
    String name;

    public JSONObject getJSONfromURL(String url) throws IOException, JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        URL getURL = new URL(url);
        HttpURLConnection urlC = (HttpURLConnection) getURL.openConnection();
        BufferedReader jsonReader = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
        String jsonLine;

        while ((jsonLine = jsonReader.readLine()) != null) {
            stringBuilder.append(jsonLine);
        }

        return new JSONObject(stringBuilder.toString());
    }

    public List<ModelChampion> getChampionListfromJSON(JSONObject jsonObject) throws JSONException {
        JSONObject dataObject = jsonObject.optJSONObject("data");
        List<ModelChampion> list = new ArrayList<>();
        Iterator<String> keys = dataObject.keys();
        while (keys.hasNext()) {
            JSONObject temp = dataObject.optJSONObject(keys.next());
            int id = temp.optInt("id");
            String key = temp.optString("key");
            String name = temp.optString("name").toLowerCase();
            String title = temp.optString("title");
            list.add(new ModelChampion(id,key,name,title));
        }
        return list;
    }

    public int getPlayerIDfromJSON(JSONObject jsonObject) throws JSONException {
        Iterator<String> keys = jsonObject.keys();
        if( keys.hasNext() ){
            name = keys.next();
        }
        return new JSONObject(jsonObject.optString(name)).optInt("id");
    }

    public int getMostPlayedfromJSON(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.optJSONArray("champions");
        for (int i = 0; i < jsonArray.length(); i++) {
            int id = jsonArray.optJSONObject(i).getInt("id");
            int mostplayed = jsonArray.optJSONObject(i).getJSONObject("stats").getInt("totalSessionsPlayed");
            list.add(new Sort<>(mostplayed, id));
        }

        for (int i = 0; i < list.size(); i++) {
            if (!(list.get(i).getB() == 0)) {
                number_played = list.get(i).getA();
                if (number_played > pre_number_played) {
                    pre_number_played = number_played;
                    position_most_played = i;
                }
            }
        }
        return list.get(position_most_played).getB();
    }
}
