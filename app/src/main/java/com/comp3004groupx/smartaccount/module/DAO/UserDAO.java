package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;
import android.util.Pair;

/**
 * Created by chenjunhao on 2017/11/11.
 */

public class UserDAO extends AbstractDAO {
    //Pair<String, String> = Pair<Username,Password>
    public UserDAO(Context context) {
        super(context);
    }
    public Pair<String,String> getUP(){
        String sqlquery = "SELECT * FROM USER";
        try {
            Cursor cursor = database.rawQuery(sqlquery,null);
            if(cursor.moveToNext()){
                return new Pair<>(cursor.getString(cursor.getColumnIndex("USERNAME")),cursor.getString(cursor.getColumnIndex("PASSWORD")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Pair<>(null,null);
    }
    public boolean setUP(Pair<String,String> upPair){
        String sqlquery = "INSERT INTO USER(USERNAME,PASSWORD) VALUES(?,?)";
        boolean flag = false;
        try {
            database.execSQL(sqlquery,new String[]{upPair.first,upPair.second});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
