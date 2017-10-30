package com.comp3004groupx.smartaccount.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.IncomeTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by chenjunhao on 2017/10/29.
 */

public class Transaction_List extends AppCompatActivity {
    Spinner account_spinner;
    Spinner type_spinner;
    EditText date_from;
    EditText date_to;
    LinearLayout trans_list;
    AccountDAO accountDAO;
    TransactionDAO transactionDAO;
    IncomeTypeDAO incomeTypeDAO;
    PurchaseTypeDAO purchaseTypeDAO;
    boolean after_pause = false;

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePickerDialog;
    //selection is for identify which edittext should be put value in
    //0 for date_from
    //1 for date_to
    int selection = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list);
        //link to widgets
        Button search_button = (Button)findViewById(R.id.trans_list_search);
        account_spinner = (Spinner) findViewById(R.id.trans_list_account_list);
        type_spinner = (Spinner) findViewById(R.id.trans_list_type_list);
        date_from = (EditText) findViewById(R.id.trans_list_date_from);
        date_to = (EditText) findViewById(R.id.trans_list_date_to);
        trans_list = (LinearLayout) findViewById(R.id.trans_list_detail);

        //initialize DAOs
        accountDAO = new AccountDAO(getApplicationContext());
        transactionDAO = new TransactionDAO(getApplicationContext());
        incomeTypeDAO = new IncomeTypeDAO(getApplicationContext());
        purchaseTypeDAO = new PurchaseTypeDAO(getApplicationContext());

        //set spinner items
        ArrayList<Account> accounts = accountDAO.getAllAccount();
        ArrayList<String> account_names = new ArrayList<>();
        account_names.add("ALL");
        for (Account a : accounts){
            account_names.add(a.getName());
        }
        ArrayAdapter<String> account_list_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,account_names);
        account_list_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_spinner.setAdapter(account_list_adapter);

        ArrayList<String> types = new ArrayList<>();
        types.add("ALL");
        types.addAll(incomeTypeDAO.getAllTypes());
        types.addAll(purchaseTypeDAO.getalltypes());


        ArrayAdapter<String> type_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,types);
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(type_adapter);

        //set datepicker
        calendar = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd";

        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);
        date_from.setText(sdf.format(calendar.getTime()));
        date_to.setText(sdf.format(calendar.getTime()));
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(calendar.YEAR,year);
                calendar.set(calendar.MONTH,month);
                calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);


                if (selection == 0){
                    date_from.setText(sdf.format(calendar.getTime()));
                }else if (selection == 1){
                    date_to.setText(sdf.format(calendar.getTime()));
                }
            }
        };
        date_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 0;
                new DatePickerDialog(Transaction_List.this,datePickerDialog,calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 1;
                new DatePickerDialog(Transaction_List.this,datePickerDialog,calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //set search feature
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshList();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (after_pause){
            refreshList();
            after_pause = false;
        }

    }
    @Override
    protected void onPause(){
        super.onPause();
        after_pause = true;
    }
    private void refreshList(){
        if (date_from.getText().toString().equals("") || date_to.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Date Range is invalid",Toast.LENGTH_SHORT).show();
        }else{
            Date from = new Date(date_from.getText().toString());
            Date to = new Date(date_to.getText().toString());
            if (to.compareTo(from)<0){
                Toast toast = Toast.makeText(getApplicationContext(),"Date range is invalid",Toast.LENGTH_SHORT);
                toast.show();
            }else{
                ArrayList<Transaction> alltrans = transactionDAO.getAllTransaction(from,to,account_spinner.getSelectedItem().toString(),type_spinner.getSelectedItem().toString());
                trans_list.removeAllViews();
                for (Transaction t: alltrans){
                    final LinearLayout parent = new LinearLayout(getApplicationContext());
                    parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,120));
                    parent.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout top = new LinearLayout(getApplicationContext());
                    LinearLayout bottom = new LinearLayout(getApplicationContext());
                    top.setOrientation(LinearLayout.HORIZONTAL);
                    bottom.setOrientation(LinearLayout.HORIZONTAL);
                    top.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,2));
                    bottom.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
                    TextView type = new TextView(getApplicationContext());
                    TextView amount = new TextView(getApplicationContext());
                    TextView account_name = new TextView(getApplicationContext());
                    TextView date = new TextView(getApplicationContext());
                    type.setText(t.getType());
                    //TODO: identify income or outcome
                    amount.setText(Double.toString(t.getAmount()));
                    account_name.setText(t.getAccount());
                    date.setText(t.getDate().toString());

                    type.setTextSize(20);
                    amount.setTextSize(25);

                    top.addView(type,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    top.addView(amount,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    bottom.addView(account_name,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));
                    bottom.addView(date,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1));

                    parent.addView(top);
                    parent.addView(bottom);
                    parent.setLabelFor(t.getId());
                    parent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(v.getContext(),Show_Transaction.class);
                            intent.putExtra("ID",parent.getLabelFor());
                            startActivity(intent);
                        }
                    });
                    trans_list.addView(parent);
                    View line = new View(getApplicationContext());
                    line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                    line.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    trans_list.addView(line);
                }
            }
        }
    }
}
