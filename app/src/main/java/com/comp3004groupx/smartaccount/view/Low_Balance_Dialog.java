package com.comp3004groupx.smartaccount.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;


import com.comp3004groupx.smartaccount.R;


/**
 * Created by wuguanhong on 2017-11-26.
 */

public class Low_Balance_Dialog extends Dialog {
    private TextView message;
    private Button okButton;
    private double balance;
    private onYesOnclickListener onYesOnclickListener;
    private String yesStr;

    public Low_Balance_Dialog(Context context){
        super(context, R.style.pap_dialog_style);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.low_balance_dialog);
        setCanceledOnTouchOutside(false);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();

        win.setAttributes(lp);

        initView();

        initData();

        initEvent();

    }

    private void initView(){
        message = (TextView) findViewById(R.id.message);
        okButton = (Button) findViewById(R.id.okButton);
    }

    private void initData(){
       if (balance != 0){
           message.setText("Sorry, your balance is " + Double.toString(balance) + ". That is a low balance notification. Thanks for using!");
       }
    }

    private void initEvent(){
        //设置确定按钮被点击后，向外界提供监听
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onYesOnclickListener != null) {
                    onYesOnclickListener.onYesClick();
                }
            }
        });
    }

    public void setBalance(double amount){
        balance = amount;
    }

    public void setYesOnclickListener(String str, Low_Balance_Dialog.onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.onYesOnclickListener = onYesOnclickListener;
    }

    public interface onYesOnclickListener{
        public void onYesClick();
    }

}
