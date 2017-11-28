package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;

import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.PAP;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class PAPDAO extends AbstractDAO {
    //CHECKED =0 = Not Check yet
    //CHECKED = 1 = already checked
    private static final String BASIC_SELECT_QUERY ="SELECT DATE,CHECKED, PAP.ID AS ID, ACCOUNT.NAME AS ACCOUNT, PAP.AMOUNT AS AMOUNT, NOTE, PURCHASETYPE.NAME AS TYPE, PERIOD FROM PAP INNER JOIN ACCOUNT ON PAP.ACCOUNT = ACCOUNT.ID INNER JOIN PURCHASETYPE ON PAP.TYPE = PURCHASETYPE.ID";
    private static final Map<String,String> CLASS_PAIR = new HashMap<>();
    private static final Map<String,String> TYPE_PAIR = new HashMap<>();
    public PAPDAO(Context context){

        super(context);
        dbname = "PAP";
        initPairs();

    }

    public boolean addAutoDesc(PAP pap){
        boolean flag = false;
        String sqlquery = "INSERT INTO PAP(AMOUNT,DATE,TYPE,ACCOUNT,NOTE,CHECKED,PERIOD) VALUES (?,?,(SELECT ID FROM PURCHASETYPE WHERE NAME = ?),(SELECT ID FROM ACCOUNT WHERE NAME = ?),?,'0','1')";
        try{
            database.execSQL(sqlquery ,new Object[]{pap.getAmount(),pap.getDate(),pap.getType(),pap.getAccount(),pap.getNote()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public ArrayList<PAP> getAutoDesc(){
        return parseCursor(BASIC_SELECT_QUERY,null);
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

    public boolean modifyAutoDesc(PAP pap){
        boolean flag = false;
        try{
            String sqlquery = "UPDATE PAP SET AMOUNT = ? ,DATE = ? ,TYPE = (SELECT ID FROM PURCHASETYPE WHERE NAME = ?) ,ACCOUNT = (SELECT ID FROM ACCOUNT WHERE NAME = ?) ,NOTE = ?,PERIOD = ? WHERE ID = ?";
            database.execSQL(sqlquery, new Object[]{pap.getAmount(),pap.getDate(),pap.getType(),pap.getAccount(),pap.getNote(),pap.getPERIOD(),pap.getId()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public ArrayList<PAP> getPAPBefore(String date_str){
        String sqlquery = BASIC_SELECT_QUERY+" WHERE DATE <= ?";
        return parseCursor(sqlquery,new String[]{date_str});
    }
    public ArrayList<PAP> getPAPBefore(Date date){
        return  getPAPBefore(date.toString());
    }
    public ArrayList<PAP> getUncheckedPAPBefore(Date date){
        String sqlquery = BASIC_SELECT_QUERY + " WHERE CHECKED = 0 AND DATE <= ? ORDER BY DATE DESC";
        return parseCursor(sqlquery,new String[]{date.toString()});
    }
    public ArrayList<PAP> getUncheckedPAP(){
        String sqlquery = BASIC_SELECT_QUERY + " WHERE CHECKED = 0 ORDER BY DATE DESC";
        return parseCursor(sqlquery,null);
    }

    private ArrayList<PAP> parseCursor (String query,String[] args){
        Cursor cursor = database.rawQuery(query,args);
        int col_count = cursor.getColumnCount();
        ArrayList<PAP> allpap = new ArrayList<>();
        String[] colnames = cursor.getColumnNames();

        while (cursor.moveToNext()){
            Class cursor_class = cursor.getClass();
            PAP pap = new PAP();
            Class pap_class = pap.getClass();
            for (int i = 0; i<col_count;i++)
                try {
                    Field pap_field = pap_class.getDeclaredField(colnames[i]);
                    pap_field.setAccessible(true);
                    String field_type_str = pap_field.getType().getName();
                    String cur_result = cursor.getString(i);
                    if (field_type_str.equals("java.lang.String")) {
                        pap_field.set(pap, cur_result);
                    }else if (field_type_str.equals("com.comp3004groupx.smartaccount.Core.Date")){
                        pap.setDate(new Date(cur_result));

                    }
                    else {
                        Class pap_field_cls = pap_field.getClass();
                        String field_method_str = "set" + TYPE_PAIR.get(field_type_str);
                        String parse_mtd_str = "parse"+TYPE_PAIR.get(field_type_str);
                        Method pap_field_mtd = pap_field_cls.getDeclaredMethod(field_method_str, Object.class,(Class) Class.forName(CLASS_PAIR.get(field_type_str)).getDeclaredField("TYPE").get(Class.forName(CLASS_PAIR.get(field_type_str))));
                        Class parser_cls = Class.forName(CLASS_PAIR.get(field_type_str));
                        Method parser_mtd = parser_cls.getDeclaredMethod(parse_mtd_str,String.class);
                        Object cursor_result = parser_mtd.invoke(parser_cls, cur_result);
                        pap_field_mtd.invoke(pap_field, pap, cursor_result);
                    }
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | ClassNotFoundException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            allpap.add(pap);
        }
        return allpap;

    }
    public boolean checkPAP(int id){
        boolean flag = false;
        String sqlquery = "UPDATE PAP SET CHECKED = 1 WHERE ID = ?";
        try {
            database.execSQL(sqlquery,new Object[]{id});
            flag = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
    public ArrayList<PAP> getCheckedPAP(){
        String sqlquery = BASIC_SELECT_QUERY + " WHERE CHECKED = 1";
        return parseCursor(sqlquery,null);
    }
    private void initPairs(){
        CLASS_PAIR.put("int","java.lang.Integer");
        CLASS_PAIR.put("double","java.lang.Double");
        CLASS_PAIR.put("boolean","java.langBoolean");
        CLASS_PAIR.put("float","java.langFloat");
        CLASS_PAIR.put("long","java.lang.Long");
        TYPE_PAIR.put("int","Int");
        TYPE_PAIR.put("double","Double");
        TYPE_PAIR.put("boolean","Boolean");
        TYPE_PAIR.put("float","Float");
        TYPE_PAIR.put("long","Long");
    }

}
