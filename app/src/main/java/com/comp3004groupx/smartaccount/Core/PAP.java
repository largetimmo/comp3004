package com.comp3004groupx.smartaccount.Core;

/**
 * Created by chenjunhao on 2017/11/27.
 */

public class PAP {
    private int ID;
    private Date DATE;
    private double AMOUNT;
    private String ACCOUNT;
    private String NOTE;
    private String TYPE;
    private int PERIOD;
    private String CHECKED;
    public PAP(){

    }
    public PAP(Date date, double amount, String account, String note, String type) {
        this.DATE = date;
        this.AMOUNT = amount;
        this.ACCOUNT = account;
        this.NOTE = note;
        this.TYPE = type;
        this.CHECKED = "0";
        this.PERIOD = 1;
    }

    public PAP(int id, Date date, double amount, String account, String note, String type) {
        this.ID = id;
        this.DATE = date;
        this.AMOUNT = amount;
        this.ACCOUNT = account;
        this.TYPE = type;
        this.NOTE = note;
        this.CHECKED = "0";
        this.PERIOD = 1;
    }
    public int getId() {
        return ID;
    }

    public Date getDate() {
        return DATE;
    }

    public double getAmount() {
        return AMOUNT;
    }

    public void setId(int id) {
        this.ID = id;
    }

    public void setDate(Date date) {
        this.DATE = date;
    }

    public void setType(String type) {
        this.TYPE = type;
    }

    public void setAmount(double amount) {
        this.AMOUNT = amount;
    }

    public void setAccount(String account) {
        this.ACCOUNT = account;
    }

    public void setNote(String note) {
        this.NOTE = note;
    }

    public String getAccount() {
        return ACCOUNT;
    }

    public String getNote() {
        return NOTE;
    }

    public String getType() {
        return TYPE;
    }

    public int getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(int PERIOD) {
        this.PERIOD = PERIOD;
    }
    public String getCHECKED(){
        return CHECKED;
    }
    public void setCHECKED(String checked){
        this.CHECKED = checked;
    }
}
