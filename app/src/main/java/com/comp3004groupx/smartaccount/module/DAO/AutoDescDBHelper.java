package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class AutoDescDBHelper  extends SQLiteOpenHelper{
    private static final String DBNAME = "AutoDesc.db";
    private static final int VERSION = 1;
    public AutoDescDBHelper(Context context){
        super(context,DBNAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery = "CREATE TABLE IF NOT EXIST AUTODESC(ID, INTEGER PRIMARY KEY UNIQUE AUTOINCREMENT,AMOUNT REAL, DATE DATE, PURCHASETYPE VARCHAR(20), ACCOUNT VARCHAR(20), NOTE VARCHAR(200))";
        db.execSQL(sqlquery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
