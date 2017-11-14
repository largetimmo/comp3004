package com.comp3004groupx.smartaccount.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.PAPDAO;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;
import com.comp3004groupx.smartaccount.module.DecimalDigitsInputFilter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by devray on 2017-09-16.
 * Taken by devray.
 */


public class NewTransaction extends AppCompatActivity {


    AccountDAO accounts;
    PurchaseTypeDAO purchaseTypeList;

    TabHost host;

    TextView expDate;
    TextView incDate;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePickerDialog;

    Button expButton;
    EditText expAmount;
    Spinner expAccountSpinner;
    Spinner expTypeSpinner;
    EditText expNote;
    Switch expSwitch;
    TextView PS1;
    View line1;

    Button incButton;
    EditText incAmount;
    Spinner incAccountSpinner;
    Spinner incTypeSpinner;
    EditText incNote;
    Switch incSwitch;
    TextView PS2;
    View line2;

    Date currDate;
    Date preDate;

    List<Integer> Day = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newtransaction);

        setupTabHost();
//        clearDefaultValue();



        setUpExpenseSpinner();
        addExpense();


        setUpIncomeSpinner();
        addIncome();
    }

    //    setup----------------------------------------------------------------------------------------------------------------------------------
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


    public void setIncPreDate(){
        incDate = (TextView) findViewById(R.id.incDate);
        calendar = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd";

        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);
        incDate.setText(sdf.format(calendar.getTime()));
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                incDate.setText(sdf.format(calendar.getTime()));

            }
        };
        incDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewTransaction.this, datePickerDialog,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


    }

    public void setExpPreDate(){
        expDate = (TextView) findViewById(R.id.expDate);
        calendar = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd";

        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);
        expDate.setText(sdf.format(calendar.getTime()));

        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                expDate.setText(sdf.format(calendar.getTime()));

            }
        };
        expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewTransaction.this, datePickerDialog,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                //preDate = new Date(sdf.format(calendar.getTime()));
            }
        });
    }


    public Date getDate() {

        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = new Date(dateFormat.format(newCalendar.getTime()));
        return currDate;
    }

