package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;

import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class AutoDescDAO extends AbstractDAO {
    public AutoDescDAO(Context context){
        super(context);
        dbname = "AUTODESC";
    }
    public boolean addAutoDesc(Transaction transaction){
        boolean flag = false;
        String sqlquery = "INSERT INTO AUTODESC (AMOUNT,DATE,PUCHASETYPE,ACCOUNT,NOTE) VALUES (?,?,?,?,?)";
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
    public boolean removeAutoDesc(int id){
        boolean flag = false;
        try {
            database.execSQL("REMOVE FROM AUTODESC WHERE ID = ?",new Object[]{Integer.toString(id)});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean modifyAutoDesc(Transaction transaction){
        boolean flag = false;
        try{
            String sqlquery = "UPDATE AUTODESC SET AMOUNT = ? ,DATE = ? ,PURCHASETYPE = ? ,ACCOUNT = ? ,NOTE = ? WHERE ID = ?";
            database.execSQL(sqlquery, new Object[]{transaction.getAmount(),transaction.getDate(),transaction.getType(),transaction.getAccount(),transaction.getNote(),transaction.getId()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }


}
