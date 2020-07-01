package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "aways")
public class Aways implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "shown")
    private int shown;
    @Column(name = "cutmoney")
    private Double cutmoney;
    @Column(name = "workdayid")
    private int workdayid;
    @Column(name = "stayid")
    private int stayid;
    @Column(name = "awardsid")
    private int awardsid;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "shown")
    public int getShown() {
        return shown;
    }

    public void setShown(int shown) {
        this.shown = shown;
    }

    @Basic
    @Column(name = "cutmoney")
    public Double getCutmoney() {
        return cutmoney;
    }

    public void setCutmoney(double cutmoney) {
        this.cutmoney = cutmoney;
    }

    public void setCutmoney(Double cutmoney) {
        this.cutmoney = cutmoney;
    }

    @Basic
    @Column(name = "workdayid")
    public int getWorkdayid() {
        return workdayid;
    }

    public void setWorkdayid(int workdayid) {
        this.workdayid = workdayid;
    }

    @Basic
    @Column(name = "stayid")
    public int getStayid() {
        return stayid;
    }

    public void setStayid(int stayid) {
        this.stayid = stayid;
    }

    @Basic
    @Column(name = "awardsid")
    public int getAwardsid() {
        return awardsid;
    }

    public void setAwardsid(int awardsid) {
        this.awardsid = awardsid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aways aways = (Aways) o;
        return id == aways.id &&
                shown == aways.shown &&
                workdayid == aways.workdayid &&
                stayid == aways.stayid &&
                awardsid == aways.awardsid &&
                Objects.equals(cutmoney, aways.cutmoney);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shown, cutmoney, workdayid, stayid, awardsid);
    }
}
