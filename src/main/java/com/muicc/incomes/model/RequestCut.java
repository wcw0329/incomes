package com.muicc.incomes.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


public class RequestCut implements Serializable {
    private int id;
    private int ccid;
    private String ename;
    private String category;
    private double cut;
    private int eid;
    private String time;
    private int cdid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }


    public int getCcid() {
        return ccid;
    }

    public void setCcid(int ccid) {
        this.ccid = ccid;
    }


    public double getCut() {
        return cut;
    }

    public void setCut(double cut) {
        this.cut = cut;
    }


    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCdid() {
        return cdid;
    }

    public void setCdid(int cdid) {
        this.cdid = cdid;
    }

}
