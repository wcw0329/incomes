package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cut")
public class Cut implements Serializable {
    private int id;
    private int ccid;
    private double cut;
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

    @Basic
    @Column(name = "ccid")
    public int getCcid() {
        return ccid;
    }

    public void setCcid(int ccid) {
        this.ccid = ccid;
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
    @Column(name = "eid")
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cut cut1 = (Cut) o;
        return id == cut1.id &&
                ccid == cut1.ccid &&
                Double.compare(cut1.cut, cut) == 0 &&
                eid == cut1.eid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ccid, cut, eid);
    }
}
