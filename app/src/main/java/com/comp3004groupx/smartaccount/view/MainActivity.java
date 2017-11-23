package com.comp3004groupx.smartaccount.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3004groupx.smartaccount.Core.Date;
import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.PAPDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;
import com.comp3004groupx.smartaccount.view.Pap_Dialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    TextView transaction;
    TextView accounts;
    TextView setting;
    TextView statistics;
    Button newTrans;
    TextView income;
    TextView cost;
    TransactionDAO transactionDAO;
    PAPDAO papDAO;
    Pap_Dialog pap_dialog;
    int number = 0;
    ArrayList<Transaction> papTransaction;
    ArrayList<Transaction> papTransaction2;
    DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transaction = (TextView) findViewById(R.id.main_trans);
        accounts = (TextView) findViewById(R.id.main_account);
        setting = (TextView) findViewById(R.id.main_setting);
        statistics = (TextView) findViewById(R.id.main_statistic);
        newTrans = (Button) findViewById(R.id.newTrans);
        income = (TextView) findViewById(R.id.income);
        cost = (TextView) findViewById(R.id.cost);
        transactionDAO = new TransactionDAO(getApplicationContext());
        decimalFormat = new DecimalFormat("0.00");


        checkPAP();


        //debug start--------------------------------------------------------------------------------------
        TextView title = (TextView) findViewById(R.id.main_title);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DebugDatabase.class);
                startActivity(intent);
            }
        });
        //debug end--------------------------------------------------------------------------------------

        transaction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Transaction_List.class);
                startActivity(intent);
            }
        });
        accounts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Account_List.class);
                startActivity(intent);
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Setting.class);
                startActivity(intent);
            }
        });
        statistics.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Statistics.class);
                startActivity(intent);
            }
        });

        newTrans.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NewTransaction.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        income.setText(decimalFormat.format(transactionDAO.getTotalIncome()));
        cost.setText(decimalFormat.format(transactionDAO.getTotalSpend()));
    }

    private void checkPAP() {
        Date checkDate;
        createPAPTrans();
        checkDate = getCheckDate();
        papDAO = new PAPDAO(getApplicationContext());
        papTransaction = papDAO.getUncheckedPAPBefore(checkDate);
        if (papTransaction.size() != 0) {
            setDialog( );
        }
    }

    private void createPAPTrans(){

        papDAO = new PAPDAO(getApplicationContext());
        papTransaction2 = papDAO.getCheckedPAP();
        if (papTransaction2.size()!=0){
            Date currDate = getDate();
            for (int i = 0;i<papTransaction2.size();i++){
                if (currDate.compareTo(papTransaction2.get(i).getDate()) != -1){
                    transactionDAO.addTrans(papTransaction2.get(i));
                    papTransaction2.get(i).getDate().plusDate(30);
                    papDAO.removeAutoDesc(papTransaction2.get(i).getId());
                    papDAO.addAutoDesc(papTransaction2.get(i));
                    toast("Created a transaction for you.");
                }
            }
        }
    }

    private Pap_Dialog setDialog() {
        pap_dialog = new Pap_Dialog(MainActivity.this);
        pap_dialog.setAmount(papTransaction.get(number).getAmount());
        pap_dialog.setDate(papTransaction.get(number).getDate().toString());
        pap_dialog.setType(papTransaction.get(number).getType());
        pap_dialog.setYesOnclickListener("Save", new Pap_Dialog.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                papDAO.checkPAP(papTransaction.get(number).getId());
                papTransaction.get(number).setAmount(pap_dialog.getAmountDouble());
                papDAO.modifyAutoDesc(papTransaction.get(number));
                toast("Save successfully!");
                number++;
                if (papTransaction.size() > number) {
                    pap_dialog.dismiss();
                    setDialog();
                } else {
                    pap_dialog.dismiss();
                }

            }
        });
        pap_dialog.setNoOnclickListener("Later", new Pap_Dialog.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                toast("Let's check next time.");
                number++;
                if (papTransaction.size() > number) {
                    pap_dialog.dismiss();
                    setDialog();
                } else {
                    pap_dialog.dismiss();
                }
            }
        });
        pap_dialog.show();
        return  pap_dialog;
    }

    private Date getCheckDate() {

        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = new Date(dateFormat.format(newCalendar.getTime()));
        currDate.plusDate(5);
        return currDate;
    }

    private void toast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private Date getDate() {
        Calendar newCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currDate = new Date(dateFormat.format(newCalendar.getTime()));
        return currDate;
    }
}