//    expense----------------------------------------------------------------------------------------------------------------------------------



    public void setUpExpenseSpinner() {

//        setup type spinner
        expTypeSpinner = (Spinner) findViewById(R.id.expenseTypeSpinner);
        purchaseTypeList = new PurchaseTypeDAO(getApplicationContext());
        List<String> expenseTypeList = purchaseTypeList.getALLExpenseType();         //list get at start

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
        expDate = (TextView) findViewById(R.id.expDate);

        expButton = (Button) findViewById(R.id.addExpenseButton);
        expAmount = (EditText) findViewById(R.id.expenseAmount);
        expAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        expAccountSpinner = (Spinner) findViewById(R.id.expenseAccountSpinner);
        expTypeSpinner = (Spinner) findViewById(R.id.expenseTypeSpinner);
        expNote = (EditText) findViewById(R.id.expenseNotes);
        expSwitch = (Switch) findViewById(R.id.switch1);
        PS1 = (TextView) findViewById(R.id.textView1);
        line1 = findViewById(R.id.line1);
        currDate = getDate();




        expButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (errCheck("expense")) {
                    double Amount = Double.parseDouble(expAmount.getText().toString());
                    String accountName = expAccountSpinner.getSelectedItem().toString();
                    String Note = expNote.getText().toString();
                    String Type = expTypeSpinner.getSelectedItem().toString();
                    //Amount = checkIsCredit(accounts.getAccount(accountName).getType(), Amount);
                    TransactionDAO TransDAO = new TransactionDAO(getApplicationContext());
                    //new obj
                    Transaction newTrans = new Transaction(currDate, Amount, accountName, Note, Type);
                    //insert
                    TransDAO.addTrans(newTrans);
                    //back to main
                    Context context = getApplicationContext();
                    CharSequence text = "Success";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            }
        });

        expSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    setVisibilityOFF(expDate, PS1, line1);
                    expButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (errCheck("expense")) {
                                double Amount = Double.parseDouble(expAmount.getText().toString());
                                String accountName = expAccountSpinner.getSelectedItem().toString();
                                String Note = expNote.getText().toString();
                                String Type = expTypeSpinner.getSelectedItem().toString();
                                //Amount = checkIsCredit(accounts.getAccount(accountName).getType(), Amount);
                                TransactionDAO TransDAO = new TransactionDAO(getApplicationContext());
                                //new obj
                                Transaction newTrans = new Transaction(currDate, Amount, accountName, Note, Type);
                                //insert
                                TransDAO.addTrans(newTrans);
                                //back to main
                                Context context = getApplicationContext();
                                CharSequence text = "Success";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                finish();
                            }
                        }
                    });
                } else {
                    setVisibilityON(expDate, PS1, line1);
                    setExpPreDate();
                    expButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            preDate = new Date(expDate.getText().toString());
                            if (errCheckPer("expense")) {
                                double Amount;
                                if (expAmount.getText().toString().equals("")) {
                                    Amount = 0;
                                }
                                else{
                                Amount = Double.parseDouble(expAmount.getText().toString());}

                                String accountName = expAccountSpinner.getSelectedItem().toString();
                                String Note = expNote.getText().toString();
                                String Type = expTypeSpinner.getSelectedItem().toString();
                                PAPDAO PAPDAO = new PAPDAO(getApplicationContext());

                                //Amount = checkIsCredit(accounts.getAccount(accountName).getType(), Amount);

                                //Create Transaction obj
                                Transaction newTrans = new Transaction(preDate, Amount, accountName, Note, Type);
                                //Create Autodesc obj
                                if (PAPDAO.addAutoDesc(newTrans)) {
                                    //Finish
                                    Context context = getApplicationContext();
                                    CharSequence text = "Success";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    finish();
                                }
                            }
                        }
                    });


                }
            }
        });


    }


    //    income----------------------------------------------------------------------------------------------------------------------------------


    public void setUpIncomeSpinner() {

//        setup type spinner
        incTypeSpinner = (Spinner) findViewById(R.id.incomeTypeSpinner);
        List<String> incomeTypeL = purchaseTypeList.getAllIncomeType();         //list get at start

        List<String> typeSpinnerList = new ArrayList<>();                       //list add to spinner
        typeSpinnerList.add("----Select Income Type--------------------------------");
        for (int i = 0; i < incomeTypeL.size(); i++) {
            typeSpinnerList.add(incomeTypeL.get(i));
        }
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
        incDate = (TextView) findViewById(R.id.incDate);

        incButton = (Button) findViewById(R.id.addIncomeButton);
        incAmount = (EditText) findViewById(R.id.incomeAmount);
        incAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        incAccountSpinner = (Spinner) findViewById(R.id.incomeAccountSpinner);
        incTypeSpinner = (Spinner) findViewById(R.id.incomeTypeSpinner);
        incNote = (EditText) findViewById(R.id.incomeNotes);
        incSwitch = (Switch) findViewById(R.id.switch2);
        PS2 = (TextView) findViewById(R.id.textView2);
        line2 = findViewById(R.id.line2);
        currDate = getDate();

        incButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (errCheck("income")) {
                    double Amount = Double.parseDouble(incAmount.getText().toString());
                    String accountName = incAccountSpinner.getSelectedItem().toString();
                    String Note = incNote.getText().toString();
                    String Type = incTypeSpinner.getSelectedItem().toString();
                    //In database, negative means income
                    Amount *= -1;
                    //Amount = checkIsCredit(accounts.getAccount(accountName).getType(), Amount);
                    TransactionDAO TransDAO = new TransactionDAO(getApplicationContext());
                    //new obj
                    Transaction newTrans = new Transaction(currDate, Amount, accountName, Note, Type);
                    //insert
                    TransDAO.addTrans(newTrans);
                    Context context = getApplicationContext();
                    CharSequence text = "Success";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            }
        });

        incSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    setVisibilityOFF(incDate, PS2, line2);
                    incButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (errCheck("income")) {
                                double Amount = Double.parseDouble(incAmount.getText().toString());
                                String accountName = incAccountSpinner.getSelectedItem().toString();
                                String Note = incNote.getText().toString();
                                String Type = incTypeSpinner.getSelectedItem().toString();
                                //In database, negative means income
                                Amount *= -1;
                                //Amount = checkIsCredit(accounts.getAccount(accountName).getType(), Amount);
                                TransactionDAO TransDAO = new TransactionDAO(getApplicationContext());
                                //new obj
                                Transaction newTrans = new Transaction(currDate, Amount, accountName, Note, Type);
                                //insert
                                TransDAO.addTrans(newTrans);
                                Context context = getApplicationContext();
                                CharSequence text = "Success";
                                int duration = Toast.LENGTH_SHORT;
                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                                finish();
                            }
                        }
                    });
                } else {
                    setVisibilityON(incDate, PS2, line2);
                    setIncPreDate();
                    incButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            preDate = new Date(incDate.getText().toString());
                            if (errCheckPer("income")) {
                                double Amount;
                                if (incAmount.getText().toString().equals("")) {
                                    Amount = 0;
                                }
                                else {
                                Amount = Double.parseDouble(incAmount.getText().toString());}
                                String accountName = incAccountSpinner.getSelectedItem().toString();
                                String Note = incNote.getText().toString();
                                String Type = incTypeSpinner.getSelectedItem().toString();
                                PAPDAO PAPDAO = new PAPDAO(getApplicationContext());
                                //Amount = checkIsCredit(Type, Amount);
                                //In database, negative means income
                                Amount *= -1;
                                Amount = checkIsCredit(accounts.getAccount(accountName).getType(), Amount);

                                //Create Transaction obj
                                Transaction newTrans = new Transaction(preDate, Amount, accountName, Note, Type);
                                //Create Autodesc obj
                                if (PAPDAO.addAutoDesc(newTrans)) {
                                    //Finish
                                    Context context = getApplicationContext();
                                    CharSequence text = "Success";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(context, text, duration);
                                    toast.show();
                                    finish();
                                }
                            }
                        }
                    });


                }
            }
        });


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
            if (incAmount.getText().toString().equals("")) {
                text = "Please enter amount.";
                noErr = false;
            }

            if (incTypeSpinner.getSelectedItem().toString().equals("----Select Income Type--------------------------------")) {
                text = "Please select a type.";
                noErr = false;
            }
            if (incAccountSpinner.getSelectedItem().toString().equals("----Select Account---------------------------------------")) {
                text = "Please select a accountinfo.";
                noErr = false;
            }

        }
        if (!noErr) {
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        }

        return noErr;
    }

    public boolean errCheckPer(String transType) {

        Context context = getApplicationContext();
        boolean noErr = true;
        CharSequence text = "";

        if (transType.equals("expense")) {
            if (preDate.compareTo(currDate) != 1) {
                text = "Please check your first due day.";
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
            if (preDate.compareTo(currDate) != 1) {
                text = "Please check your first due day.";
                noErr = false;
            }
            if (incAccountSpinner.getSelectedItem().toString().equals("----Select Account---------------------------------------")) {
                text = "Please select a accountinfo.";
                noErr = false;
            }
            if (incTypeSpinner.getSelectedItem().toString().equals("----Select Income Type--------------------------------")) {
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

    public void setVisibilityON(TextView date, TextView PS, View line) {
        date.setVisibility(View.VISIBLE);
        PS.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
    }

    public void setVisibilityOFF(TextView date, TextView PS, View line) {
        date.setVisibility(View.INVISIBLE);
        PS.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);
    }

    public double checkIsCredit(String accountType,double amount){
        if (accountType.equals("Credit Card")){
            amount *= -1;
        }
        return amount;
    }

}