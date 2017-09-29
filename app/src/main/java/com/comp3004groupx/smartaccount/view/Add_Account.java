package com.comp3004groupx.smartaccount.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.comp3004groupx.smartaccount.R;
/**
 * Created by wuguanhong on 2017-09-20.
 */

public class Add_Account extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        this.setTitle("Add a New Account");
        Button ok = (Button) findViewById(R.id.button);
        EditText accountName = (EditText)findViewById(R.id.editText);
        EditText password = (EditText)findViewById(R.id.editText2);
        EditText confirmPassword = (EditText)findViewById(R.id.editText3);
        /*ok.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {

                    }
                });*/
    }
}
