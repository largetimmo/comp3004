package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/10/6.
 */

public abstract class AbstractDAO {
    protected DBHelper dbHelper;
    protected SQLiteDatabase database;
    protected String dbname;
    protected Context context;
    public AbstractDAO(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public ArrayList<ArrayList<String>> GETRAWDATA(){
        ArrayList<ArrayList<String>> RAWDATA = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM "+dbname,null);
        int count = cursor.getColumnCount();
        for (int i = 0; i<count;i++){
            RAWDATA.add(new ArrayList<String>());
            RAWDATA.get(i).add(cursor.getColumnName(i));
        }
        while (cursor.moveToNext()){
            for (int i = 0; i<count; i++){
                RAWDATA.get(i).add(cursor.getString(i));
            }
        }
        return RAWDATA;
    }
}