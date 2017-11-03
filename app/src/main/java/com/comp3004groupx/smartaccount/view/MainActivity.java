package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    TextView transaction;
    TextView accounts;
    TextView setting;
    TextView statistics;
    Button newTrans;
    TextView income;
    TextView cost;
    TransactionDAO transactionDAO;
    Button add;


    DecimalFormat decimalFormat;

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
        decimalFormat = new DecimalFormat("0.00");

        //debug start--------------------------------------------------------------------------------------
        TextView title = (TextView) findViewById(R.id.main_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DebugDatabase.class);
                startActivity(intent);
            }
        });
        //debug end--------------------------------------------------------------------------------------

        transaction.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Transaction_List.class);
                startActivity(intent);
            }
        });
        accounts.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Account_List.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Setting.class);
                startActivity(intent);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Statistics.class);
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
        income.setText(decimalFormat.format(transactionDAO.getTotalIncome()));
        cost.setText(decimalFormat.format(transactionDAO.getTotalSpend()));
    }

}
