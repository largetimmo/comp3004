package com.comp3004groupx.smartaccount.view;

/**
 * Created by wuguanhong on 2017-11-12.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.DialogInterface;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DecimalDigitsInputFilter;

import java.text.DecimalFormat;

public class Pap_Dialog extends Dialog {
    private Button save;
    private Button later;
    private TextView type;
    private TextView date;
    private EditText amount;
    private double amountDouble;
    private String typeStr;
    private String dateStr;
    DecimalFormat decimalFormat;

    private String yesStr, noStr;
    public onNoOnclickListener onNoOnclickListener;//取消按钮被点击了的监听器
    public onYesOnclickListener onYesOnclickListener;//确定按钮被点击了的监听器

    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.onNoOnclickListener = onNoOnclickListener;
    }

    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.onYesOnclickListener = onYesOnclickListener;
    }

    public Pap_Dialog(Context context){
        super(context, R.style.pap_dialog_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pap_dialog);
        setCanceledOnTouchOutside(false);

        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();

        win.setAttributes(lp);
        decimalFormat = new DecimalFormat("0.00");



        initView();

        initData();

        initEvent();
    }

    public void initView(){
        save = (Button) findViewById(R.id.save);
        later = (Button) findViewById(R.id.later);
        amount = (EditText) findViewById(R.id.amount);
        date = (TextView) findViewById(R.id.date);
        type = (TextView) findViewById(R.id.type);
        amount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
    }

    public void initData(){
        if (typeStr != null){
            type.setText(typeStr);
        }
        if (dateStr != null){
            date.setText(dateStr);
        }
        if (Double.toString(amountDouble) != null){
            amount.setText(decimalFormat.format(amountDouble));
        }
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onYesOnclickListener != null) {
                    onYesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNoOnclickListener != null) {
                    onNoOnclickListener.onNoClick();
                }
            }
        });
    }


    public void setDate(String date){
        dateStr = date;
    }

    public void setType(String type){
        typeStr = type;
    }

    public void setAmount(double amount){
        amountDouble = amount;
    }

    public double getAmountDouble(){
        amountDouble = Double.parseDouble(amount.getText().toString());
        System.out.print("Test");
        return amountDouble;
    }


    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
