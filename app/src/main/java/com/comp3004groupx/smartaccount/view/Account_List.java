package com.comp3004groupx.smartaccount.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.AccountTypeDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by wuguanhong on 2017-09-18.
 * tacken by devray
 */

public class Account_List extends AppCompatActivity {


    AccountDAO accountDataBase;
    AccountTypeDAO accountTypeDataBase;

    TextView balanceNumber;
    TextView debtNumber;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    List<String> accountTypeHeader;
    HashMap<String, List<List<String>>> accountListItems;

    ImageButton addAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_list);
        //load content here
        setupOverView();
        setupExpList();
        //add account event
        addAccountListener();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        //reload page here
        setupOverView();
        setupExpList();
    }

    private void setupOverView() {
        //database
        accountDataBase = new AccountDAO(getApplicationContext());
        accountTypeDataBase = new AccountTypeDAO(getApplicationContext());
        ArrayList<Account> accountList = accountDataBase.getAllAccount();

        //calculate
        Double balance = Double.valueOf(0);
        Double debt = Double.valueOf(0);
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getType().equals("Credit Card")) {
                if (accountList.get(i).getBalance() > 0) {
                    debt += accountList.get(i).getBalance();
                } else {
                    balance += accountList.get(i).getBalance();
                }
            } else {
                if (accountList.get(i).getBalance() > 0) {
                    balance += accountList.get(i).getBalance();
                } else {
                    debt += accountList.get(i).getBalance();
                }
            }
        }

        //setup
        balanceNumber = (TextView) findViewById(R.id.balanceNumber);
        debtNumber = (TextView) findViewById(R.id.debtNumber);
        balanceNumber.setText(balance.toString());
        debtNumber.setText(debt.toString());

    }

    private void setupExpList() {
        //init
        expListView = (ExpandableListView) findViewById(R.id.accountExpList);
        accountTypeHeader = new ArrayList<>();
        accountListItems = new HashMap<>();
        accountDataBase = new AccountDAO(getApplicationContext());
        accountTypeDataBase = new AccountTypeDAO(getApplicationContext());
        final ArrayList<Account> accountList = accountDataBase.getAllAccount();
        ArrayList<String> accountTypeList = accountTypeDataBase.getAllType();
        //setup header
        for (int i = 0; i < accountTypeList.size(); i++) {
            accountTypeHeader.add(accountTypeList.get(i).toString());
        }
        //setup content of header
        for (int i = 0; i < accountTypeList.size(); i++) {
            List<List<String>> newListofAccount = new ArrayList<>();
            List<String> AccountDetail;
            Double ban = 0.00;
            for (int j = 0; j < accountList.size(); j++) {
                if (accountList.get(j).getType().equals(accountTypeList.get(i))) {
                    //add name
                    AccountDetail = new ArrayList<>();
                    AccountDetail.add(accountList.get(j).getName());
                    //set and add balance
                    ban = accountList.get(j).getBalance();
                    if (accountList.get(j).getType().equals("Credit Card")) {
                        if (ban < 0) {
                            ban *= -1;
                            AccountDetail.add("(" + ban.toString() + ")");
                        } else {
                            AccountDetail.add(ban.toString());
                        }
                    } else {
                        AccountDetail.add(ban.toString());
                    }
                    //add to list
                    newListofAccount.add(AccountDetail);
                }
            }
            //put to specific header
            accountListItems.put(accountTypeHeader.get(i), newListofAccount);
        }
        //setup Adapter & add to view
        listAdapter = new ExpandableListAdapter(this, accountTypeHeader, accountListItems);
        expListView.setAdapter(listAdapter);

        //set expanded as default
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            expListView.expandGroup(i);
        }

        //setup onClick event
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                int iidd = accountDataBase.getAccount(accountListItems.get(accountTypeHeader.get(groupPosition)).get(childPosition).get(0)).getID();
                Intent intent = new Intent(v.getContext(), AccountInfo.class);
                intent.putExtra("ID", iidd);
                startActivity(intent);

                return true;
            }
        });
    }

    private void addAccountListener() {
        addAccountButton = (ImageButton) findViewById(R.id.addAccButton);

        addAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Add_Account.class);
                startActivity(intent);
            }
        });
    }
}


class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<List<String>>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<List<String>>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public List<String> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText_Acc = getChild(groupPosition, childPosition).get(0);
        final String childText_ban = getChild(groupPosition, childPosition).get(1);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.account_list_item, null);
        }

        TextView accItem = (TextView) convertView.findViewById(R.id.accountNameItem);
        TextView banItem = (TextView) convertView.findViewById(R.id.accountBalanceItem);

        accItem.setText(childText_Acc);
        banItem.setText(childText_ban);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.account_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.accountTypeHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}