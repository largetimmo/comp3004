package com.comp3004groupx.smartaccount.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.comp3004groupx.smartaccount.R;

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
    public Object getItem(int position) {
        return mData.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View rowView = mInflater.inflate(R.layout.transaction_adapter_view, parent, false);
        return rowView;
    }
}
