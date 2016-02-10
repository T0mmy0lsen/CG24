package com.example.tommy.cg24;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.example.tommy.cg24.Database.Database;
import com.example.tommy.cg24.Logic.JSON;
import com.example.tommy.cg24.Logic.URLs;
import com.example.tommy.cg24.Models.ModelChampion;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends AppCompatActivity{

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        database = new Database(getApplicationContext());
        final Intent player = new Intent(this, Player.class);
        if (database.getChampionsCount() < 50){
            getChampions();
            startActivity(player);
        } else {
            startActivity(player);
        }
    }

    public void getChampions(){
        new AsyncTask<Void, Void, List<ModelChampion>>(){
            @Override
            protected List<ModelChampion> doInBackground(Void... params) {
                List<ModelChampion> list = new ArrayList<>();
                JSON json = new JSON();
                try {
                    list.addAll(json.getChampionListfromJSON(json.getJSONfromURL(new URLs().urlChampions())));
                } catch (JSONException | IOException e) {
                    // error
                }
                return list;
            }
            @Override
            protected void onPostExecute(List<ModelChampion> list) {
                for (int i = 0; i < list.size(); i++){
                    database.addChamp(list.get(i));
                }
            }
        }.execute();
    }
}
