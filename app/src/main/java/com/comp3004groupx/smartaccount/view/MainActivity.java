package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.comp3004groupx.smartaccount.R;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView transaction = (TextView)findViewById(R.id.textView3);
        TextView accounts = (TextView)findViewById(R.id.textView4);
        TextView setting = (TextView)findViewById(R.id.textView);
        TextView statistics = (TextView)findViewById(R.id.textView2);
        Button newTrans = (Button) findViewById(R.id.button3);
        TextView balance = (TextView)findViewById(R.id.balance);
        TextView income = (TextView)findViewById(R.id.income);
        TextView cost = (TextView)findViewById(R.id.cost);

        //balance.setText();
        //DEBUG CODE STARTS HERE
        Button debug_button  = (Button) findViewById(R.id.DEBUG);
        debug_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DebugDatabase.class);
                startActivity(intent);
            }
        });
        //Add Account Button
        Button add_account = (Button)findViewById(R.id.Add);
        add_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),Add_Account.class);
                startActivity(intent);
            }
        });
        transaction.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Transaction_List_Account.class);
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

}
