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
}
