package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userobject")
public class Userobject {
    private int id;
    private int eid;
    private int cdid;
    private double regularpay;
    private double work;
    private double holiday;
    private double allowanceids;
    private double awards;
    private double absence;
    private double initialsum;
    private double insurance;
    private double secondsum;
    private double cut;
    private double tax;
    private double sumpayable;
    private String qianzhang;
    private String note;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    @Column(name = "cdid")
    public int getCdid() {
        return cdid;
    }

    public void setCdid(int cdid) {
        this.cdid = cdid;
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
    @Column(name = "work")
    public double getWork() {
        return work;
    }

    public void setWork(double work) {
        this.work = work;
    }

    @Basic
    @Column(name = "holiday")
    public double getHoliday() {
        return holiday;
    }

    public void setHoliday(double holiday) {
        this.holiday = holiday;
    }

    @Basic
    @Column(name = "allowanceids")
    public double getAllowanceids() {
        return allowanceids;
    }

    public void setAllowanceids(double allowanceids) {
        this.allowanceids = allowanceids;
    }

    @Basic
    @Column(name = "awards")
    public double getAwards() {
        return awards;
    }

    public void setAwards(double awards) {
        this.awards = awards;
    }

    @Basic
    @Column(name = "absence")
    public double getAbsence() {
        return absence;
    }

    public void setAbsence(double absence) {
        this.absence = absence;
    }

    @Basic
    @Column(name = "initialsum")
    public double getInitialsum() {
        return initialsum;
    }

    public void setInitialsum(double initialsum) {
        this.initialsum = initialsum;
    }

    @Basic
    @Column(name = "insurance")
    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    @Basic
    @Column(name = "secondsum")
    public double getSecondsum() {
        return secondsum;
    }

    public void setSecondsum(double secondsum) {
        this.secondsum = secondsum;
    }

    @Basic
    @Column(name = "cut")
    public double getCut() {
        return cut;
    }

    public void setCut(double cut) {
        this.cut = cut;
    }

    @Basic
    @Column(name = "tax")
    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Basic
    @Column(name = "sumpayable")
    public double getSumpayable() {
        return sumpayable;
    }

    public void setSumpayable(double sumpayable) {
        this.sumpayable = sumpayable;
    }

    @Basic
    @Column(name = "qianzhang")
    public String getQianzhang() {
        return qianzhang;
    }

    public void setQianzhang(String qianzhang) {
        this.qianzhang = qianzhang;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
