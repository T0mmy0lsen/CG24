package com.example.tommy.cg24;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tommy.cg24.Database.DatabaseListPlayer;
import com.example.tommy.cg24.Models.ModelListPlayer;

import java.util.ArrayList;
import java.util.List;

public class Player extends AppCompatActivity {

    ListView playerListView;
    static DatabaseListPlayer databaseListPlayer;
    static ArrayAdapter<ModelListPlayer> playerAdapter;
    static List<ModelListPlayer> playerList = new ArrayList<ModelListPlayer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(120);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        databaseListPlayer = new DatabaseListPlayer(getApplicationContext());

        playerListView = (ListView) findViewById(R.id.list_players);

        if(databaseListPlayer.getUserCount() != 0){
            playerList.addAll(databaseListPlayer.getAllPlayers());
        }

        updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(Player.this, Add.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateList(){
        playerAdapter = new PlayerListAdapter();
        playerListView.setAdapter(playerAdapter);
    }

    private class PlayerListAdapter extends ArrayAdapter<ModelListPlayer> {
        public PlayerListAdapter() {
            super(Player.this, R.layout.player_list, playerList);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null){
                view = getLayoutInflater().inflate(R.layout.player_list, parent, false);
            }

            ModelListPlayer currentPlayer = playerList.get(position);
            ImageView bg_list = (ImageView) view.findViewById(R.id.bg_list);

            try{
                bg_list.setImageURI(Uri.parse("android.resource://tommy.cg21/drawable/" + "get" + "_splash_0"));
            } catch (NullPointerException e){
                bg_list.setImageURI(Uri.parse("android.resource://tommy.cg21/drawable/teemo_splash_0"));
            }

            TextView playerName = (TextView) view.findViewById(R.id.playerName);
            playerName.setText(currentPlayer.getSummonname());

            return view;
        }
    }

    public class Add extends Activity {

        private final String[]paths = {"EU West", "EU East"};
        EditText username;
        String region;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_design);

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
                public void onNothingSelected(AdapterView<?> parent) {}
            });

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;

            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.y = (int)(width*.1);

            getWindow().setLayout((int)(width*.8), (int)(height*.3));
            getWindow().setGravity(Gravity.TOP);
            getWindow().setAttributes(params);
        }

        public void add_player(View v) throws InterruptedException {
            username = (EditText) findViewById(R.id.edit_player);
            if(!playerExists(username.getText().toString())){
                //
            }
        }

        public boolean playerExists(String thisPlayer){
            int playerCount = playerList.size();
            for (int i = 0; i < playerCount; i++) {
                if (thisPlayer.compareToIgnoreCase(playerList.get(i).getSummonname()) == 0) {
                    return true;
                }
            }
            return false;
        }
    }
}
