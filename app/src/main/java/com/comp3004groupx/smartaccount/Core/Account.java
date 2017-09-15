package com.comp3004groupx.smartaccount.Core;

/**
 * Created by chenjunhao on 2017/9/14.
 */

public class Account {
    private int ID;
    private String name;
    private Type type;
    private double balance;
    public Account(String name, Type type, double balance){
        this.name = name;
        this.type = type;
        this.balance = balance;
    }
    public Account(int ID,String name, Type type, double balance){
        this.ID= ID;
        this.name=name;
        this.type=type;
        this.balance=balance;
    }

    public int getID(){
        return ID;
    }
    public String getName(){
        return name;
    }
    public Type getType(){
        return type;
    }
    public double getBalance(){
        return balance;
    }

}
