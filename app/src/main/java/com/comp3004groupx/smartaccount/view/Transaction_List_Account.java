package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.*;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;
import com.comp3004groupx.smartaccount.Core.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuguanhong on 2017-09-20.
 */

public class Transaction_List_Account extends AppCompatActivity {
    AccountDAO accounts;
    TransactionDAO trans;
    TextView balance;
    TextView AccountName;
    Spinner AccountSpinner;
    ListView TransList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list_account);

        setSpinner();
        AccountSpinner = (Spinner)findViewById(R.id.AccountList);
        //Found online for dynamically change contents in ListView.
        AccountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TransList.setAdapter(null);
                showAccountInfo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TransList.setAdapter(null);
               // showDefault();
            }
        });
    }
    public void setSpinner(){
        AccountSpinner = (Spinner)findViewById(R.id.AccountList);
        accounts = new AccountDAO(getApplicationContext());

        ArrayList<Account> AccountList = accounts.getAllAccount();
        List<String> AccountsNameList = new ArrayList<>();
        for (int i = 0; i < AccountList.size(); i++) {
            AccountsNameList.add(AccountList.get(i).getName());
        }
        ArrayAdapter<String> AccountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AccountsNameList);
        AccountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AccountSpinner.setAdapter(AccountsAdapter);
    }
    //Don't know what to do in this method
    /*public void showDefault(){

    }*/
    public void showAccountInfo(){
        accounts = new AccountDAO(getApplicationContext());
        balance = (TextView) findViewById(R.id.Balance);
        AccountName = (TextView) findViewById(R.id.AccountName);
        TransList = (ListView)findViewById(R.id.TransByAccount);
        AccountSpinner = (Spinner)findViewById(R.id.AccountList);

        String name = AccountSpinner.getSelectedItem().toString();
        AccountName.setText(name);
        Account thisAccount = accounts.getAccount(name);
        balance.setText(Double.toString(thisAccount.getBalance()));

        ArrayList<Transaction> AccountTrans = findTransactionByAccount(name);
        List<String> TransStrings = new ArrayList<>(AccountTrans.size());
        //Found a way using custom adapter online but not work for now, changed to silly method for avoiding crash.
        for(int i=0; i<TransStrings.size(); i++){
            TransStrings.set(i,AccountTrans.get(i).getDate().toString()+"  "+AccountTrans.get(i).getAmount());
        }
        ArrayAdapter<String> AccountTransList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TransStrings);
        TransList.setAdapter((ListAdapter) TransStrings);
    }
    public ArrayList<Transaction> findTransactionByAccount(String name){

        trans = new TransactionDAO(getApplicationContext());
        //Get All Transactions
        ArrayList<Transaction> allTrans = trans.getAllTransaction();

        ArrayList<Transaction> accountTrans = new ArrayList<>();
        //Find transactions belonging to this account
        //Maybe TOOO SLOW, try to solve this in the future.
        for(int i=0; i<allTrans.size();i++){
            if(allTrans.get(i).getAccount()==name)
                accountTrans.add(allTrans.get(i));
        }

        //TODO Sort transactions by Date.
        //MergeSort(accountTrans);
        return accountTrans;
    }
    /*public void MergeSort(List<Transaction> account){
        int size = account.size();
        List<Transaction> tempSortList = new ArrayList<>(size);
        doMerge(0,size-1,account,tempSortList);
    }
    public void doMerge(int start, int end, List<Transaction> account, List<Transaction> temp){
        if(start<end){
            int middle = start+(end-start)/2;
            doMerge(start,middle,account,temp);
            doMerge(middle,end,account,temp);
            mergeParts()
        }
    }
    public void mergeParts(int start, int middle, int end,List<Transaction> account,List<Transaction> temp){
        for(int i=start; i<end; i++){
            temp.set(i,account.get(i));
        }
        int x = start, y = middle + 1, z = end;
        while(x<=middle&&y<=end){
            if(temp.get(x)<=temp.get(y))
        }
    }*/
}
