package com.muicc.incomes.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "allowance")
public class RequestAllowance implements Serializable {
    private int id;
    private int alcid;
    private String ename;
    private String category;
    private double allowance;
    private int eid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @Basic
    @Column(name = "alcid")
    public int getAlcid() {
        return alcid;
    }

    public void setAlcid(int alcid) {
        this.alcid = alcid;
    }

    @Basic
    @Column(name = "allowance")
    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    @Basic
    @Column(name = "eid")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestAllowance allowance1 = (RequestAllowance) o;
        return id == allowance1.id &&
                alcid == allowance1.alcid &&
                Double.compare(allowance1.allowance, allowance) == 0 &&
                eid == allowance1.eid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alcid, allowance, eid);
    }
}
