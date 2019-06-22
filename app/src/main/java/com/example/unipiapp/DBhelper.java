package com.example.unipiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LOGIN";
    public String TABLE_NAME = "USERS";


    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null  , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT ,EMAIL TEXT,PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public  void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

    public void addUser(String name, String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME",name);
        values.put("EMAIL",email);
        values.put("PASSWORD",pass);
        db.insert(TABLE_NAME,null,values);
        db.close();

    }


    //not usable
    public Cursor login(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE EMAIL=\""+email+"\"",null,null);
        return data;

    }

    public void deleteRowbyName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"NAME=?",new String[]{name});

    }
}
