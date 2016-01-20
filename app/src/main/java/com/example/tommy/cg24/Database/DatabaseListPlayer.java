package com.example.tommy.cg24.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.tommy.cg24.Models.ModelListPlayer;

import java.util.ArrayList;
import java.util.List;

public class DatabaseListPlayer extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cg24.db",
            TABLE_USERS = "playerlist",
            C_SUMMONNAME = "_summonname",
            C_SUMID = "_id",
            C_REGION = "_region",
            C_MOST = "_mostplayed";

    public DatabaseListPlayer(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USERS + "(" +
                C_SUMMONNAME + " TEXT, " +
                C_REGION + " TEXT, " +
                C_MOST + " INTEGER, " +
                C_SUMID + " INTEGER " +
                ");";
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(ModelListPlayer player) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(C_SUMMONNAME, player.getSummonname());
        contentValues.put(C_REGION, player.getRegion());
        contentValues.put(C_MOST, player.getMostplayed());
        contentValues.put(C_SUMID, player.getId());

        db.insert(TABLE_USERS, null, contentValues);
        db.close();
    }

    public List<ModelListPlayer> getAllPlayers(){
        List<ModelListPlayer> players = new ArrayList<ModelListPlayer>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);

        if (c.moveToFirst()) {
            do {
                players.add(new ModelListPlayer(c.getString(0), c.getString(1), Integer.parseInt(c.getString(2)), Integer.parseInt(c.getString(3))));
            } while (c.moveToNext());
        }

        db.close();
        c.close();
        return players;
    }

    public int getUserCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        int count = c.getCount();
        db.close();
        c.close();

        return count;
    }

    public void deleteUser(String i) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, C_SUMID + "=" + i, null);
        db.close();
    }

    public ModelListPlayer getPlayer(int i){
        ModelListPlayer getPlayer;
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
        c.moveToPosition(i);

        getPlayer = new ModelListPlayer(c.getString(0), c.getString(1), Integer.parseInt(c.getString(2)), Integer.parseInt(c.getString(3)));

        db.close();
        c.close();
        return getPlayer;
    }
}
