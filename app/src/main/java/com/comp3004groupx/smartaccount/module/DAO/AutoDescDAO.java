package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.comp3004groupx.smartaccount.Core.Transaction;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class AutoDescDAO {
    AutoDescDBHelper autoDescDBHelper = null;
    public AutoDescDAO(Context context){
        autoDescDBHelper = new AutoDescDBHelper(context);
    }
    public boolean addAutoDesc(Transaction transaction){
        boolean flag = false;
        String sqlquery = "INSERT INTO AUTODESC VALUES(AMOUNT,DATE,PUCHASETYPE,ACCOUNT,NOTE) VALUES (?,?,?,?,?)";
        SQLiteDatabase database = null;
        try{
            database = autoDescDBHelper.getWritableDatabase();
            database.execSQL(sqlquery ,new Object[]{transaction.getAmount(),transaction.getDate(),transaction.getType(),transaction.getAccount(),transaction.getNote()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
        return flag;
    }

}
