package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class TransactionDAO {
    DBHelper dbHelper;
    SQLiteDatabase database;
    public TransactionDAO(Context context){
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public List<Transaction> getAllTransaction(){
        List<Transaction> allTrans = new ArrayList<>();
        String sqlquery = "SELECT * FROM TRANSACTION ORDER BY ID ASC";
        try{
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String account = cursor.getString(cursor.getColumnIndex("ACCOUNT"));
                String date_string = cursor.getString(cursor.getColumnIndex("DATE"));
                Date date = new Date(DateFormat.getInstance().parse(date_string).getTime());
                Double balance = cursor.getDouble(cursor.getColumnIndex("BALANCE"));
                String note = cursor.getString(cursor.getColumnIndex("NOTE"));
                String type = cursor.getString(cursor.getColumnIndex("TYPE"));
                Transaction transaction = new Transaction(id,date,balance,account,note,type);
                allTrans.add(transaction);
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
        String sqlquery = "INSERT INTO TRANSACTION(DATE,BALANCE,ACCOUNT,NOTE,TYPE) VALUES(?,?,?,?,?)";
        try{
            database.execSQL(sqlquery,new Object[]{transaction.getDate(),transaction.getAmount(),transaction.getAccount(),transaction.getNote(),transaction.getType()});
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
        return flag;
    }
    public boolean modifyTrans(Transaction transaction){
        Boolean flag = false;
        String sqlquery = "UPDATE TRANSACTION SET DATE = ?, BALANCE = ?, ACCOUNT =?, NOTE = ? TYPE = ? WHERE ID = ? ";
        try{
            database.execSQL(sqlquery, new Object[]{transaction.getDate(),transaction.getAmount(),transaction.getNote(),transaction.getType(),transaction.getId()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
        return flag;
    }
    public boolean removeTrans(int id){
        Boolean flag = false;
        String sqlquery = "DELETE FROM TRANSACTION WHERE ID = ?";
        try {
            database.execSQL(sqlquery,new Object[]{id});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
        return flag;
    }
}
