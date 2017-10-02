package com.comp3004groupx.smartaccount.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.*;
import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;

/**
 * Created by devray on 2017-09-16.
 */

public class Transaction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        this.setTitle("Transaction");
        AccountDAO accountDAO = new AccountDAO(getApplicationContext());
        accountDAO.addAccount(new Account("TEST","TEST",0.00));
        String name = accountDAO.getAllAccount().get(0).getName();
        TextView textView = (TextView) findViewById(R.id.textView15);
        textView.setText(name);
    }
}