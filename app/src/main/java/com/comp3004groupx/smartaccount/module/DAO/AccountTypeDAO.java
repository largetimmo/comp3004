package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class AccountTypeDAO  extends AbstractDAO{
    public AccountTypeDAO(Context context){
        super(context);
        dbname = "ACCOUNTTYPE";
    }
    public ArrayList<String> getAllType(){
        ArrayList<String> alltypes = new ArrayList<>();
        String sqlquery = "SELECT * FROM ACCOUNTTYPE";
        try{
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
}
