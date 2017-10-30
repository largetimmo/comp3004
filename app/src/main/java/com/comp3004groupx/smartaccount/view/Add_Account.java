package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuguanhong on 2017-09-20.
 */

public class Add_Account extends AppCompatActivity{

    Spinner accountTypeSpinner;
    AccountTypeDAO AccountType;
    EditText AccountName;
    EditText AccountAmount;
    Button CreateButton;
    AccountDAO accountDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_account);
        this.setTitle("Add a New Account");

        accountDAO = new AccountDAO(getApplicationContext());
        AccountAmount = (EditText) findViewById(R.id.Amount);
        AccountAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});

        setUpAccountTypeSpinner();
        //cleanDefaultValue();
        createAccount();
    }

    public void setUpAccountTypeSpinner() {
        accountTypeSpinner = (Spinner) findViewById(R.id.AccountTypeSpinner);

        AccountType = new AccountTypeDAO(getApplicationContext());
        List<String> AccountTypeList = AccountType.getAllType();
        List<String> typeSpinnerList = new ArrayList<>();
        typeSpinnerList.add("Select Account");
        for (int i= 0; i < AccountTypeList.size(); i++){
            typeSpinnerList.add(AccountTypeList.get(i));
        }
        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(typeDataAdapter);


    }

    public void cleanDefaultValue(){
        AccountName = (EditText) findViewById(R.id.Name);


        AccountName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AccountName.setText("");
            }
        });
        AccountAmount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AccountAmount.setText("");
            }
        });
    }

    public void createAccount(){
        CreateButton = (Button) findViewById(R.id.button);
        AccountName = (EditText) findViewById(R.id.Name);
        AccountAmount = (EditText) findViewById(R.id.Amount);
        accountTypeSpinner = (Spinner) findViewById(R.id.AccountTypeSpinner);

        CreateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean isCreate = false;
                String accountName = AccountName.getText().toString();
                double amount = Double.parseDouble(AccountAmount.getText().toString());
                String accountType = accountTypeSpinner.getSelectedItem().toString();
                if (checkSpinner(accountType) == false){
                    Context context = getApplicationContext();
                    CharSequence text = "Please select a type of account!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else{
                    Account newAccount = new Account(accountName, accountType, amount,amount);
                    isCreate = accountDAO.addAccount(newAccount);
                    if (isCreate == true){
                        Context context = getApplicationContext();
                        CharSequence text = "Success";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        finish();
                    }
                    else{
                        Context context = getApplicationContext();
                        CharSequence text = "Fail";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }
        });
    }
    public boolean checkSpinner(String selectText){
        String equalText = "----Select Account Type------------------------------";
        if (selectText.equals(equalText)){
            return false;
        }
        return true;
    }

}
