package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by chenjunhao on 2017/10/6.
 */

public abstract class AbstractDAO {
    protected DBHelper dbHelper;
    protected SQLiteDatabase database;
    public AbstractDAO(Context context){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
}
