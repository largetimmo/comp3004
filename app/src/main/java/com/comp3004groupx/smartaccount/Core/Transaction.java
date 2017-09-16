package com.comp3004groupx.smartaccount.Core;

import java.sql.*;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class Transaction {
    private int id;
    private Date date;
    private double amount;
    private Account account;
    private String note;

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

    public String getNote() {
        return note;
    }

    public Transaction(Date date, double amount, Account account, String note) {
        this.date = date;
        this.amount = amount;
        this.account = account;
        this.note = note;
    }

    public Transaction(int id, Date date, double amount, Account account, String note) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.account = account;
        this.note = note;
    }
}
