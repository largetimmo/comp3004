package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class PurchaseTypeDAO extends AbstractDAO{
    public PurchaseTypeDAO(Context context){
        super(context);
        dbname = "PURCHASETYPE";
    }
    @Deprecated
    public ArrayList<String> getalltypes(){
        //done
        ArrayList<String> alltypes = new ArrayList<>();
        String sqlquery = "SELECT * FROM PURCHASETYPE";
        try {
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                alltypes.add(name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alltypes;
    }
    @Deprecated
    public boolean addType(String type){
        //done
        Boolean flag = false;
        String sqlquery = "INSERT INTO PURCHASETYPE(NAME,INCOME) VALUES(?,0)";
        try {
            database.execSQL(sqlquery,new Object[]{type});
            flag= true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public boolean removeType(String type){
        //done
        Boolean flag = false;
        String sqlquery = "DELETE FROM PURCHASETYPE WHERE NAME = ?";
        try {
            database.execSQL(sqlquery,new Object[]{type});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public ArrayList<String> getAllIncomeType(){
        //done
        String sqlquery = "SELECT NAME FROM PURCHASETYPE WHERE INCOME = 1";
        ArrayList<String> types = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                types.add(cursor.getString(cursor.getColumnIndex("NAME")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return types;
    }
    public ArrayList<String> getALLExpenseType(){
        //done
        String sqlquery = "SELECT NAME FROM PURCHASETYPE WHERE INCOME = 0";
        ArrayList<String> types = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                types.add(cursor.getString(cursor.getColumnIndex("NAME")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return types;
    }
    public boolean addPurchaseType(String name){
        //done
        String sqlquery = "INSERT INTO PURCHASETYPE(NAME,INCOME) VALUES (?,0)";
        boolean flag = false;
        try {
            database.execSQL(sqlquery,new String[]{name});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public boolean addIncomeType(String name){
        //done
        String sqlquery = "INSERT INTO PURCHASETYPE(NAME, INCOME) VALUES (?,1)";
        boolean flag = false;
        try {
            database.execSQL(sqlquery,new String[]{name});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  flag;
    }

}
