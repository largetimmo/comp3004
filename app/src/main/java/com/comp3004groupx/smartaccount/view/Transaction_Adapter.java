package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;

import java.lang.reflect.Array;
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
        String date = mData.get(position).getDate().toString();
        String amount = Double.toString(mData.get(position).getAmount());
        TextView Date = (TextView) rowView.findViewById(R.id.TransDate);
        TextView Amount = (TextView) rowView.findViewById(R.id.TransAmount);
        Date.setText(date);
        Amount.setText(amount);
        return rowView;

    }
}
