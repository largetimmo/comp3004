package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.comp3004groupx.smartaccount.Core.*;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    TextView transaction;
    TextView accounts;
    TextView setting;
    TextView statistics;
    Button newTrans;
    TextView income;
    TextView cost;
    TransactionDAO transactionDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transaction = (TextView)findViewById(R.id.main_trans);
        accounts = (TextView)findViewById(R.id.main_account);
        setting = (TextView)findViewById(R.id.main_setting);
        statistics = (TextView)findViewById(R.id.main_statistic);
        newTrans = (Button) findViewById(R.id.newTrans);
        income = (TextView)findViewById(R.id.income);
        cost = (TextView)findViewById(R.id.cost);
        transactionDAO = new TransactionDAO(getApplicationContext());
        income.setText(Double.toString(transactionDAO.getTotalIncome()));
        cost.setText(Double.toString(transactionDAO.getTotalSpend()));
        //DEBUG CODE STARTS HERE
        TextView title = (TextView) findViewById(R.id.main_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DebugDatabase.class);
                startActivity(intent);
            }
        });
        //Debug Code Ends Here


        transaction.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Transaction_List.class);
                startActivity(intent);
            }
        });
        accounts.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Add_Account.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Setting.class);
                intent.putExtra("ID",3);
                startActivity(intent);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Statistics.class);
                intent.putExtra("ID", 3);
                startActivity(intent);
            }
        });

        newTrans.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), NewTransaction.class);
                startActivity(intent);
            }
        });
        
    }

    @Override
    protected void onPause(){
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
        income.setText(Double.toString(transactionDAO.getTotalIncome()));
        cost.setText(Double.toString(transactionDAO.getTotalSpend()));
    }

}
