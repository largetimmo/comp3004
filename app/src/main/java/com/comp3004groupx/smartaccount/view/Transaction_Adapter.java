package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;

import java.util.ArrayList;

/**
 * Created by Alex Meng on 2017-10-26.
 */

public class Transaction_Adapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Transaction> mData;

    public Transaction_Adapter(Context context, ArrayList<Transaction> items){
        mContext = context;
        mData = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public String getItem(int position) { return mData.get(position).getAccount(); }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layout = LayoutInflater.from(mContext);
        View rowView = layout.inflate(R.layout.transaction_adapter_view, parent, false);
        AccountDAO accounts = new AccountDAO(mContext.getApplicationContext());

        //Initializing viewHolder
        viewHolder holder = new viewHolder();
        holder.TransType = (TextView)rowView.findViewById(R.id.TransType);
        holder.TransAmount = (TextView) rowView.findViewById(R.id.TransAmount);
        holder.TransAccount =(TextView) rowView.findViewById(R.id.TransAccount);
        holder.TransDate = (TextView)rowView.findViewById(R.id.TransDate);
        rowView.setTag(holder);

        //get data from list
        String type = mData.get(position).getType();
        double amountNum = mData.get(position).getAmount();
        String accountName = mData.get(position).getAccount();
        String date = mData.get(position).getDate().toString();
        Account account = accounts.getAccount(accountName);
        String accountType = account.getType();
        //set data in viewHolder

        holder.TransDate.setText(date);
        holder.TransAccount.setText(accountName+"  "+accountType);
        holder.TransType.setText(type);

        if (amountNum >0){
            String amount = Double.toString(amountNum);
            holder.TransAmount.setText(amount);
            holder.TransAmount.setTextColor(Color.RED);
        }else{
            String amount = Double.toString(-1*amountNum);
            holder.TransAmount.setText(amount);
            holder.TransAmount.setTextColor(Color.GREEN);
        }
        return rowView;
    }

    public class viewHolder{
        TextView TransAccount;
        TextView TransDate;
        TextView TransType;
        TextView TransAmount;
    }
}
