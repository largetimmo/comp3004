package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.UserDAO;

/**
 * Created by chenjunhao on 2017/11/12.
 */

public class LoginPage extends AppCompatActivity {
    EditText username;
    EditText password;
    Button submit;
    UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        submit = (Button) findViewById(R.id.login_login);
        userDAO = new UserDAO(getApplicationContext());
        if(userDAO.getUP().first==null ) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_text = username.getText().toString();
                String password_text = password.getText().toString();
                Pair<String,String> UP = userDAO.getUP();
                if(UP.first.equals(username_text) && UP.second.equals(password_text)){
                    Intent intent = new Intent(v.getContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast toast = new Toast(v.getContext());
                    toast.setText("Your Username/Password Pair is not correct");
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }
}
