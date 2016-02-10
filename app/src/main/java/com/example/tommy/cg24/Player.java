package com.example.tommy.cg24;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.tommy.cg24.Database.Database;
import com.example.tommy.cg24.Logic.ViewAdapter;
import com.example.tommy.cg24.Models.ModelPlayer;

import java.util.ArrayList;
import java.util.List;

public class Player extends AppCompatActivity {

    static RecyclerView playerListView;
    public static Database database;
    static List<ModelPlayer> playerList = new ArrayList<>();
    protected PowerManager.WakeLock mWakeLock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(120);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "Tag");
        this.mWakeLock.acquire();

        database = new Database(getApplicationContext());

        playerListView = (RecyclerView) findViewById(R.id.list_player);
        playerListView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        playerListView.setLayoutManager(mLayoutManager);

        if(database.getUserCount() != 0){
            playerList.addAll(database.getAllPlayers());
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
                Intent addDialog = new Intent(this, Add.class);
                startActivity(addDialog);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void updateList(){
        playerList.clear();
        playerList.addAll(database.getAllPlayers());
        playerListView.setAdapter(new ViewAdapter(playerList));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
    }
}
