package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.comp3004groupx.smartaccount.Core.Account;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/9/14.
 */

public class AccountDAO extends AbstractDAO {
    public AccountDAO(Context context) {
        super(context);
        dbname = "ACCOUNT";
    }

    public ArrayList<Account> getAllAccount() {
        //done
        ArrayList<Account> allaccount = new ArrayList<>();
        String sqlquery = "SELECT ACCOUNT.ID, ACCOUNT.NAME AS NAME1, ACCOUNTTYPE.NAME AS NAME2, BALANCE,REALAMOUNT FROM ACCOUNT INNER JOIN ACCOUNTTYPE ON ACCOUNT.TYPE = ACCOUNTTYPE.ID";
        try {
            Cursor cursor = database.rawQuery(sqlquery, null);
            while (cursor.moveToNext()) {
                allaccount.add(parseAccount(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allaccount;
    }

    public Account getAccount(String name) {
        //done
        String sqlquery = "SELECT ACCOUNT.ID, ACCOUNT.NAME AS NAME1, ACCOUNTTYPE.NAME AS NAME2, BALANCE,REALAMOUNT FROM ACCOUNT INNER JOIN ACCOUNTTYPE ON ACCOUNT.TYPE = ACCOUNTTYPE.ID WHERE ACCOUNT.NAME = ?;";
        try {
            Cursor cursor = database.rawQuery(sqlquery, new String[]{name});
            if (cursor.moveToNext()) {
                return parseAccount(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account getAccount(int id) {
        //done
        String sqlquery = "SELECT ACCOUNT.ID, ACCOUNT.NAME AS NAME1, ACCOUNTTYPE.NAME AS NAME2, BALANCE,REALAMOUNT FROM ACCOUNT INNER JOIN ACCOUNTTYPE ON ACCOUNT.TYPE = ACCOUNTTYPE.ID WHERE ACCOUNT.ID = ?;";
        try {
            Cursor cursor = database.rawQuery(sqlquery, new String[]{Integer.toString(id)});
            if (cursor.moveToNext()) {
                return parseAccount(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addAccount(Account account) {
        //done
        boolean flag = false;
        String sqlquery = "INSERT INTO ACCOUNT (NAME,TYPE,BALANCE,REALAMOUNT) VALUES(?,(SELECT ID FROM ACCOUNTTYPE WHERE NAME = ?),?,?)";
        try {
            database.execSQL(sqlquery, new Object[]{account.getName(), account.getType(), account.getBalance(), account.getRealAmount()});
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean updateAccount(Account account) {
        //done
        boolean flag = false;
        String sqlquery_update_account = "UPDATE ACCOUNT SET NAME = ? ,TYPE = (SELECT ID FROM ACCOUNTTYPE WHERE NAME = ?) ,BALANCE = ? ,REALAMOUNT = ? WHERE ID = ?";
        try {
            database.execSQL(sqlquery_update_account, new Object[]{account.getName(), account.getType(), account.getBalance(), account.getRealAmount(), account.getID()});
            ;
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean removeAccount(int id) {
        //done
        boolean flag = false;
        String sqlquery_remove_account = "DELETE FROM ACCOUNT WHERE ID = ?";
        try {
            database.execSQL(sqlquery_remove_account, new Object[]{id});
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private Account parseAccount(Cursor cursor) {
        //done
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String name = cursor.getString(cursor.getColumnIndex("NAME1"));
        String type = cursor.getString(cursor.getColumnIndex("NAME2"));
        double balance = cursor.getDouble(cursor.getColumnIndex("BALANCE"));
        double realAmount = cursor.getDouble(cursor.getColumnIndex("REALAMOUNT"));
        Account account = new Account(id, name, type, balance, realAmount);
        return account;
    }
}
