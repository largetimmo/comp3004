package com.comp3004groupx.smartaccount.Core;

import java.sql.*;

/**
 * Created by chenjunhao on 2017/9/15.
 */

public class Transaction {
    private int id;
    private Date date;
    private String type;
    private double amount;
    private String account;
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

    public String getAccount() {
        return account;
    }

    public String getNote() {
        return note;
    }

    public String getType() {
        return type;
    }

    public Transaction(Date date, double amount, String account, String note, String type) {
        this.date = date;
        this.amount = amount;
        this.account = account;
        this.note = note;
        this.type = type;
    }

    public Transaction(int id, Date date, double amount, String account, String note, String type) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.account = account;
        this.type = type;
        this.note = note;
    }
}
