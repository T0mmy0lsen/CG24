package com.example.tommy.cg24;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.example.tommy.cg24.Database.Database;
import com.example.tommy.cg24.Logic.JSON;
import com.example.tommy.cg24.Logic.URLs;
import com.example.tommy.cg24.Models.ModelPlayer;
import org.json.JSONException;
import java.io.IOException;

public class Add extends Activity {

    private final String[]paths = {"EU West", "EU East"};
    Database database;
    JSON editJSON = new JSON();
    URLs urls = new URLs();
    EditText username;
    String usernameString;
    String region;
    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_design);

        database = new Database(getApplicationContext());

        Spinner spinner = (Spinner) findViewById(R.id.spinner_region);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Add.this, android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        region = "euw";
                        break;
                    case 1:
                        region = "eue";
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
        height = dm.heightPixels;

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.y = (int)(width*.1);

        getWindow().setLayout((int) (width * .8), (int) (height * .3));
        getWindow().setGravity(Gravity.TOP);
        getWindow().setAttributes(params);
    }

    public void addPlayer(View v) throws InterruptedException {
        username = (EditText) findViewById(R.id.edit_player);
        usernameString = username.getText().toString();
        if(!playerExists(usernameString)){
            getPlayer();
        } else {
            username.setText("");
        }
    }

    private void getPlayer() {
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... params) {
                try {
                    int id = editJSON.getPlayerIDfromJSON(
                            editJSON.getJSONfromURL(
                                    urls.urlPlayer(region, usernameString)
                            ));
                    database.addUser(new ModelPlayer(
                            id,
                            usernameString,
                            region,
                            database.getChampion(editJSON.getMostPlayedfromJSON(editJSON.getJSONfromURL(urls.urlStats(region, id))))
                    ));

                    return true;
                } catch (JSONException error){
                    return false;
                } catch (IOException e) {
                    return false;
                }
            }
            @Override
            protected void onPostExecute(Boolean bool) {
                if(bool){
                    finish();
                } else {
                    username.setText("");
                }
            }
        }.execute();
    }



    public boolean playerExists(String thisPlayer){
        int playerCount = Player.playerList.size();
        for (int i = 0; i < playerCount; i++) {
            if (thisPlayer.compareToIgnoreCase(Player.playerList.get(i).getSummonname()) == 0) {
                return true;
            }
        }
        return false;
    }
}

