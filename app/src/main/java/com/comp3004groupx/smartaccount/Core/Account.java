package com.comp3004groupx.smartaccount.Core;

/**
 * Created by chenjunhao on 2017/9/14.
 */

public class Account {
    private int ID;
    private String name;
    private String type;
    private double balance;
    private double real_balance;
    public Account(String name, String type, double balance, double real_balance){
        this.name = name;
        this.type = type;
        this.balance = balance;
        this.real_balance = real_balance;
    }
    public Account(int ID,String name, String type, double balance, double real_balance){
        this.ID= ID;
        this.name=name;
        this.type=type;
        this.balance=balance;
        this.real_balance = real_balance;
    }

    public int getID(){
        return ID;
    }
    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setRealAmount(double realAmount) {
        this.real_balance = realAmount;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getReal_balance() {
        return real_balance;
    }

    public void setReal_balance(double real_balance) {
        this.real_balance = real_balance;
    }

    public double getRealAmount(){
        return real_balance;

    }
    public void spendMoney(double amount){
        this.real_balance -= amount;
        this.balance -= amount;
    }

}
