package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.UserDAO;

/**
 * Created by devray on 2017-09-16.
 */

public class Setting extends AppCompatActivity {
    UserDAO user;
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
        user = new UserDAO((getApplicationContext()));
        welcome.setText("Hi, " + user.getUP().first);

        perpayment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PAP_List.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),User_Manager.class);
                startActivity(intent);
            }
        });
        accountM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Account_List.class);
                startActivity(intent);
            }
        });

        purchaseM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Edit_Purchase_Type.class);
               // intent.putExtra("key", 0);
                startActivity(intent);
            }
        });
    }
}

