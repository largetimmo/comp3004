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
    }
}