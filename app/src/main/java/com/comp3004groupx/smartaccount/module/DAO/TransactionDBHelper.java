package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class TransactionDBHelper extends SQLiteOpenHelper {
    private static final String NAME = "TransactionDBHelper";
    private static final int VERSION = 1;
    private static final String DBNAME = "TRANSACTION.db";

    public TransactionDBHelper(Context context){
        super(context,DBNAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery = "CREATE TABLE IF NOT EXIST TRANSACTION(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, DATE DATE, BALANCE REAL, ACCOUNT VARCHAR(20), NOTE VARCHAR(200), TYPE VARCHAR(20)";
        db.execSQL(sqlquery);
        Log.i(NAME,"on create");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
