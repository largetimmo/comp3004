package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
        transaction.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Transaction.class);
                startActivity(intent);
            }
        });
        accounts.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), Account.class);
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
    }

}
