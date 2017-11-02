package com.comp3004groupx.smartaccount.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.comp3004groupx.smartaccount.R;

/**
 * Created by wuguanhong on 2017-11-01.
 */

public class User_Manager extends AppCompatActivity {
    EditText userName;
    EditText password;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_manager);

        userName = (EditText) findViewById(R.id.userName);
        userName.setText("Smart Account");
        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
