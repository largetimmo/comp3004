package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.comp3004groupx.smartaccount.R;

import android.view.View;
import android.widget.TextView;

/**
 * Created by devray on 2017-09-16.
 */

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        this.setTitle("Setting");
        TextView welcome = (TextView) findViewById(R.id.welcome);
        TextView setting = (TextView) findViewById(R.id.setting);
        TextView perpayment = (TextView) findViewById(R.id.perpayment);
        TextView purchaseM = (TextView) findViewById(R.id.purchasemanager);
        TextView accountM = (TextView) findViewById(R.id.accountmanager);

//        setting.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        perpayment.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        purchaseM.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//        accountM.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        perpayment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Per_Auth_Payment.class);
                startActivity(intent);
            }
        });
    }
}

