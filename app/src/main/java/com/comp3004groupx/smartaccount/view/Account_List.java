package com.comp3004groupx.smartaccount.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.comp3004groupx.smartaccount.R;
/**
 * Created by wuguanhong on 2017-09-18.
 */

public class Account_List extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_list);
        this.setTitle("Accounts_List");
    }

}
