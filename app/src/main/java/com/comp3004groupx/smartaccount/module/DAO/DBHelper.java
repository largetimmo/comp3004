package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.comp3004groupx.smartaccount.Core.Account;

/**
 * Created by chenjunhao on 2017/9/14.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final String NAME = "DBHelper";
    private static final String DB_NAME = "SmartAccountDatabase.db";
    private static final int VERSION = 1;
    public DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(NAME,"On Create");
        String sqlquery = "CREATE TABLE IF NOT EXISTS ACCOUNT(NAME VARCHAR(20) UNIQUE ,BALANCE REAL,TYPE INTEGER,ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT)";
        db.execSQL(sqlquery);
        sqlquery = "CREATE TABLE IF NOT EXISTS ACCOUNTTYPE( NAME VARCHAR(20) UNIQUE)";
        db.execSQL(sqlquery);
        sqlquery = "INSERT INTO ACCOUNTTYPE VALUES(?)";
        String[] types = {"Cash","Chequing","Saving","Credit Card"};
        try {
            for (String s : types){
                db.execSQL(sqlquery,new Object[]{s});
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        sqlquery = "CREATE TABLE IF NOT EXISTS AUTODESC(ID INTEGER PRIMARY KEY AUTOINCREMENT,AMOUNT REAL, DATE DATE, PURCHASETYPE VARCHAR(20), ACCOUNT VARCHAR(20), NOTE VARCHAR(200))";
        db.execSQL(sqlquery);
        sqlquery = "CREATE TABLE IF NOT EXISTS PURCHASETYPE(NAME VARCHAR(20) UNIQUE)";
        db.execSQL(sqlquery);
        sqlquery  = "INSERT INTO PURCHASETYPE VALUES('Utility')";
        try {
            db.execSQL(sqlquery);
        }catch (Exception e){
            e.printStackTrace();
        }
        sqlquery = "CREATE TABLE IF NOT EXISTS TRANS(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, DATE DATE, BALANCE REAL, ACCOUNT VARCHAR(20), NOTE VARCHAR(200), TYPE VARCHAR(20))";
        db.execSQL(sqlquery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(NAME, "WE dont support this");
    }
}
