package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.*;
import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;

/**
 * Created by devray on 2017-09-16.
 * Taken by devray
 */

public class Transaction extends AppCompatActivity {

    public Transaction(Date currDate, Double amount, String accountName, String note, String type) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction);
        this.setTitle("Transaction");
    }
}