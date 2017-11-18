package com.comp3004groupx.smartaccount.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.UserDAO;

/**
 * Created by wuguanhong on 2017-11-01.
 */

public class User_Manager extends AppCompatActivity {
    EditText userName;
    EditText password;
    Button saveButton;
    UserDAO user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_manager);
        password = (EditText) findViewById(R.id.password);
        userName = (EditText) findViewById(R.id.userName);
        userName.setText("Smart Account");
        password = (EditText) findViewById(R.id.password);
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName = userName.getText().toString();
                String passWord = password.getText().toString();
                user = new UserDAO(getApplicationContext());
                Pair<String,String> userInformation = new Pair<String, String>(uName, passWord);
                user.setUP(userInformation);
                finish();
            }
        });
    }
}
