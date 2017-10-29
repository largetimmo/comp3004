package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.AccountTypeDAO;

/**
 * Created by devray on 2017-09-16.
 */


public class AccountInfo extends AppCompatActivity {

    AccountDAO accountDAO;
    Account account;
    TextView accountTitle;
    TextView amount;
    TextView accountType;
    TextView accountPerBalance;
    Button editButton;
    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountinfo);

        //Get account from last activity
        Intent intent = getIntent();
        accountId = intent.getIntExtra("ID",0);
        accountDAO = new AccountDAO(getApplicationContext());
        account = accountDAO.getAccount(accountId);
        System.out.print(account);

        //Show Account Name
        String accountName = account.getName();
        accountTitle = (TextView) findViewById(R.id.accountTitle);
        accountTitle.setText(accountName + " Information");

        //Show Account Balance
        double accountBalance = account.getBalance();
        amount = (TextView) findViewById(R.id.amount);
        amount.setText(Double.toString(accountBalance));

        //Show Account Type
        String type = account.getType();
        accountType = (TextView) findViewById(R.id.accountType);
        accountType.setText(type);

        //Show per balance
        double perBalance = accountBalance-account.getReal_balance();
        accountPerBalance = (TextView) findViewById(R.id.perBalance);
        accountPerBalance.setText(Double.toString(perBalance));

        //Set Edit button
        editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Edit_Account.class);
                intent.putExtra("ID",accountId);
                startActivity(intent);
            }
        });

    }
}

