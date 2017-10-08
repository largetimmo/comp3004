package com.comp3004groupx.smartaccount.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.AccountTypeDAO;
import com.comp3004groupx.smartaccount.Core.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuguanhong on 2017-09-20.
 */

public class Add_Account extends AppCompatActivity{
    AccountTypeDAO AccountTypes;
    EditText AccountName = (EditText)findViewById(R.id.AccountName);
    EditText AccountAmount = (EditText)findViewById(R.id.NewAmount);
    Spinner TypeSpinner = (Spinner)findViewById(R.id.AccountTypeSpinner);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        this.setTitle("Add a New Account");

        setTypeSpinner();
        addAccount();
    }
    public void setTypeSpinner(){
        ArrayList<String> AccountTypeList = AccountTypes.getAllType();
        List<String> TypeSpinnerList = new ArrayList<>();                    //list add to spinner
        TypeSpinnerList.add("----------Select Account Type----------");
        for (int i = 0; i < AccountTypeList.size(); i++) {
            TypeSpinnerList.add(AccountTypeList.get(i));
        }

        ArrayAdapter<String> TypeDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TypeSpinnerList);
        TypeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeSpinner.setAdapter(TypeDataAdapter);
    }
    public void addAccount(){
        //Who should set ID for Account? Users or us?
        int ID = (int)(Math.random()*10);//Set the ID manually for now
        String name = AccountName.getText().toString();
        String type = TypeSpinner.getSelectedItem().toString();
        Double amount = Double.parseDouble(AccountAmount.getText().toString());
        //ToDo check if the name is already in the list.
        Account newAccount = new Account(ID, name, type, amount);
        AccountDAO AccDAO = new AccountDAO(getApplicationContext());
        AccDAO.addAccount(newAccount);
    }
}
