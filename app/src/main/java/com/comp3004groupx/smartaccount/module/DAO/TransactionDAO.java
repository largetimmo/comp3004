package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class TransactionDAO {
    TransactionDBHelper transactionDBHelper = null;
    public TransactionDAO(Context context){
        transactionDBHelper = new TransactionDBHelper(context);
    }
    public List<Transaction> getAllTransaction(){
        List<Transaction> allTrans = new ArrayList<>();
        String sqlquery = "SELECT * FROM TRANSACTION ORDER BY ID ASC";
        SQLiteDatabase database = null;
        try{
            database = transactionDBHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                int accountid = cursor.getInt(cursor.getColumnIndex("ACCOUNTID"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
        return allTrans;
    }
    public boolean addTrans(Transaction transaction){
        Boolean flag = false;
        return flag;
    }
    public boolean modifyTranse(Transaction transaction){
        Boolean flag = false;
        return flag;
    }
    public boolean removeTrans(Transaction transaction){
        Boolean flag = false;
        return flag;
    }
}
