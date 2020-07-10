package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Aways {
    private int id;
    private int shown;
    private double cutmoney;
    private int eid;
    private int cdid;
    private int status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public double getCutmoney() {
        return cutmoney;
    }

    public void setCutmoney(double cutmoney) {
        this.cutmoney = cutmoney;
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
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aways aways = (Aways) o;
        return id == aways.id &&
                shown == aways.shown &&
                Double.compare(aways.cutmoney, cutmoney) == 0 &&
                eid == aways.eid &&
                cdid == aways.cdid &&
                status == aways.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shown, cutmoney, eid, cdid, status);
    }
}
