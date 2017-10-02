package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.comp3004groupx.smartaccount.Core.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjunhao on 2017/9/14.
 */

public class AccountDAO {
    private DBHelper DBHelper;
    private SQLiteDatabase database;
    public AccountDAO(Context context){
        DBHelper = new DBHelper(context);
        database = DBHelper.getWritableDatabase();
    }

    public ArrayList<Account> getAllAccount(){
        ArrayList<Account> allaccount = new ArrayList<>();
        String sqlquery = "SELECT * FROM ACCOUNT";
        try{
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                String type = cursor.getString(cursor.getColumnIndex("TYPE"));
                double balance = cursor.getDouble(cursor.getColumnIndex("BALANCE"));
                Account account = new Account(id,name,type,balance);
                allaccount.add(account);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allaccount;
    }


    public boolean addAccount(Account account) {
        boolean flag = false;
        String sqlquery = "INSERT INTO ACCOUNT (NAME,TYPE,BALANCE) VALUES(?,?,?)";
        try {
            database.execSQL(sqlquery,new Object[]{account.getName(),account.getType(),account.getBalance()});
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    public boolean updateAccount(Account account){
        boolean flag = false;
        String sqlquery = "UPDATE ACCOUNT SET NAME = ? TYPE = ? WHERE ID = ?";
        try {
            database.execSQL(sqlquery,new Object[]{account.getName(),account.getType(),account.getID()});
            flag = true;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    public boolean removeAccount(int id){
        boolean flag = false;
        String sqlquery = "DELETE FROM ACCOUNT WHWRE ID = ?";
        try {
            database.execSQL(sqlquery,new Object[]{id});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}
