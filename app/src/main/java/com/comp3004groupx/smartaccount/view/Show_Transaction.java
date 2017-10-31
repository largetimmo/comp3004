package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;
import com.comp3004groupx.smartaccount.module.DecimalDigitsInputFilter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuguanhong on 2017-10-28.
 */

public class Show_Transaction extends AppCompatActivity {

    PurchaseTypeDAO purchaseTypeList;
    AccountDAO accounts;
    TransactionDAO transactionList;
    Transaction tran;
    Spinner yearSpinner;
    Spinner monthSpinner;
    Spinner daySpinner;
    TextView title;
    EditText amount;
    Spinner purchaseTypeSpinner;
    Spinner accountSpinner;
    EditText notes;
    Button saveButton;
    Button deleteButton;
    DecimalFormat decimalFormat;
    List<Integer> Day = new ArrayList<>();
    int status = 0;  //0 == purchase, 1 == income



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_transaction);
        decimalFormat = new DecimalFormat("0.00");
        //Get button
        saveButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);

        //Get tran from last activity
        Intent intent = getIntent();
        int tranId = intent.getIntExtra("ID",0);
        transactionList = new TransactionDAO(getApplicationContext());
        tran = transactionList.getTransByID(tranId);


        //Check this tran is purchase or income
        status = checkTransactionStatus(tran.getAmount());

        //Init Type Spinner
        purchaseTypeSpinner = (Spinner) findViewById(R.id.purchaseTypeSpinner);
        purchaseTypeList = new PurchaseTypeDAO(getApplicationContext());
        if (status == 0){
            //Init purchaseTypeSpinner
            List<String> purchaseTypes = purchaseTypeList.getALLExpenseType();
            setTypeSpinner(purchaseTypeSpinner, purchaseTypes);
        }
        else if (status == 1){
            List<String > incomeTypes = purchaseTypeList.getAllIncomeType();
            setTypeSpinner(purchaseTypeSpinner, incomeTypes);
        }

        //Init DateSpinner
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        setDateSpinner(yearSpinner, monthSpinner, daySpinner);



        //Init accountSpinner
        accountSpinner = (Spinner) findViewById(R.id.accountSpinner);
        accounts = new AccountDAO(getApplicationContext());
        ArrayList<Account> accountTypes = accounts.getAllAccount();
        List<String> accountsNames = new ArrayList<>();
        for (int i =0; i<accountTypes.size(); i++){
            accountsNames.add(accountTypes.get(i).getName());
        }
        setTypeSpinner(accountSpinner,accountsNames);

        //Set Title
        title = (TextView) findViewById(R.id.title);
        if (status == 0){
            title.setText("Edit Expense Transaction");
        }
        else if (status == 1){
            title.setText("Edit Income Transaction");
        }

        //Set amount
        amount = (EditText) findViewById(R.id.amount);
        amount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        if (status == 0){
            amount.setText(decimalFormat.format(tran.getAmount()));
        }
        else {
            amount.setText(decimalFormat.format(-1*tran.getAmount()));
        }

        //Set notes
        notes = (EditText) findViewById(R.id.notes);
        if (tran.getNote().equals("")){
            notes.setHint("Notes");
        }
        else {
            notes.setText(tran.getNote());
        }

        //Set PurchaseTypeSpinner
        int purchaseTypePosition = getTypePosition(purchaseTypeSpinner, tran.getType());
        purchaseTypeSpinner.setSelection(purchaseTypePosition);

        //Set accountSpinner
        int accountPosition = getTypePosition(accountSpinner, tran.getAccount());
        accountSpinner.setSelection(accountPosition);

        //Set yearSpinner
        int yearPosition = getTypePosition(yearSpinner, Integer.toString(tran.getDate().getYear()));
        yearSpinner.setSelection(yearPosition);

        //Set monthSpinner
        int monthPosition = getTypePosition(monthSpinner, Integer.toString(tran.getDate().getMonth()));
        monthSpinner.setSelection(monthPosition);

        //Set daySpinner
        int check = daySpinner.getAdapter().getCount();  //Debug code
        int dayPosition = getTypePosition(daySpinner,Integer.toString(tran.getDate().getDay()));
        daySpinner.setSelection(dayPosition);


        //Update Transaction
        updateTransaction();

        //Delete Transaction
        deleteTransaction();





    }


    public int checkTransactionStatus(double amount){
          if (amount > 0) {
              return 0;
          }
          else {
              return 1;
          }
    }
    public void setYearSpinner(Spinner YearSpinner){
        List<Integer> Year = new ArrayList<>();
        for (int i=2017;i<2049;i++){
            Year.add(i);
        }
        ArrayAdapter<Integer> YearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Year);
        YearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YearSpinner.setAdapter(YearAdapter);
    }
    public void setMonthSpinner(Spinner MonthSpinner){
        List<Integer> Month = new ArrayList<>();
        for (int i=1;i<=12;i++){
            Month.add(i);
        }
        ArrayAdapter<Integer> MonthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Month);
        MonthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MonthSpinner.setAdapter(MonthAdapter);
    }
    public void setDayspinner(Spinner MonthSpinner, final Spinner DaySpinner) {
        int Month = Integer.parseInt(MonthSpinner.getSelectedItem().toString());
        if (Month == 1 || Month == 3 || Month == 5 || Month == 7 || Month == 8 || Month == 10 || Month == 12) {
            for (int i = 1; i <= 31; i++) {
                Day.add(i);
            }
        }
        else if (Month == 2){
            for (int i=1;i<=28;i++){
                Day.add(i);
            }
        }
        else{
            for (int i=1;i<=30;i++){
                Day.add(i);
            }
        }
        final ArrayAdapter<Integer> DayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Day);
        DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        DaySpinner.setAdapter(DayAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Day.clear();
                //daySpinner.setSelection(0);
                String selectedItem = monthSpinner.getSelectedItem().toString();
                int Month = Integer.parseInt(selectedItem);
                if (Month == 1 || Month == 3 || Month == 5 || Month == 7 || Month == 8 || Month == 10 || Month == 12) {
                    for (int i = 1; i <= 31; i++) {
                        Day.add(i);
                    }
                    //DayAdapter.add(31);

                }
                else if (Month == 2){
                    for (int i=1;i<=28;i++){
                        Day.add(i);
                    }
                    DayAdapter.remove(31);
                    DayAdapter.remove(30);
                    DayAdapter.remove(29);
//                    DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    DaySpinner.setAdapter(DayAdapter);

                }
                else{
                    for (int i=1;i<=30;i++){
                        Day.add(i);
                    }
                    DayAdapter.remove(31);
//                    DayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    DaySpinner.setAdapter(DayAdapter);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }
    public void setDateSpinner(Spinner yearSpinner, Spinner monthSpinner, Spinner daySpinner){
        setYearSpinner(yearSpinner);
        setMonthSpinner(monthSpinner);
        setDayspinner(monthSpinner,daySpinner);
    }
    public void setTypeSpinner(Spinner TypeSpinner, List<String> Types){
        List<String> TypeSpinnerList = new ArrayList<>();
        for (int i = 0;i<Types.size(); i++){
            TypeSpinnerList.add(Types.get(i));
        }
        ArrayAdapter<String> typeDateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TypeSpinnerList);
        typeDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeSpinner.setAdapter(typeDateAdapter);
    }
    public int getTypePosition (Spinner TypeSpinner, String toComp){
        for (int i = 0; i<TypeSpinner.getAdapter().getCount(); i++){
            String checkS=TypeSpinner.getAdapter().getItem(i).toString();
            if(toComp.equals(TypeSpinner.getAdapter().getItem(i).toString())){
                int check =i;
                return i;
            }
        }
        return 0;
    }

    public void updateTransaction(){
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        amount = (EditText) findViewById(R.id.amount);
        purchaseTypeSpinner = (Spinner) findViewById(R.id.purchaseTypeSpinner);
        accountSpinner = (Spinner) findViewById(R.id.accountSpinner);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        notes = (EditText) findViewById(R.id.notes);
        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Date upDate = getDate(yearSpinner,monthSpinner,daySpinner);
                double upAmount = Double.parseDouble(amount.getText().toString());
                if (status == 1){
                    upAmount *= -1;
                }
                String upPurchaseType = purchaseTypeSpinner.getSelectedItem().toString();
                String upAccountName = accountSpinner.getSelectedItem().toString();
                String upNotes = notes.getText().toString();
                if (errorChecking(upAmount)){
                    tran.setAccount(upAccountName);
                    tran.setAmount(upAmount);
                    tran.setDate(upDate);
                    tran.setType(upPurchaseType);
                    tran.setNote(upNotes);
                    if (transactionList.modifyTrans(tran)) {
                        toast("Success");
                        finish();
                    }

                }
            }
        });
    }
    public void deleteTransaction(){
        deleteButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(transactionList.removeTrans(tran.getId())){
                    toast("Success");
                    finish();
                }
            }
        });
    }
    public boolean errorChecking (double upAmount){
        boolean noError = true;
        if (upAmount == 0){
            noError = false;
            toast("New Amount can't be 0");
        }
        return noError;
    }
    public Date getDate(Spinner yearSpinner, Spinner monthSpinner, Spinner daySpinner){
        int year, month, day;
        year = Integer.parseInt(yearSpinner.getSelectedItem().toString());
        month = Integer.parseInt(monthSpinner.getSelectedItem().toString());
        day = Integer.parseInt(daySpinner.getSelectedItem().toString());
        return new Date(year, month, day);
    }
    public void toast(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
