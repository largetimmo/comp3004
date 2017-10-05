package com.comp3004groupx.smartaccount.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

//import android.provider.Settings; 这他妈是什么玩意

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.TabHost;

import java.util.List;
import java.util.ArrayList;


import com.comp3004groupx.smartaccount.R;


/**
 * Created by devray on 2017-09-16.
 */


public class NewTransaction extends AppCompatActivity {


    Button expButton;
    EditText expText;
    Spinner expAccountSpinner;
    Spinner expTypeSpinner;

    Button incButton;
    EditText incText;
    Spinner incAccountSpinner;
    Spinner incTypeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtransaction);

        setUpExpenseSpinner();
        addExpense();

        setUpIncomeSpinner();
        addIncome();


        TabHost host = (TabHost)findViewById(R.id.tabs);
        host.setup();

        //expense tab
        TabHost.TabSpec spec = host.newTabSpec("Tab Expense");
        spec.setContent(R.id.expense);
        spec.setIndicator("Eab Expense");
        host.addTab(spec);

        //income tab
        spec = host.newTabSpec("Tab Income");
        spec.setContent(R.id.income);
        spec.setIndicator("Tab Income");
        host.addTab(spec);

    }

    public void setUpExpenseSpinner() {

        expAccountSpinner = (Spinner) findViewById(R.id.expenseAccountSpinner);
        expTypeSpinner = (Spinner) findViewById(R.id.expenseTypeSpinner);

        List<String> accountList = new ArrayList<>();
        List<String> typeList = new ArrayList<>();

//        TODO (expense)ask kyle about how to access database, add account to list


//        find ArrayAdapter online, don't really know how to work, leave to future

//        setup account spinner
        accountList.add("EA list 1");
        accountList.add("EA list 2");
        accountList.add("EA list 3");
        ArrayAdapter<String> accountDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, accountList);
        accountDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expAccountSpinner.setAdapter(accountDataAdapter);

//        setup type spinner
        typeList.add("ET list 1");
        typeList.add("ET list 2");
        typeList.add("ET list 3");
        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, typeList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expTypeSpinner.setAdapter(typeDataAdapter);
    }


    public void addExpense() {

        expButton = (Button) findViewById(R.id.addExpenseButton);
        expText = (EditText) findViewById(R.id.expenseAmount);

//        click on add expense
        expButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float expAmount = Float.parseFloat(expText.getText().toString());
//                TODO addexpense event
//                later, later
            }
        });
    }


    public void setUpIncomeSpinner() {

        incAccountSpinner = (Spinner) findViewById(R.id.incomeAccountSpinner);
        incTypeSpinner = (Spinner) findViewById(R.id.incomeTypeSpinner);

        List<String> accountList = new ArrayList<>();
        List<String> typeList = new ArrayList<>();

//        TODO (income)ask kyle about how to access database, add account to list



        //TODO: clear number when user tap text edit.

//        find ArrayAdapter online, don't really know how to work, leave to future

//        setup account spinner
        accountList.add("IA list 1");
        accountList.add("IA list 2");
        accountList.add("IA list 3");
        ArrayAdapter<String> accountDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, accountList);
        accountDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incAccountSpinner.setAdapter(accountDataAdapter);

//        setup type spinner
        typeList.add("IT list 1");
        typeList.add("IT list 2");
        typeList.add("IT list 3");
        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, typeList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incTypeSpinner.setAdapter(typeDataAdapter);

    }


    public void addIncome() {
        incButton = (Button) findViewById(R.id.addIncomeButton);
        incText = (EditText) findViewById(R.id.incomeAmount);
//        TODO addincome event
    }


}
