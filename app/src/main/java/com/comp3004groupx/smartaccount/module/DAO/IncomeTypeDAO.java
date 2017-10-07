package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/10/6.
 */

public class IncomeTypeDAO extends AbstractDAO{
    public IncomeTypeDAO(Context context) {
        super(context);
        dbname = "INCOMETYPE";
    }
    public boolean addType(String type){
        String sqlquery = "INSERT INTO INCOMETYPE VALUES(?)";
        boolean flag = false;
        try {
            database.execSQL(sqlquery,new Object[]{type});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public ArrayList<String> getAllTypes(){
        String sqlquery = "SELECT * FROM INCOMETYPE";
        ArrayList<String> alltype = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(sqlquery,null);
            while (cursor.moveToNext()){
                alltype.add(cursor.getColumnName(cursor.getColumnIndex("NAME")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alltype;
    }
    public boolean removeTypes(String name){
        String sqlquery = "DELETE FROM INCOMETYPE WHERE NAME = ?";
        boolean flag = false;
        try {
            database.execSQL(sqlquery,new String[]{name});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
