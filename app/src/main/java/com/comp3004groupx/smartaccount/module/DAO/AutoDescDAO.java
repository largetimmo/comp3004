package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.comp3004groupx.smartaccount.Core.Transaction;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class AutoDescDAO {
    DBHelper dbHelper;
    SQLiteDatabase database;
    public AutoDescDAO(Context context){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public boolean addAutoDesc(Transaction transaction){
        boolean flag = false;
        String sqlquery = "INSERT INTO AUTODESC VALUES(AMOUNT,DATE,PUCHASETYPE,ACCOUNT,NOTE) VALUES (?,?,?,?,?)";
        try{
            database.execSQL(sqlquery ,new Object[]{transaction.getAmount(),transaction.getDate(),transaction.getType(),transaction.getAccount(),transaction.getNote()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }   database.close();
        return flag;
    }

}
