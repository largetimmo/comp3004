package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.text.InputFilter;
import android.view.View;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;


import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;

/**
 * Created by wuguanhong on 2017-10-24.
 */

public class Per_Auth_Payment extends AppCompatActivity  {
    AccountDAO accounts;
    PurchaseTypeDAO purchaseTypeList;

    TabHost host;
    Button expButton;
    EditText expAmount;
    Spinner expAccountSpinner;
    Spinner expTypeSpinner;
    EditText expNote;
    Button incButton;
    EditText incAmount;
    Spinner incAccountSpinner;
    Spinner incTypeSpinner;
    EditText incNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.per_auth_payment);
        this.setTitle("Per-Auth Payment");
        expAmount = (EditText) findViewById(R.id.expenseAmount);
        expAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        incAmount = (EditText) findViewById(R.id.incomeAmount);
        incAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        setupTabHost();
        setUpExpenseSpinner();
        addExpense();
        setUpIncomeSpinner();
        addIncome();


    }

    public void setupTabHost() {
        host = (TabHost) findViewById(R.id.tabs);
        host.setup();

        //expense tab
        TabHost.TabSpec spec = host.newTabSpec("Expense");
        spec.setContent(R.id.expense);
        spec.setIndicator("Expense");
        host.addTab(spec);

        //income tab
        spec = host.newTabSpec("Income");
        spec.setContent(R.id.income);
        spec.setIndicator("Income");
        host.addTab(spec);
    }

    public Date getDate() {
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String[] date = dateFormat.format(newCalendar.getTime()).split(" ");
        int year, month, day;
        year = Integer.parseInt(date[0]);
        switch (date[1]) {
            case "Jan":
                month = 1;
                break;
            case "Feb":
                month = 2;
                break;
            case "Mar":
                month = 3;
                break;
            case "Apr":
                month = 4;
                break;
            case "May":
                month = 5;
                break;
            case "Jun":
                month = 6;
                break;
            case "Jul":
                month = 7;
                break;
            case "Aug":
                month = 8;
                break;
            case "Sep":
                month = 9;
                break;
            case "Oct":
                month = 10;
                break;
            case "Nov":
                month = 11;
                break;
            case "Dec":
                month = 12;
                break;
            default:
                month = 0;
                break;
        }
        day = Integer.parseInt(date[2]);
        Date currDate = new Date(year, month, day);
        return currDate;
    }

    public void setUpExpenseSpinner() {

//        setup type spinner
        expTypeSpinner = (Spinner) findViewById(R.id.expenseTypeSpinner);
        purchaseTypeList = new PurchaseTypeDAO(getApplicationContext());
        List<String> expenseTypeList = purchaseTypeList.getalltypes();         //list get at start
        List<String> typeSpinnerList = new ArrayList<>();                       //list add to spinner
        typeSpinnerList.add("----Select Expense Type------------------------------");
        for (int i = 0; i < expenseTypeList.size(); i++) {
            typeSpinnerList.add(expenseTypeList.get(i));
        }
        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expTypeSpinner.setAdapter(typeDataAdapter);
//        setup accountinfo spinner
        expAccountSpinner = (Spinner) findViewById(R.id.expenseAccountSpinner);
        accounts = new AccountDAO(getApplicationContext());
        ArrayList<Account> accountList = accounts.getAllAccount();              //list get at start
        List<String> accountSpinnerList = new ArrayList<>();                    //list add to spinner
        accountSpinnerList.add("----Select Account---------------------------------------");
        for (int i = 0; i < accountList.size(); i++) {
            accountSpinnerList.add(accountList.get(i).getName());
        }
        ArrayAdapter<String> accountDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountSpinnerList);
        accountDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expAccountSpinner.setAdapter(accountDataAdapter);

    }

    public void addExpense() {
        expButton = (Button) findViewById(R.id.addExpenseButton);

        expAccountSpinner = (Spinner) findViewById(R.id.expenseAccountSpinner);
        expTypeSpinner = (Spinner) findViewById(R.id.expenseTypeSpinner);
        expNote = (EditText) findViewById(R.id.expenseNotes);


        expButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (errCheck("expense")) {
                    Date currDate = getDate();
                    double Amount = Double.parseDouble(expAmount.getText().toString());
                    String accountName = expAccountSpinner.getSelectedItem().toString();
                    String Note = expNote.getText().toString();
                    String Type = expTypeSpinner.getSelectedItem().toString();
                    TransactionDAO TransDAO = new TransactionDAO(getApplicationContext());

                    //new obj
                    Transaction newTrans = new Transaction(currDate, Amount, accountName, Note, Type);
                    //insert
                    TransDAO.addTrans(newTrans);
                    //back to main
                    finish();
                }
            }
        });
    }

    public void setUpIncomeSpinner() {

//        setup type spinner
        incTypeSpinner = (Spinner) findViewById(R.id.incomeTypeSpinner);
        //purchaseTypeList = new PurchaseTypeDAO(getApplicationContext());   TODO: income list when it ready
        //List<String> incomeTypeList = purchaseTypeList.getalltypes();         //list get at start

        List<String> typeSpinnerList = new ArrayList<>();                       //list add to spinner
        typeSpinnerList.add("----Select Income Type--------------------------------");
        //for (int i = 0; i < expenseTypeList.size(); i++) {
        //    typeSpinnerList.add(expenseTypeList.get(i));
        //}
        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incTypeSpinner.setAdapter(typeDataAdapter);

//        setup accountinfo spinner
        incAccountSpinner = (Spinner) findViewById(R.id.incomeAccountSpinner);
        accounts = new AccountDAO(getApplicationContext());
        ArrayList<Account> accountList = accounts.getAllAccount();              //list get at start

        List<String> accountSpinnerList = new ArrayList<>();                    //list add to spinner
        accountSpinnerList.add("----Select Account---------------------------------------");
        for (int i = 0; i < accountList.size(); i++) {
            accountSpinnerList.add(accountList.get(i).getName());
        }

        ArrayAdapter<String> accountDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountSpinnerList);
        accountDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incAccountSpinner.setAdapter(accountDataAdapter);


    }

    public void addIncome() {


        incButton = (Button) findViewById(R.id.addIncomeButton);

        incAccountSpinner = (Spinner) findViewById(R.id.incomeAccountSpinner);
        incTypeSpinner = (Spinner) findViewById(R.id.incomeTypeSpinner);
        incNote = (EditText) findViewById(R.id.incomeNotes);


        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (errCheck("income")) {
                    Date currDate = getDate();
                    double Amount = Double.parseDouble(incAmount.getText().toString());
                    String accountName = incAccountSpinner.getSelectedItem().toString();
                    String Note = incNote.getText().toString();
                    String Type = incTypeSpinner.getSelectedItem().toString();
                    TransactionDAO TransDAO = new TransactionDAO(getApplicationContext());

                    //new obj
                    Transaction newTrans = new Transaction(currDate, Amount, accountName, Note, Type);
                    //insert
                    TransDAO.addTrans(newTrans);
                    finish();
                }
            }
        });

        incButton = (Button) findViewById(R.id.addIncomeButton);
        incAmount = (EditText) findViewById(R.id.incomeAmount);
    }

    public boolean errCheck(String transType) {

        Context context = getApplicationContext();
        boolean noErr = true;
        CharSequence text = "";

        if (transType.equals("expense")) {
            if (expAmount.getText().toString().equals("")) {
                text = "Please enter amount.";
                noErr = false;
            }
            if (expAccountSpinner.getSelectedItem().toString().equals("----Select Account---------------------------------------")) {
                text = "Please select a accountinfo.";
                noErr = false;
            }
            if (expTypeSpinner.getSelectedItem().toString().equals("----Select Expense Type------------------------------")) {
                text = "Please select a type.";
                noErr = false;
            }

        }
        if (transType.equals("income")) {
            if (expAmount.getText().toString().equals("")) {
                text = "Please enter amount.";
                noErr = false;
            }
            if (expAccountSpinner.getSelectedItem().toString().equals("----Select Account---------------------------------------")) {
                text = "Please select a accountinfo.";
                noErr = false;
            }
            if (expTypeSpinner.getSelectedItem().toString().equals("----Select Income Type--------------------------------")) {
                text = "Please select a type.";
                noErr = false;
            }

        }
        if (!noErr) {
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        }

        return noErr;
    }

}
