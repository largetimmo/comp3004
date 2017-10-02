package com.comp3004groupx.smartaccount.Core;

import java.io.IOException;

/**
 * Created by chenjunhao on 2017/10/2.
 */

public class Date {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public Date(String date_str)  {
        String[] date_str_arr = date_str.split("-");
        this.year = Integer.parseInt(date_str_arr[0]);
        this.month = Integer.parseInt(date_str_arr[1]);
        this.day = Integer.parseInt(date_str_arr[2]);
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.year);
        sb.append("-");
        sb.append(this.month);
        sb.append("-");
        sb.append(this.day);
        return sb.toString();
    }
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
