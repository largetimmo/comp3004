package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuguanhong on 2017-10-31.
 */

public class Edit_Purchase_Type extends AppCompatActivity {

    PurchaseTypeDAO purchaseTypeDAO;
    Switch swith;
    Spinner purchaseSpinner;
    EditText newTypeName;
    Button deleteButton;
    Button createButton;
    int status = 0;//0 is expense type, 1 is income type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_purchase_type);

        //This code is for refresh
        Intent intent = getIntent();
        status = intent.getIntExtra("key", 0);

        //Set switch status
        setSwitchStatus();

        //Delete or Create
        deletePurchaseType();
        createPurchaseType();


    }


    public void setExpenseSpinner() {
        purchaseSpinner = (Spinner) findViewById(R.id.purchaseSpinner);
        purchaseTypeDAO = new PurchaseTypeDAO(getApplicationContext());
        List<String> typeList = purchaseTypeDAO.getALLExpenseType();
        List<String> typeSpinnerList = new ArrayList<>();
        typeSpinnerList.add("Create a new expense type");
        for (int i = 0; i < typeList.size(); i++) {
            typeSpinnerList.add(typeList.get(i));
        }
        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purchaseSpinner.setAdapter(typeDataAdapter);
    }

    public void setIncomeSpinner() {
        purchaseSpinner = (Spinner) findViewById(R.id.purchaseSpinner);
        purchaseTypeDAO = new PurchaseTypeDAO(getApplicationContext());
        List<String> typeList = purchaseTypeDAO.getAllIncomeType();
        List<String> typeSpinnerList = new ArrayList<>();
        typeSpinnerList.add("Create a new income type");
        for (int i = 0; i < typeList.size(); i++) {
            typeSpinnerList.add(typeList.get(i));
        }
        ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, typeSpinnerList);
        typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        purchaseSpinner.setAdapter(typeDataAdapter);
    }

    public void setSwitchStatus() {
        swith = (Switch) findViewById(R.id.switch1);

        //Default is Expense Spinner
        setExpenseSpinner();

        if (status == 0) {
            setExpenseSpinner();
        } else {
            setIncomeSpinner();
        }

        swith.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    setIncomeSpinner();
                    status = 1;
                } else {
                    setExpenseSpinner();
                    status = 0;
                }
            }
        });

    }


    public void createPurchaseType() {
        createButton = (Button) findViewById(R.id.createButton);
        newTypeName = (EditText) findViewById(R.id.newTypeName);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == 0) {
                    purchaseTypeDAO.addPurchaseType(newTypeName.getText().toString());
                    toast("Sucess");
                    Intent intent = new Intent(view.getContext(), Edit_Purchase_Type.class);
                    intent.putExtra("key", status);
                    startActivity(intent);
                    finish();
                } else {
                    purchaseTypeDAO.addIncomeType(newTypeName.getText().toString());
                    toast("Sucess");
                    Intent intent = new Intent(view.getContext(), Edit_Purchase_Type.class);
                    intent.putExtra("key", status);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void deletePurchaseType() {
        deleteButton = (Button) findViewById(R.id.editButton);
        purchaseSpinner = (Spinner) findViewById(R.id.purchaseSpinner);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteErrorChecking()) {
                    if (status == 0) {
                        purchaseTypeDAO.removeType(purchaseSpinner.getSelectedItem().toString());
                        toast("Success");
                        Intent intent = new Intent(view.getContext(), Edit_Purchase_Type.class);
                        intent.putExtra("key", status);
                        startActivity(intent);
                        finish();
                    } else {
                        purchaseTypeDAO.removeType(purchaseSpinner.getSelectedItem().toString());
                        toast("Success");
                        Intent intent = new Intent(view.getContext(), Edit_Purchase_Type.class);
                        intent.putExtra("key", status);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

    }

    public boolean deleteErrorChecking() {
        boolean noError = true;
        purchaseSpinner = (Spinner) findViewById(R.id.purchaseSpinner);
        if (purchaseSpinner.getSelectedItem().toString().equals("Create a new expense type") || purchaseSpinner.getSelectedItem().toString().equals("Create a new income type")) {
            noError = false;
            toast("Please choose one type which you want to delete");
        }
        return noError;
    }

    public void toast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
