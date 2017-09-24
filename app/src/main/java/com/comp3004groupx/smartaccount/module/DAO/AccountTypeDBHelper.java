package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class AccountTypeDBHelper extends SQLiteOpenHelper {
    private static final String NAME = "AccountTypeDBHelper";
    private static final String DB_NAME = "ACCOUNTTYPE.db";
    private static final int VERSION = 1;
    public AccountTypeDBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery = "CREATE TABLE IF NOT EXIST ACCOUNTTYPE( NAME VARCHAR(20) UNIQUE)";
        db.execSQL(sqlquery);
        sqlquery = "INSERT INTO ACCOUNTTYPE VALUES(?)";
        String[] types = {"Cash","Chequing","Saving","Credit Card"};
        for (String s : types){
            db.execSQL(s,new Object[]{s});
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(NAME,"Not support this");
    }
}
