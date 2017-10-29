package com.comp3004groupx.smartaccount.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuguanhong on 2017-09-20.
 */

public class Transaction_List_Account extends AppCompatActivity{
    AccountDAO accounts;
    TransactionDAO trans;
    TextView balance;
    Spinner AccountSpinner;
    ListView TransList;
    Transaction_Adapter AccountTransList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list_account);

        setSpinner();
        //Found online for dynamically change contents in ListView.
        Button show = (Button)findViewById(R.id.show);

        show.setOnClickListener(new AdapterView.OnClickListener(){
            @Override
            public void onClick(View v) {
                showAccountInfo();
            }
        });

    }
    public void setSpinner(){
        AccountSpinner = (Spinner)findViewById(R.id.AccountList);
        accounts = new AccountDAO(getApplicationContext());

        ArrayList<Account> AccountList = accounts.getAllAccount();
        List<String> AccountsNameList = new ArrayList<String>();
        for (int i = 0; i < AccountList.size(); i++) {
            AccountsNameList.add(AccountList.get(i).getName());
        }
        ArrayAdapter<String> AccountsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, AccountsNameList);
        AccountsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AccountSpinner.setAdapter(AccountsAdapter);
    }
    public void showAccountInfo(){
        accounts = new AccountDAO(getApplicationContext());
        balance = (TextView) findViewById(R.id.Balance);
        TransList = (ListView)findViewById(R.id.TransByAccount);
        AccountSpinner = (Spinner)findViewById(R.id.AccountList);
        trans = new TransactionDAO(getApplicationContext());

        String name = AccountSpinner.getSelectedItem().toString();
        Account thisAccount = accounts.getAccount(name);
        balance.setText(String.format("%.2f",thisAccount.getBalance()));
        ArrayList<Transaction> allTrans = trans.getAllTransaction();
        ArrayList<Transaction> TransStrings = new ArrayList<>();

        //Find transactions belonging to this account
        for(int i=0; i<allTrans.size();i++){
            if(allTrans.get(i).getAccount().equals(name)) {
                TransStrings.add(i,allTrans.get(i));
            }
        }
        AccountTransList = new Transaction_Adapter(this, TransStrings);
        TransList.setAdapter(AccountTransList);
        AccountTransList.notifyDataSetChanged();
    }
    /*public void MergeSort(List<Transaction> accountinfo){
        int size = accountinfo.size();
        List<Transaction> tempSortList = new ArrayList<>(size);
        doMerge(0,size-1,accountinfo,tempSortList);
    }
    public void doMerge(int start, int end, List<Transaction> accountinfo, List<Transaction> temp){
        if(start<end){
            int middle = start+(end-start)/2;
            doMerge(start,middle,accountinfo,temp);
            doMerge(middle+1,end,accountinfo,temp);
            mergeParts(start, middle, end, accountinfo, temp);
        }
    }
    public void mergeParts(int start, int middle, int end,List<Transaction> accountinfo,List<Transaction> temp){
        for(int i=start; i<end; i++){
            temp.set(i,accountinfo.get(i));
        }
        int x = start, y = middle + 1, z = start;
        while(x<=middle&&y<=end){
            if(temp.get(x).getDate().compareTo(temp.get(y).getDate())<=0){
                accountinfo.set(z, temp.get(x));
                x++;
            }
            else{
                accountinfo.set(z, temp.get(y));
                y++;
            }
            z++;
        }
        while(x<=middle){
            accountinfo.set(z, temp.get(x));
            z++;
            x++;
        }
    }*/
}
