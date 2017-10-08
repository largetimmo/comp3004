package com.comp3004groupx.smartaccount.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.comp3004groupx.smartaccount.R;

/**
 * Created by devray on 2017-09-16.
 */


public class Account extends AppCompatActivity {

    public Account(int id, String name, String type, Double amount) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        this.setTitle("Accounts");
    }
}

