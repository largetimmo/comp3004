package com.comp3004groupx.smartaccount.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AbstractDAO;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.AccountTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.PAPDAO;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjunhao on 2017/10/7.
 */

public class DebugDatabase extends Activity implements AdapterView.OnItemSelectedListener{
    List<String> database_name_list = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_debug);
        Spinner spinner = (Spinner) findViewById(R.id.database_spinier);
        database_name_list.add("ACCOUNT");
        database_name_list.add("ACCOUNTTYPE");
        database_name_list.add("AUTODESC");
        database_name_list.add("PURCHASETYPE");
        database_name_list.add("TRANS");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,database_name_list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        AbstractDAO dao = null;
        switch (position){
            case 0:
                dao = new AccountDAO(getApplicationContext());
                break;
            case 1:
                dao = new AccountTypeDAO(getApplicationContext());
                break;
            case 2:
                dao = new PAPDAO(getApplicationContext());
                break;
            case 3:
                dao = new PurchaseTypeDAO(getApplicationContext());
                break;
            case 4:
                dao = new TransactionDAO(getApplicationContext());
                break;
            default:
                dao = null;
                break;
        }

        update(dao.GETRAWDATA());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void update(ArrayList<ArrayList<String>> RAWDATA){
        TableLayout table = (TableLayout) findViewById(R.id.database_table);
        table.removeAllViews();
        boolean flag = true;
        int index = 0;
        while (flag){
            flag = false;
            TableRow row = new TableRow(this);
            for (ArrayList<String> col : RAWDATA){
                TextView textView = new TextView(this);
                try {
                    String text = col.get(index);
                    textView.setText(text);
                    flag = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
                row.addView(textView);
            }
            table.addView(row);
            index++;
        }
    }
}
