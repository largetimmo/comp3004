package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;

import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class TransactionDAO extends AbstractDAO{
    public TransactionDAO(Context context){
        super(context);
    }
    public ArrayList<Transaction> getTopTrans(int num){
        String sqlquery = "SELECT * FROM TRANS ORDER BY DATE DESC LIMIT ?";
        return acquireData(sqlquery,new String[]{Integer.toString(num)});
    }
    public ArrayList<Transaction> getAllTransThisMonth(int year, int month){
        return acquireData("SELECT * FROM TRANS WHERE DATE LIKE ?-?% ORDER BY ID",new String[]{Integer.toString(year),Integer.toString(month)});
    }
    public ArrayList<Transaction> getOneDayTrans(int year, int month, int day){
        return acquireData("SELECT * FROM TRANS WHERE DATE = ?-?-? ORDER BY ID",new String[]{Integer.toString(year),Integer.toString(month),Integer.toString(day)});
    }
    public ArrayList<Transaction> getAllTransaction(){
        String sqlquery = "SELECT * FROM TRANS ORDER BY ID ASC";
        return acquireData(sqlquery,null);
    }
    public boolean addTrans(Transaction transaction){
        Boolean flag = false;
        String sqlquery = "INSERT INTO TRANS(DATE,BALANCE,ACCOUNT,NOTE,TYPE) VALUES(?,?,?,?,?)";
        try{
            database.execSQL(sqlquery,new Object[]{transaction.getDate(),transaction.getAmount(),transaction.getAccount(),transaction.getNote(),transaction.getType()});
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public boolean modifyTrans(Transaction transaction){
        Boolean flag = false;
        String sqlquery = "UPDATE TRANS SET DATE = ?, BALANCE = ?, ACCOUNT =?, NOTE = ? TYPE = ? WHERE ID = ? ";
        try{
            database.execSQL(sqlquery, new Object[]{transaction.getDate(),transaction.getAmount(),transaction.getNote(),transaction.getType(),transaction.getId()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public boolean removeTrans(int id){
        Boolean flag = false;
        String sqlquery = "DELETE FROM TRANS WHERE ID = ?";
        try {
            database.execSQL(sqlquery,new Object[]{id});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    private ArrayList<Transaction> acquireData(String sqlquery, String[] paras){
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(sqlquery,paras);
            while (cursor.moveToNext()){
                transactions.add(parseTrans(cursor));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }
    private Transaction parseTrans(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String account = cursor.getString(cursor.getColumnIndex("ACCOUNT"));
        String date_string = cursor.getString(cursor.getColumnIndex("DATE"));
        Date date = new Date(date_string);
        Double balance = cursor.getDouble(cursor.getColumnIndex("BALANCE"));
        String note = cursor.getString(cursor.getColumnIndex("NOTE"));
        String type = cursor.getString(cursor.getColumnIndex("TYPE"));
        Transaction transaction = new Transaction(id,date,balance,account,note,type);
        return transaction;
    }
}
