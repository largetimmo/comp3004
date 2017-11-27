package com.comp3004groupx.smartaccount.Core;


import java.util.Calendar;

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
        if (month<=9){
            sb.append("0");
        }
        sb.append(this.month);
        sb.append("-");
        if (this.day<=9){
            sb.append("0");
        }
        sb.append(this.day);
        return sb.toString();
    }
    public int getYear() { return year; }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    //Simple compare function
    public int compareTo(Date another){
        if(this.getYear()>another.getYear()) {
            return 1;
        }
        else if(this.getYear()==another.getYear()){
            if(this.getMonth()>another.getMonth()){
                return 1;
            }
            else if(this.getMonth()==another.getMonth()){
                if(this.getDay()>another.getDay()){
                    return 1;
                }
                else if(this.getDay()==another.getDay())
                    return 0;
                else
                    return -1;
            }
            else
                return -1;
        }
        else
            return -1;
    }

    public void plusDate(int n){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        calendar.add(Calendar.DATE,n);
        day = calendar.get(Calendar.DATE);
        month = (calendar.get(Calendar.MONTH)+1)%12;
        if(month==0)
            month = 12;
        year = calendar.get(Calendar.YEAR);
    }

    public void plusMonth(int n){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        calendar.add(Calendar.MONTH,+n);
        month = (calendar.get(Calendar.MONTH)+1)%12;
        if(month==0)
            month =12;
        year = calendar.get(Calendar.YEAR);
    }

    public void plusYear(int n){
        year+=n;
    }
}
