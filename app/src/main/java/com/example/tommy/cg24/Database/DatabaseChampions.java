package com.example.tommy.cg24.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tommy.cg24.Models.ModelChampion;

public class DatabaseChampions extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cg24.db",
            TABLE = "champions",
            C_ID = "_id",
            C_NAME = "_name";

    public DatabaseChampions(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE + "(" +
                C_ID + " INTEGER, " +
                C_NAME + " TEXT " +
                ");";
        db.execSQL(query);
    }

    public void addChamp(ModelChampion champ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(C_ID, champ.getId());
        contentValues.put(C_NAME, champ.getName());

        db.insert(TABLE, null, contentValues);
        db.close();
    }

    public String getChampion(int i){
        String championName;
        SQLiteDatabase db = getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE + " WHERE _id=" + i,null);
        c.moveToFirst();

        championName = c.getString(1);

        db.close();
        c.close();
        return championName;
    }

    public int getChampionsCount(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE, null);
        int count = c.getCount();
        db.close();
        c.close();

        return count;
    }
}
