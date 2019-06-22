package com.example.unipiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by teogomes on 01/12/2017.
 */

public class DBhelper2 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DEP";
    public String TABLE_NAME = "MEMBERS";


    public DBhelper2(Context context) {
        super(context, DATABASE_NAME, null  , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,PHONE TEXT,EMAIL TEXT,GRADE TEXT,OFFICE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public  void deleteall(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.execSQL("delete from "+ TABLE_NAME);
        db.close();
    }

    public void addMember(String name, String phone, String email, String grade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("NAME",name);
        values.put("PHONE",phone);
        values.put("EMAIL",email);
        values.put("GRADE",grade);
        db.insert(TABLE_NAME,null,values);
        db.close();

    }



    public Cursor searchMember(String query, String search){
        SQLiteDatabase db = this.getWritableDatabase();
        search=search.toUpperCase();
        Cursor data = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+query+" LIKE \""+search+"\"",null,null);
        Log.d("TEST","SELECT * FROM "+TABLE_NAME+" WHERE "+"\""+query+" LIKE \""+search+"\"");
        return data;

    }
    public Cursor searchMemberEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE EMAIL=\""+email+"\"",null,null);
        Log.d("TEST","SELECT * FROM "+TABLE_NAME+" WHERE EMAIL=\""+email+"\"");
        return data;

    }

    public void deleteRowbyName(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"EMAIL=?",new String[]{email});

    }

    public void updatePhonebyEmail(String email,String phone){
        SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("UPDATE "+TABLE_NAME+" SET PHONE=\""+phone+"\""+" WHERE EMAIL=\""+email+"\"");
         db.close();
    }
}
