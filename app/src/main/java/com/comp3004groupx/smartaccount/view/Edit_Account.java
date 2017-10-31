package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.AccountTypeDAO;
import com.comp3004groupx.smartaccount.module.DecimalDigitsInputFilter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuguanhong on 2017-10-29.
 */

public class Edit_Account extends AppCompatActivity {

    Spinner accountTypeSpinner;
    EditText name;
    EditText amount;
    AccountDAO accountDAO;
    AccountTypeDAO AccountType;
    Account account;
    Button editButton;
    Button deleteButton;
    DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_account);
        decimalFormat = new DecimalFormat("0.00");
        //Set button
        editButton = (Button) findViewById(R.id.editButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        //Get account from last activity
        Intent intent = getIntent();
        int accountId = intent.getIntExtra("ID", 0);
        accountDAO = new AccountDAO(getApplicationContext());
        account = accountDAO.getAccount(accountId);

        //Init AccountTypeSpinner
        accountTypeSpinner = (Spinner) findViewById(R.id.AccountTypeSpinner);
        setUpAccountTypeSpinner(accountTypeSpinner);

        //Set AccountTypeSpinner
        int accountTypePosition = getTypePosition(accountTypeSpinner, account.getType().toString());
        accountTypeSpinner.setSelection(accountTypePosition);

        //Set Account Name
        String accountName = account.getName();
        name = (EditText) findViewById(R.id.Name);
        name.setText(accountName);

        //Set Account Amount
        double accountAmount = account.getBalance();
        amount = (EditText) findViewById(R.id.Amount);
        amount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        //accountAmount = checkAmount(account.getType().toString(), accountAmount);
        amount.setText(decimalFormat.format(accountAmount));
        //Delete this account
        deleteAccount();

        //Edit this account
        editAccount();

    }

    public void toast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void setUpAccountTypeSpinner(Spinner accountTypeSpinner) {
        AccountType = new AccountTypeDAO(getApplicationContext());
        List<String> AccountTypeList = AccountType.getAllType();
        List<String> typeSpinnerList = new ArrayList<>();
        for (int i = 0; i < AccountTypeList.size(); i++) {
            typeSpinnerList.add(AccountTypeList.get(i));
        }
        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(typeDataAdapter);
    }

    public int getTypePosition(Spinner TypeSpinner, String toComp) {
        for (int i = 0; i < TypeSpinner.getAdapter().getCount(); i++) {
            String checkS = TypeSpinner.getAdapter().getItem(i).toString();
            if (toComp.equals(TypeSpinner.getAdapter().getItem(i).toString())) {
                int check = i;
                return i;
            }
        }
        return 0;
    }

    public void deleteAccount() {
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accountDAO.removeAccount(account.getID())) {
                    toast("Success");
                    finish();
                }
            }
        });
    }

    public void editAccount() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountName = name.getText().toString();
                double accountAmount = Double.parseDouble(amount.getText().toString());
                String accountType = accountTypeSpinner.getSelectedItem().toString();
                double amountDifference = accountAmount - account.getBalance();
                accountAmount = checkAmount(accountType, accountAmount);
                if (errorChecking(accountName)) {
                    account.setName(accountName);
                    account.setType(accountType);
                    account.setBalance(accountAmount);
                    account.setReal_balance(account.getRealAmount() + amountDifference);
                    if (accountDAO.updateAccount(account)) {
                        toast("Success");
                        finish();
                    }
                }
            }
        });
    }

    public boolean errorChecking(String accountName) {
        boolean noError = true;
        if (accountName.equals("")) {
            noError = false;
            toast("Account Name can not empty");
        }
        return noError;
    }

    public double checkAmount(String accountType, double accountAmount) {
        if (accountType.equals("Credit Card")) {
            if (accountAmount < 0) {
                accountAmount *= -1;
                return accountAmount;
            }
        }
        return accountAmount;
    }

}
