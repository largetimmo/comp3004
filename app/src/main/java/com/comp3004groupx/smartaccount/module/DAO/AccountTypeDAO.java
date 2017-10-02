package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class AccountTypeDAO  {
    private DBHelper DBHelper = null;
    private SQLiteDatabase database;
    public AccountTypeDAO(Context context){
        DBHelper = new DBHelper(context);
        database = DBHelper.getWritableDatabase();
    }
    public List<String> getAllType(){
        List<String> alltypes = new ArrayList<>();
        String sqlquery = "SELECT * FROM ACCOUNTTYPE";
        try{
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                alltypes.add(name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alltypes;
    }
}
