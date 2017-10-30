package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.text.InputFilter;
import android.view.View;


import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
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
import com.comp3004groupx.smartaccount.module.DAO.PAPDAO;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;

/**
 * Created by devray on 2017-09-16.
 * Taken by devray.
 */


public class NewTransaction extends AppCompatActivity {


    AccountDAO accounts;
    PurchaseTypeDAO purchaseTypeList;

    TabHost host;

    Spinner expYearSpinner;
    Spinner expMonthSpinner;
    Spinner expDaySpinner;
    Spinner incDaySpinner;
    Spinner incYearSpinner;
    Spinner incMonthSpinner;

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
    Date perDate;

    List<Integer> Day = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.newtransaction);

        setupTabHost();
//        clearDefaultValue();


        setExpDate();
        setUpExpenseSpinner();
        addExpense();

        setIncDate();
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

    public void setYearSpinner(Spinner YearSpinner) {
        List<Integer> Year = new ArrayList<>();
        for (int i = 2017; i < 2049; i++) {
            Year.add(i);
        }
        ArrayAdapter<Integer> YearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Year);
        YearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearSpinner.setAdapter(YearAdapter);
    }

    public void setMonthSpinner(Spinner MonthSpinner) {
        List<Integer> Month = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            Month.add(i);
        }
        ArrayAdapter<Integer> MonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Month);
        MonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MonthSpinner.setAdapter(MonthAdapter);
    }

    public void setDayspinner(Spinner MonthSpinner, Spinner DaySpinner) {
        final Spinner monthSpinner = MonthSpinner;
        final Spinner daySpinner = DaySpinner;
        for (int i = 0; i < 31; i++) {
            Day.add(i);
        }
        MonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Day.clear();
                Day.add(1);
                daySpinner.setSelection(0);
                String selectedItem = monthSpinner.getSelectedItem().toString();
                int Month = Integer.parseInt(selectedItem);

                if (Month == 1 || Month == 3 || Month == 5 || Month == 7 || Month == 8 || Month == 10 || Month == 12) {
                    for (int i = 2; i <= 31; i++) {
                        Day.add(i);
                    }
                } else if (Month == 2) {
                    for (int i = 2; i <= 28; i++) {
                        Day.add(i);
                    }
                } else {
                    for (int i = 2; i <= 30; i++) {
                        Day.add(i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<Integer> DayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Day);
        DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DaySpinner.setAdapter(DayAdapter);

    }

    public Date getPerDate(Spinner YearSpinner, Spinner MonthSpinner, Spinner DaySpinner) {
        int year, month, day;
        year = Integer.parseInt(YearSpinner.getSelectedItem().toString());
        month = Integer.parseInt(MonthSpinner.getSelectedItem().toString());
        day = Integer.parseInt(DaySpinner.getSelectedItem().toString());
        Date perDate = new Date(year, month, day);
        return perDate;
    }

    public Date getDate() {

        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String[] date = dateFormat.format(newCalendar.getTime()).split(" ");

        int year, month, day;

        year = Integer.parseInt(date[2]);

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
        day = Integer.parseInt(date[0]);
        Date currDate = new Date(year, month, day);
        return currDate;
    }

//    expense----------------------------------------------------------------------------------------------------------------------------------

    public void setExpDate() {
        expYearSpinner = (Spinner) findViewById(R.id.expYearSpinner);
        expMonthSpinner = (Spinner) findViewById(R.id.expMonthSpinner);
        expDaySpinner = (Spinner) findViewById(R.id.expDaySpinner);
        setYearSpinner(expYearSpinner);
        setMonthSpinner(expMonthSpinner);
        setDayspinner(expMonthSpinner, expDaySpinner);
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
        expYearSpinner = (Spinner) findViewById(R.id.expYearSpinner);
        expMonthSpinner = (Spinner) findViewById(R.id.expMonthSpinner);
        expDaySpinner = (Spinner) findViewById(R.id.expDaySpinner);

        expButton = (Button) findViewById(R.id.addExpenseButton);
        expAmount = (EditText) findViewById(R.id.expenseAmount);
        expAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        expAccountSpinner = (Spinner) findViewById(R.id.expenseAccountSpinner);
        expTypeSpinner = (Spinner) findViewById(R.id.expenseTypeSpinner);
        expNote = (EditText) findViewById(R.id.expenseNotes);
        expSwitch = (Switch) findViewById(R.id.switch1);
        PS1 = (TextView) findViewById(R.id.textView1);
        line1 = (View) findViewById(R.id.line1);
        currDate = getDate();
        perDate = getPerDate(expYearSpinner, expMonthSpinner, expDaySpinner);

        expButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (errCheck("expense")) {
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
                    setVisibilityOFF(expYearSpinner, expMonthSpinner, expDaySpinner, PS1, line1);
                    expButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (errCheck("expense")) {
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
                    setVisibilityON(expYearSpinner, expMonthSpinner, expDaySpinner, PS1, line1);
                    expButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (errCheckPer("expense")) {
                                double Amount = Double.parseDouble(expAmount.getText().toString());
                                String accountName = expAccountSpinner.getSelectedItem().toString();
                                String Note = expNote.getText().toString();
                                String Type = expTypeSpinner.getSelectedItem().toString();
                                PAPDAO PAPDAO = new PAPDAO(getApplicationContext());
                                if (expAmount.getText().toString().equals("")) {
                                    Amount = 0;
                                }
                                //Create Transaction obj
                                Transaction newTrans = new Transaction(perDate, Amount, accountName, Note, Type);
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
    public void setIncDate() {
        incYearSpinner = (Spinner) findViewById(R.id.incYearSpinner);
        incMonthSpinner = (Spinner) findViewById(R.id.incDaySpinner);
        incDaySpinner = (Spinner) findViewById(R.id.incMonthSpinner);
        setYearSpinner(incYearSpinner);
        setMonthSpinner(incMonthSpinner);
        setDayspinner(incMonthSpinner, incDaySpinner);
    }

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
        incYearSpinner = (Spinner) findViewById(R.id.incYearSpinner);
        incMonthSpinner = (Spinner) findViewById(R.id.incDaySpinner);
        incDaySpinner = (Spinner) findViewById(R.id.incMonthSpinner);

        incButton = (Button) findViewById(R.id.addIncomeButton);
        incAmount = (EditText) findViewById(R.id.incomeAmount);
        incAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        incAccountSpinner = (Spinner) findViewById(R.id.incomeAccountSpinner);
        incTypeSpinner = (Spinner) findViewById(R.id.incomeTypeSpinner);
        incNote = (EditText) findViewById(R.id.incomeNotes);
        incSwitch = (Switch) findViewById(R.id.switch2);
        PS2 = (TextView) findViewById(R.id.textView2);
        line2 = (View) findViewById(R.id.line2);
        currDate = getDate();
        perDate = getPerDate(incYearSpinner, incMonthSpinner, incDaySpinner);

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
                    setVisibilityOFF(incYearSpinner, incMonthSpinner, incDaySpinner, PS2, line2);
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
                    setVisibilityON(incYearSpinner, incMonthSpinner, incDaySpinner, PS2, line2);
                    incButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (errCheckPer("income")) {
                                double Amount = Double.parseDouble(incAmount.getText().toString());
                                String accountName = incAccountSpinner.getSelectedItem().toString();
                                String Note = incNote.getText().toString();
                                String Type = incTypeSpinner.getSelectedItem().toString();
                                PAPDAO PAPDAO = new PAPDAO(getApplicationContext());
                                if (incAmount.getText().toString().equals("")) {
                                    Amount = 0;
                                }
                                //In database, negative means income
                                Amount *= -1;
                                //Create Transaction obj
                                Transaction newTrans = new Transaction(perDate, Amount, accountName, Note, Type);
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

    public boolean errCheckPer(String transType) {

        Context context = getApplicationContext();
        boolean noErr = true;
        CharSequence text = "";

        if (transType.equals("expense")) {
            if (perDate.compareTo(currDate) == 1) {
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

    public void setVisibilityON(Spinner YearSpinner, Spinner MonthSpinner, Spinner DaySpinner, TextView PS, View line) {
        YearSpinner.setVisibility(View.VISIBLE);
        DaySpinner.setVisibility(View.VISIBLE);
        MonthSpinner.setVisibility(View.VISIBLE);
        PS.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
    }

    public void setVisibilityOFF(Spinner YearSpinner, Spinner MonthSpinner, Spinner DaySpinner, TextView PS, View line) {
        DaySpinner.setVisibility(View.INVISIBLE);
        YearSpinner.setVisibility(View.INVISIBLE);
        MonthSpinner.setVisibility(View.INVISIBLE);
        PS.setVisibility(View.INVISIBLE);
        line.setVisibility(View.INVISIBLE);
    }


}