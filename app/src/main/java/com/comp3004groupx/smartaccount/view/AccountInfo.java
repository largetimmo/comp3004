package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;

import java.text.DecimalFormat;

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
    DecimalFormat decimalFormat;
    int accountId;
    boolean after_pause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountinfo);
        decimalFormat = new DecimalFormat("0.00");

        //Get account from last activity
        Intent intent = getIntent();
        accountId = intent.getIntExtra("ID", 0);

        System.out.print(account);

        //Set all information of the account
        freshInfo(accountId);

        //Set Edit button
        editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Edit_Account.class);
                intent.putExtra("ID", accountId);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (after_pause) {
            freshInfo(accountId);
            //finish();
            after_pause = false;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        after_pause = true;
    }

    public String checkAmount(String accountType, double accountAmount) {
        if (accountType.equals("Credit Card")) {
            if (accountAmount > 0) {

                return "(" + decimalFormat.format(accountAmount) + ")";
            }
            else {
                return decimalFormat.format(accountAmount*-1);
            }
        }
        return decimalFormat.format(accountAmount);
    }

    public void freshInfo(int accountId) {
        accountDAO = new AccountDAO(getApplicationContext());
        account = accountDAO.getAccount(accountId);

        if (account == null){
            finish();
        }
        else {
            //Show Account Name
            String accountName = account.getName();
            accountTitle = (TextView) findViewById(R.id.accountTitle);
            accountTitle.setText(accountName + " Information");




            //Show Account Type
            String type = account.getType();
            accountType = (TextView) findViewById(R.id.accountType);
            accountType.setText(type);

            //Show Account Balance
            double accountBalance = account.getBalance();
            amount = (TextView) findViewById(R.id.amount);
            String balance;
            balance = checkAmount(type,accountBalance);

            amount.setText(balance);


            //Show per balance
            double perBalance = accountBalance - account.getReal_balance();
            accountPerBalance = (TextView) findViewById(R.id.perBalance);
            accountPerBalance.setText(decimalFormat.format(perBalance));
        }
    }

//    public double checkIsCreditCard(Account account,double account){
//
//    }
}

