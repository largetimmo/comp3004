package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class PurchaseTypeDAO {
    PurchaseTypeDBHelper purchaseTypeDBHelper = null;
    public PurchaseTypeDAO(Context context){
        purchaseTypeDBHelper = new PurchaseTypeDBHelper(context);
    }
    public List<String> getalltypes(){
        List<String> alltypes = new ArrayList<>();
        String sqlquery = "SELECT * FROM PURCHASETYPE";
        SQLiteDatabase database = null;
        try {
            database = purchaseTypeDBHelper.getReadableDatabase();
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                alltypes.add(name);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
        return alltypes;
    }
    public boolean addType(String type){
        Boolean flag = false;
        String sqlquery = "INSERT INTO PURCHASETYPE(NAME) VALUES(?)";
        SQLiteDatabase database = null;
        try {
            database = purchaseTypeDBHelper.getWritableDatabase();
            database.execSQL(sqlquery,new Object[]{type});
            flag= true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
        return flag;
    }
    public boolean removeType(String type){
        Boolean flag = false;
        String sqlquery = "DELETE FROM PURCHASETYPE WHERE NAME = ?";
        SQLiteDatabase database = null;
        try {
            database = purchaseTypeDBHelper.getWritableDatabase();
            database.execSQL(sqlquery,new Object[]{type});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            database.close();
        }
        return flag;
    }
}
