package com.comp3004groupx.smartaccount.module.DAO;

import android.content.Context;
import android.database.Cursor;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.util.ArrayList;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class TransactionDAO extends AbstractDAO{
    private static final String BASIC_SELECT_QUERY = "SELECT TRANS.ID AS ID, DATE,ACCOUNT.NAME AS NAME1, TRANS.AMOUNT, NOTE, PURCHASETYPE.NAME AS NAME2 FROM TRANS INNER JOIN ACCOUNT ON TRANS.ACCOUNT = ACCOUNT.ID INNER JOIN PURCHASETYPE ON TRANS.TYPE = PURCHASETYPE.ID";
    public TransactionDAO(Context context){
        super(context);
        dbname = "TRANS";
    }
    public ArrayList<Transaction> getAllTransaction(Date date_from, Date date_to, String account_name, String type){
        StringBuilder sqlquery = new StringBuilder();
        ArrayList<String> params_list = new ArrayList<>();
        sqlquery.append(BASIC_SELECT_QUERY);
        sqlquery.append(" WHERE");
        if(!account_name.equals("ALL")){
            sqlquery.append(" ACCOUNT.NAME = ? AND");
            params_list.add(account_name);
        }
        if(!type.equals("ALL")){
            sqlquery.append(" PURCHASETYPE.NAME = ? AND");
            params_list.add(type);
        }
        sqlquery.append(" DATE>=? AND DATE<=?");
        params_list.add(date_from.toString());
        params_list.add(date_to.toString());
        String[] params_array = new String[params_list.size()];
        int index = 0;
        for (String s:params_list){
            params_array[index++] = s;
        }
        return acquireData(sqlquery.toString(),params_array);
    }
    public Transaction getTransByID(int id){
        String sqlquery = BASIC_SELECT_QUERY+" WHERE TRANS.ID = ?";
        Transaction result = null;
        try {
            Cursor cursor = database.rawQuery(sqlquery,new String[]{Integer.toString(id)});
            if(cursor.moveToNext()){
                result = parseTrans(cursor);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public double getTotalSpend(){
        String sqlquery = BASIC_SELECT_QUERY+" WHERE TRANS.AMOUNT>0";
        double total = 0;
        try {
            Cursor cursor = database.rawQuery(sqlquery,null);
            if (cursor.getCount()==0){
                return 0.00;
            }
            while (cursor.moveToNext()){
                total+=cursor.getDouble(cursor.getColumnIndex("AMOUNT"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return total;
    }
    public double getTotalIncome(){
        String sqlquery = BASIC_SELECT_QUERY +" WHERE TRANS.AMOUNT<0";
        double total = 0;
        try {
            Cursor cursor = database.rawQuery(sqlquery, null);
            if (cursor.getCount()==0){
                return 0.00;
            }
            while (cursor.moveToNext()){
                total+=cursor.getInt(cursor.getColumnIndex("AMOUNT"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        total*=-1;
        return total;
    }

    public ArrayList<Transaction> getTopTrans(int num){
        String sqlquery = BASIC_SELECT_QUERY + " ORDER BY DATE DESC LIMIT ?";
        return acquireData(sqlquery,new String[]{Integer.toString(num)});
    }
    public ArrayList<Transaction> getAllTransThisMonth(int year, int month){
        return acquireData(BASIC_SELECT_QUERY+" WHERE DATE LIKE ?-?% ORDER BY ID",new String[]{Integer.toString(year),Integer.toString(month)});
    }
    public ArrayList<Transaction> getOneDayTrans(int year, int month, int day){
        return acquireData(BASIC_SELECT_QUERY + " WHERE DATE = ?-?-? ORDER BY ID",new String[]{Integer.toString(year),Integer.toString(month),Integer.toString(day)});
    }
    public ArrayList<Transaction> getAllTransaction(){
        String sqlquery = BASIC_SELECT_QUERY + " ORDER BY ID ASC";
        return acquireData(sqlquery,null);
    }
    public boolean addTrans(Transaction transaction){
        Boolean flag = false;
        String sqlquery = "INSERT INTO TRANS(DATE,AMOUNT,ACCOUNT,NOTE,TYPE) VALUES(?,?,(SELECT ID FROM ACCOUNT WHERE NAME = ?),?,(SELECT ID FROM PURCHASETYPE WHERE NAME = ?))";
        try{
            database.execSQL(sqlquery,new Object[]{transaction.getDate(),transaction.getAmount(),transaction.getAccount(),transaction.getNote(),transaction.getType()});
            AccountDAO accountDAO = new AccountDAO(context);
            Account account = accountDAO.getAccount(transaction.getAccount());
            account.spendMoney(transaction.getAmount());
            accountDAO.updateAccount(account);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public boolean modifyTrans(Transaction transaction){
        Boolean flag = false;
        String sqlquery = "UPDATE TRANS SET DATE = ?, AMOUNT = ?, ACCOUNT = (SELECT ID FROM ACCOUNT WHERE NAME = ?) , NOTE = ? ,TYPE = (SELECT ID FROM PURCHASETYPE WHERE NAME = ?) WHERE ID = ? ";
        try{
            AccountDAO accountDAO = new AccountDAO(context);
            String old_value = getData("AMOUNT",transaction.getId());
            double diff = Double.parseDouble(old_value)-transaction.getAmount();
            flag |= accountDAO.updateBalance(transaction.getAccount(),diff);
            database.execSQL(sqlquery, new Object[]{transaction.getDate(),transaction.getAmount(),transaction.getAccount(),transaction.getNote(),transaction.getType(),transaction.getId()});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    public boolean removeTrans(int id){
        Boolean flag = false;
        String sqlquery = "DELETE FROM TRANS WHERE ID = ?";
        try {
            AccountDAO accountDAO = new AccountDAO(context);
            double old_value = Double.parseDouble(getData("AMOUNT",id));
            accountDAO.updateBalance(Integer.parseInt(getData("ACCOUNT",id)),old_value);
            database.execSQL(sqlquery,new Object[]{id});
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    private ArrayList<Transaction> acquireData(String sqlquery, String[] paras){
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery(sqlquery,paras);
            while (cursor.moveToNext()){
                transactions.add(parseTrans(cursor));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return transactions;
    }
    private Transaction parseTrans(Cursor cursor){
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String account = cursor.getString(cursor.getColumnIndex("NAME1"));
        String date_string = cursor.getString(cursor.getColumnIndex("DATE"));
        Date date = new Date(date_string);
        Double balance = cursor.getDouble(cursor.getColumnIndex("AMOUNT"));
        String note = cursor.getString(cursor.getColumnIndex("NOTE"));
        String type = cursor.getString(cursor.getColumnIndex("NAME2"));
        return new Transaction(id,date,balance,account,note,type);
    }
    private String getData(String colname, int id){
        String result = "";
        String sqlquery = "SELECT " + colname + " FROM TRANS WHERE ID = ?";
        try {
            Cursor cursor = database.rawQuery(sqlquery,new String[]{Integer.toString(id)});
            if (cursor.moveToNext()){
                result = cursor.getString(cursor.getColumnIndex(colname));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
