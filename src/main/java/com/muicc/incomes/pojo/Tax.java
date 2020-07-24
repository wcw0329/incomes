package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tax")
public class Tax implements Serializable {
    private int id;
    private double tax;
    private int eid;
    private int cdid;

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
    @Column(name = "tax")
    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
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
}
