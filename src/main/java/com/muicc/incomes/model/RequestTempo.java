package com.muicc.incomes.model;

public class RequestTempo {
    private int id;
    private int cdid;
    private String time;
    private String name;
    private double secondsum;
    private double tax;
    private double sumpayable;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCdid() {
        return cdid;
    }

    public void setCdid(int cdid) {
        this.cdid = cdid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSecondsum() {
        return secondsum;
    }

    public void setSecondsum(double secondsum) {
        this.secondsum = secondsum;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getSumpayable() {
        return sumpayable;
    }

    public void setSumpayable(double sumpayable) {
        this.sumpayable = sumpayable;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
