package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;

import com.comp3004groupx.smartaccount.Core.Account;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/9/14.
 */

public class AccountDAO extends AbstractDAO{
    public AccountDAO(Context context){
        super(context);
        dbname = "ACCOUNT";
    }
    public ArrayList<Account> getAllAccount(){
        ArrayList<Account> allaccount = new ArrayList<>();
        String sqlquery = "SELECT * FROM ACCOUNT";
        try{
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                allaccount.add(parseAccount(cursor));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allaccount;
    }

    public Account getAccount(String name){
        String sqlquery = "SELECT * FROM ACCOUNT WHERE NAME = ?";
        try {
            Cursor cursor = database.rawQuery(sqlquery,new String[]{name});
            if(cursor.moveToNext()){
                return parseAccount(cursor);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccount(int id){
        String sqlquery = "SELECT * FROM ACCOUNT WHERE ID = ?";
        try {
            Cursor cursor = database.rawQuery(sqlquery,new String[]{Integer.toString(id)});
            if (cursor.moveToNext()){
                return parseAccount(cursor);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean addAccount(Account account) {
        boolean flag = false;
        String sqlquery = "INSERT INTO ACCOUNT (NAME,TYPE,BALANCE,REALAMOUNT) VALUES(?,?,?,?)";
        try {
            database.execSQL(sqlquery,new Object[]{account.getName(),account.getType(),account.getBalance(),account.getRealAmount()});
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    public boolean updateAccount(Account account){
        boolean flag = false;
        String sqlquery = "UPDATE ACCOUNT SET NAME = ? ,TYPE = ? ,BALANCE = ? ,REALAMOUNT = ? WHERE ID = ?";
        try {
            database.execSQL(sqlquery,new Object[]{account.getName(),account.getType(),account.getBalance(),account.getRealAmount(),account.getID()});
            flag = true;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    public boolean removeAccount(int id){
        boolean flag = false;
        String sqlquery = "DELETE FROM ACCOUNT WHERE ID = ?";
        try {
            database.execSQL(sqlquery,new Object[]{id});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    private Account parseAccount(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        String type = cursor.getString(cursor.getColumnIndex("TYPE"));
        double balance = cursor.getDouble(cursor.getColumnIndex("BALANCE"));
        double realAmount = cursor.getDouble(cursor.getColumnIndex("REALAMOUNT"));
        Account account = new Account(id,name,type,balance,realAmount);
        return account;
    }
}
