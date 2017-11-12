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
        sb.append(this.month);
        sb.append("-");
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
    public void plusDateFive(){
        Calendar calendar = Calendar.getInstance();
        int daysInThisMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if(getDay()+5<=daysInThisMonth){
            day = getDay()+5;
        }
        else{
            day = (getDay()+5)%daysInThisMonth;
            if(getMonth()+1<=12){
                month = getMonth()+1;
            }
            else{
                year = getYear()+1;
                month = 1;
            }
        }
    }
}
