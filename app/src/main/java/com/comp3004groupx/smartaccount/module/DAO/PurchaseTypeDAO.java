package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class PurchaseTypeDAO extends AbstractDAO{
    public PurchaseTypeDAO(Context context){
        super(context);
    }
    public ArrayList<String> getalltypes(){
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
    public boolean addType(String type){
        Boolean flag = false;
        String sqlquery = "INSERT INTO PURCHASETYPE(NAME) VALUES(?)";
        try {
            database.execSQL(sqlquery,new Object[]{type});
            flag= true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public boolean removeType(String type){
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
}
