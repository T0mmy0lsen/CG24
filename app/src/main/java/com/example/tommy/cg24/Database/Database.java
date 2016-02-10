package com.example.tommy.cg24.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tommy.cg24.Models.ModelChampion;
import com.example.tommy.cg24.Models.ModelPlayer;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "cg24.db";

    //Tables
    private static final String TABLE_CHAMPION = "t_champion", TABLE_PLAYER = "t_player";

    //Columns
    private static final String C_NAME = "name",
                                C_ID = "id",
                                C_REGION = "region",
                                C_MOST = "most",
                                C_KEY = "key",
                                C_TITLE = "title";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHAMPION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_player = "CREATE TABLE " + TABLE_PLAYER + "(" +
                C_ID + " INTEGER, " +
                C_NAME + " TEXT, " +
                C_REGION + " TEXT, " +
                C_MOST + " TEXT " +
                ");";
        db.execSQL(query_player);
        String query_champ = "CREATE TABLE " + TABLE_CHAMPION + "(" +
                C_ID + " INTEGER, " +
                C_KEY + " TEXT, " +
                C_NAME + " TEXT, " +
                C_TITLE + " TEXT " +
                ");";
        db.execSQL(query_champ);
    }

    //---------------------------------------------------------------------
    //CHAMPION
    public void addChamp(ModelChampion champ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(C_ID, champ.getId());
        contentValues.put(C_KEY, champ.getName());
        contentValues.put(C_NAME, champ.getName());
        contentValues.put(C_TITLE, champ.getName());

        db.insert(TABLE_CHAMPION, null, contentValues);
        db.close();
    }

    public String getChampion(int i){
        String championKey;
        SQLiteDatabase db = getWritableDatabase();

        try{
            Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CHAMPION + " WHERE id=" + i,null);
            c.moveToFirst();
            championKey = c.getString(1);
            c.close();
        } catch (CursorIndexOutOfBoundsException e){
            championKey = "ahri";
        }

        db.close();
        return championKey;
    }

    public int getChampionsCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_CHAMPION, null);
        int count = c.getCount();
        db.close();
        c.close();

        return count;
    }
    //---------------------------------------------------------------------

    //---------------------------------------------------------------------
    //PLAYER
    public void addUser(ModelPlayer player) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(C_NAME, player.getSummonname());
        contentValues.put(C_REGION, player.getRegion());
        contentValues.put(C_MOST, player.getMostplayed());
        contentValues.put(C_ID, player.getId());

        db.insert(TABLE_PLAYER, null, contentValues);
        db.close();
    }

    public List<ModelPlayer> getAllPlayers(){
        List<ModelPlayer> players = new ArrayList<ModelPlayer>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PLAYER, null);

        if (c.moveToFirst()) {
            do {
                players.add(new ModelPlayer(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2),  c.getString(3)));
            } while (c.moveToNext());
        }

        db.close();
        c.close();
        return players;
    }

    public int getUserCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PLAYER, null);
        int count = c.getCount();
        db.close();
        c.close();

        return count;
    }

    public void deleteUser(int i) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PLAYER, C_ID + "=" + i, null);
        db.close();
    }

    public ModelPlayer getPlayer(int i){
        ModelPlayer getPlayer;
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_PLAYER, null);
        c.moveToPosition(i);

        getPlayer = new ModelPlayer(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3));

        db.close();
        c.close();
        return getPlayer;
    }
}

