package com.muicc.incomes.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestInsurancerate {

    private int id;

    private String ename;

    private Double insurancerate;

    private Date updatedate;

    private Double fixedfee;

    private int status;

    private int eid;

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

    public Double getInsurancerate() {
        return insurancerate;
    }

    public void setInsurancerate(Double insurancerate) {
        this.insurancerate = insurancerate;
    }

    public String getUpdatedate() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(updatedate);
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Double getFixedfee() {
        return fixedfee;
    }

    public void setFixedfee(Double fixedfee) {
        this.fixedfee = fixedfee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }
}
