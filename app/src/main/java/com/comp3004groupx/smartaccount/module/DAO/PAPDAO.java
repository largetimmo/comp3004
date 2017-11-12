package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;

import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class PAPDAO extends AbstractDAO {
    //CHECKED =0 = Not Check yet
    //CHECKED = 1 = already checked
    private static final String BASIC_SELECT_QUERY ="SELECT CHECKED,TRANS.ID AS ID, ACCOUNT.NAME AS NAME1, TRANS.BALANCE, NOTE, PURCHASETYPE.NAME AS NAME2 FROM PAP INNER JOIN ACCOUNT ON PAP.ACCOUNT = ACCOUNT.ID INNER JOIN PURCHASETYPE ON PAP.TYPE = PURCHASETYPE.ID";
    public PAPDAO(Context context){
        super(context);
        dbname = "PAP";
    }
    public boolean addAutoDesc(Transaction transaction){
        boolean flag = false;
        String sqlquery = "INSERT INTO PAP(AMOUNT,DATE,TYPE,ACCOUNT,NOTE,CHECKED) VALUES (?,?,(SELECT ID FROM PURCHASETYPE WHERE NAME = ?),(SELECT ID FROM ACCOUNT WHERE NAME = ?),?,?)";
        try{
            database.execSQL(sqlquery ,new Object[]{transaction.getAmount(),transaction.getDate(),transaction.getType(),transaction.getAccount(),transaction.getNote(),"0"});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public ArrayList<Transaction> getAutoDesc(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sqlquery = BASIC_SELECT_QUERY;
        try{
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                Transaction transaction = parseCursor(cursor);
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
            database.execSQL("DELETE FROM PAP WHERE ID = ?",new Object[]{Integer.toString(id)});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public boolean modifyAutoDesc(Transaction transaction){
        boolean flag = false;
        try{
            String sqlquery = "UPDATE PAP SET AMOUNT = ? ,DATE = ? ,PURCHASETYPE = (SELECT ID FROM PURCHASETYPE WHERE NAME = ?) ,ACCOUNT = (SELECT ID FROM ACCOUNT WHERE NAME = ?) ,NOTE = ? WHERE ID = ?";
            database.execSQL(sqlquery, new Object[]{transaction.getAmount(),transaction.getDate(),transaction.getType(),transaction.getAccount(),transaction.getNote(),transaction.getId()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public ArrayList<Transaction> getPAPBefore(String date_str){
        ArrayList<Transaction> trans = new ArrayList<>();
        String sqlquery = BASIC_SELECT_QUERY+" WHERE DATE <= ?";
        try {
            Cursor cursor = database.rawQuery(sqlquery,new String[]{date_str});
            while (cursor.moveToNext()){
                Transaction t = parseCursor(cursor);
                trans.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trans;
    }
    public ArrayList<Transaction> getPAPBefore(Date date){
        return  getPAPBefore(date.toString());
    }
    public ArrayList<Transaction> getUncheckedPAPBefore(Date date){
        ArrayList<Transaction> trans = new ArrayList<>();
        String sqlquery = BASIC_SELECT_QUERY + " WHERE CHECKED = 0 AND DATE <= ? ORDER BY DATE DESC";
        try {
            Cursor cursor = database.rawQuery(sqlquery,new String[]{date.toString()});
            while (cursor.moveToNext()){
                Transaction t = parseCursor(cursor);
                trans.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trans;
    }
    public ArrayList<Transaction> getUncheckedPAP(){
        ArrayList<Transaction> trans = new ArrayList<>();
        String sqlquery = BASIC_SELECT_QUERY + " WHERE CHECKED = 0 ORDER BY DATE DESC";
        try {
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                Transaction t = parseCursor(cursor);
                trans.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trans;
    }
    private Transaction parseCursor (Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        double amount = cursor.getDouble(cursor.getColumnIndex("BALANCE"));
        Date date = new Date(cursor.getString(cursor.getColumnIndex("DATE")));
        String purchase_type = cursor.getString(cursor.getColumnIndex("NAMEW"));
        String account = cursor.getString(cursor.getColumnIndex("NAMEQ"));
        String note = cursor.getString(cursor.getColumnIndex("NOTE"));
        return new Transaction(id,date,amount,account,note,purchase_type);
    }
    public boolean checkPAP(int id){
        boolean flag = false;
        String sqlquery = "UPDATE PAP SET CHECKED = 1 WHERE ID = ?";
        try {
            database.execSQL(sqlquery,new Object[]{Integer.toString(id)});
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }




}
