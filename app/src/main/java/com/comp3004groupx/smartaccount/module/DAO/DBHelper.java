package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chenjunhao on 2017/9/14.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final String NAME = "DBHelper";
    private static final String DB_NAME = "SmartAccountDatabase.db";
    private static final int VERSION = 1;
    protected DBHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(NAME,"On Create");
        String sqlquery = "CREATE TABLE IF NOT EXISTS ACCOUNTTYPE( NAME VARCHAR(20) UNIQUE,ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT)";
        db.execSQL(sqlquery);
        sqlquery = "CREATE TABLE IF NOT EXISTS ACCOUNT(NAME VARCHAR(20) UNIQUE ,BALANCE REAL,TYPE INTEGER,ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, REALAMOUNT REAL, CONSTRAINT ACCOUNT_ACCOUNTTYPE_ID_fk FOREIGN KEY (TYPE) REFERENCES ACCOUNTTYPE (ID) ON DELETE CASCADE ON UPDATE CASCADE);";
        db.execSQL(sqlquery);
        sqlquery = "INSERT INTO ACCOUNTTYPE(NAME) VALUES(?)";
        String[] types = {"Cash","Chequing","Saving","Credit Card"};
        try {
            for (String s : types){
                db.execSQL(sqlquery,new Object[]{s});
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        sqlquery = "CREATE TABLE IF NOT EXISTS PURCHASETYPE(NAME VARCHAR(20),ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,INCOME INTEGER NOT NULL)";
        db.execSQL(sqlquery);
        sqlquery = "CREATE TABLE IF NOT EXISTS TRANS(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, DATE TEXT, BALANCE REAL, ACCOUNT INTEGER, NOTE VARCHAR(200), TYPE INTEGER,CONSTRAINT TRANS_ACCOUNT_ID_fk FOREIGN KEY (ACCOUNT) REFERENCES ACCOUNT (ID) ON DELETE CASCADE ON UPDATE CASCADE,CONSTRAINT TRANS_PURCHASETYPE_ID_fk FOREIGN KEY (TYPE) REFERENCES PURCHASETYPE (ID) ON DELETE CASCADE ON UPDATE CASCADE)";
        db.execSQL(sqlquery);
        sqlquery  = "INSERT INTO PURCHASETYPE(NAME,INCOME) VALUES('Utility',0)";
        try {
            db.execSQL(sqlquery);
        }catch (Exception e){
            e.printStackTrace();
        }
        sqlquery = "CREATE TABLE IF NOT EXISTS PAP(ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, DATE DATE, BALANCE REAL, ACCOUNT INTEGER, NOTE VARCHAR(200), TYPE INTEGER,CONSTRAINT TRANS_ACCOUNT_ID_fk FOREIGN KEY (ACCOUNT) REFERENCES ACCOUNT (ID) ON DELETE CASCADE ON UPDATE CASCADE,CONSTRAINT TRANS_PURCHASETYPE_ID_fk FOREIGN KEY (TYPE) REFERENCES PURCHASETYPE (ID) ON DELETE CASCADE ON UPDATE CASCADE)";
        db.execSQL(sqlquery);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(NAME, "WE dont support this");
    }
}
