package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.util.ArrayList;

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
        }
        return flag;
    }
    public ArrayList<Transaction> getAutoDesc(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sqlquery = "SELECT * FROM AUTODESC";
        try{
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                double amount = cursor.getDouble(cursor.getColumnIndex("AMOUNT"));
                Date date = new Date(cursor.getString(cursor.getColumnIndex("DATE")));
                String purchase_type = cursor.getString(cursor.getColumnIndex("PURCHASETYPE"));
                String account = cursor.getString(cursor.getColumnIndex("ACCOUNT"));
                String note = cursor.getString(cursor.getColumnIndex("NOTE"));
                Transaction transaction = new Transaction(id,date,amount,account,note,purchase_type);
                transactions.add(transaction);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }

}
