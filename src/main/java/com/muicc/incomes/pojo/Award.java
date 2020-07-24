package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "award")
public class Award implements Serializable {
    private int id;
    private int acid;
    private double award;
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
    @Column(name = "acid")
    public int getAcid() {
        return acid;
    }

    public void setAcid(int acid) {
        this.acid = acid;
    }

    @Basic
    @Column(name = "award")
    public double getAward() {
        return award;
    }

    public void setAward(double award) {
        this.award = award;
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
