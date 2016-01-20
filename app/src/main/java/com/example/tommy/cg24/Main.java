package com.example.tommy.cg24;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.tommy.cg24.API.JSONObjectAPI;
import com.example.tommy.cg24.Database.DatabaseChampions;
import com.example.tommy.cg24.Interface.InterfaceChampions;
import com.example.tommy.cg24.Models.ModelChampion;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Iterator;

public class Main extends AppCompatActivity implements InterfaceChampions{

    public static String key = "a7c084f1-8ec6-4e3e-840b-085cbde654a1";
    JSONObject dataObject = new JSONObject();
    JSONObject tempObject = new JSONObject();
    JSONObjectAPI jsonObjectAPI = new JSONObjectAPI();
    DatabaseChampions databaseChampions;

    String championURL = "https://global.api.pvp.net/api/lol/static-data/euw/v1.2/champion?api_key=" + key;
    private JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        databaseChampions = new DatabaseChampions(getApplicationContext());

        if (databaseChampions.getChampionsCount() < 50){
            downloadChampions(championURL);
        } else {
            final Intent player = new Intent(this, Player.class);
            startActivity(player);
        }
    }

    private void downloadChampions(String url){
        jsonObjectAPI.delegate = this;
        jsonObjectAPI.execute(url);
        TextView textView = (TextView) findViewById(R.id.main_download);
        textView.setText("Downloading...");
    }

    @Override
    public void processFinish(JSONObject jsonObject) throws JSONException {
        dataObject = jsonObject.optJSONObject("data");
        Iterator<String> keys = dataObject.keys();
        while (keys.hasNext()) {
            String name = keys.next();
            tempObject = dataObject.optJSONObject(name);
            int id = tempObject.getInt("id");
            databaseChampions.addChamp(new ModelChampion(id,name));
        }

        final Intent player = new Intent(this, Player.class);
        startActivity(player);
    }
}
