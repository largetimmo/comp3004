package com.comp3004groupx.smartaccount.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.PAPDAO;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Alex Meng on 2017/11/15.
 */

public class PAP_List extends AppCompatActivity{
    EditText date_to;
    LinearLayout pap_list;
    AccountDAO accountDAO;
    PAPDAO papDAO;
    PurchaseTypeDAO purchaseTypeDAO;
    boolean after_pause = false;
    DecimalFormat decimalFormat;

    Calendar calendar;
    DatePickerDialog.OnDateSetListener datePickerDialog;
    //selection is for identify which edittext should be put value in
    //1 for date_to
    int selection = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pap_list);

        decimalFormat = new DecimalFormat("0.00");

        //link to widgets
        Button search_button = (Button)findViewById(R.id.pap_list_search);
        date_to = (EditText) findViewById(R.id.pap_list_date_to);
        pap_list = (LinearLayout) findViewById(R.id.pap_list_detail);

        //initialize DAOs
        accountDAO = new AccountDAO(getApplicationContext());
        papDAO = new PAPDAO(getApplicationContext());
        purchaseTypeDAO = new PurchaseTypeDAO(getApplicationContext());

        //set datepicker
        calendar = Calendar.getInstance();
        String myFormat = "yyyy-MM-dd";

        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);
        date_to.setText(sdf.format(calendar.getTime()));
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                if (selection == 1){
                    date_to.setText(sdf.format(calendar.getTime()));
                }
            }
        };

        date_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selection = 1;
                new DatePickerDialog(PAP_List.this,datePickerDialog,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
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
            Date to = new Date(date_to.getText().toString());

                ArrayList<Transaction> allpap = papDAO.getPAPBefore(to);
                pap_list.removeAllViews();
                for (Transaction t: allpap){
                    final LinearLayout parent = new LinearLayout(getApplicationContext());
                    parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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

                    if (t.getAmount() >0){
                        amount.setText(decimalFormat.format(t.getAmount()));
                        amount.setTextColor(getResources().getColor(R.color.red));
                    }else{
                        amount.setText(decimalFormat.format(-1*t.getAmount()));
                        amount.setTextColor(getResources().getColor(R.color.green));
                    }

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
                            Intent intent = new Intent(v.getContext(),Edit_PAP.class);
                            intent.putExtra("ID",parent.getLabelFor());
                            startActivity(intent);
                        }
                    });
                    pap_list.addView(parent);
                    View line = new View(getApplicationContext());
                    line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1));
                    line.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    pap_list.addView(line);
                }
    }
}
