package com.example.borapaulco2mobileapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBUtils extends SQLiteOpenHelper {
    public static final String dbName = "Accounts.db";

    public DBUtils(Context context) {
        super(context, dbName, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase myDb) {
        myDb.execSQL("create Table accounts(email TEXT primary key, password TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDb, int oldVersion, int newVersion) {
        myDb.execSQL("drop Table if exists  accounts");
    }

    public Boolean insertDataIntoDb(String email, String password){
        SQLiteDatabase accountsDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        long insertValues = accountsDb.insert("accounts",null, contentValues);
        if (insertValues < 0){
             return false;
        }
        return true;
    }

    public Boolean checkIfUserExists(String email){
        SQLiteDatabase accountsDb = this.getWritableDatabase();
        Cursor cursor = accountsDb.rawQuery("Select * from accounts where email = ?", new String[]{email});
        if (cursor.getCount() > 0){
            return true;
        }
        return false;
    }

    public Boolean loginCheck(String email, String password){
        SQLiteDatabase accountsDb = this.getWritableDatabase();
        Cursor cursor = accountsDb.rawQuery("Select * from accounts where email = ? and password = ? ", new String[] {email, password});
        if (cursor.getCount() > 0){
            return true;
        }
        return false;
    }
}
