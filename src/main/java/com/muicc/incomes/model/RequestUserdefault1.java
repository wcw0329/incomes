package com.muicc.incomes.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "userdefault1")
public class RequestUserdefault1  {
    private Integer id;
    private int eid;
    private String ename;
    private double regularpay;
    private double insurancebase;
    private String updatedate;
    private int status;
    private String note;
    private String qianzhang;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "eid")
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    @Basic
    @Column(name = "regularpay")
    public double getRegularpay() {
        return regularpay;
    }

    public void setRegularpay(double regularpay) {
        this.regularpay = regularpay;
    }

    @Basic
    @Column(name = "insurancebase")
    public double getInsurancebase() {
        return insurancebase;
    }

    public void setInsurancebase(double insurancebase) {
        this.insurancebase = insurancebase;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Basic
    @Column(name = "qianzhang")
    public String getQianzhang() {
        return qianzhang;
    }

    public void setQianzhang(String qianzhang) {
        this.qianzhang = qianzhang;
    }


    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestUserdefault1 that = (RequestUserdefault1) o;
        return id == that.id &&
                eid == that.eid &&
                Double.compare(that.regularpay, regularpay) == 0 &&
                Double.compare(that.insurancebase, insurancebase) == 0 &&
                status == that.status &&
                Objects.equals(updatedate, that.updatedate) &&
                Objects.equals(note, that.note) &&
                Objects.equals(qianzhang, that.qianzhang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eid, regularpay, insurancebase, updatedate, status, note, qianzhang);
    }
}
