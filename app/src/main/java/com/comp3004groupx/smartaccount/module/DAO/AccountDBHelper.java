package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.comp3004groupx.smartaccount.Core.Account;

/**
 * Created by chenjunhao on 2017/9/14.
 */

public class AccountDBHelper extends SQLiteOpenHelper {
    private final String NAME = "AccountDBHelper";
    private static final String DB_NAME = "ACCOUNT.db";
    private static final int VERSION = 1;
    public AccountDBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(NAME,"On Create");
        String sqlquery = "CREATE TABLE IF NOT EXIST ACCOUNT(" +
                "NAME VARCHAR(20)," +
                "BALANCE REAL," +
                "TYPE INTEGER," +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT)";
        db.execSQL(sqlquery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(NAME, "WE dont support this");
    }
}
