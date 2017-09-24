package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class PurchaseTypeDBHelper extends SQLiteOpenHelper {
    private static final String NAME = "PurchaseTypeDBHelper";
    private static final int VERSION = 1;
    private static final String DBNAME = "PURCHASETYPE.db";

    public PurchaseTypeDBHelper(Context context){
        super(context,DBNAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery = "CREATE TABLE IF NOT EXIST PURCHASETYPE(NAME VARCHAR(20) UNIQUE)";
        db.execSQL(sqlquery);
        sqlquery  = "INSERT INTO PURCHASETYPE VALUES('Utility')";
        db.execSQL(sqlquery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
