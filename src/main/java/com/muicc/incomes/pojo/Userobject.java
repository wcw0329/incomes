package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "userobject")
public class Userobject implements Serializable {
    private int id;
    private int udid;
    private int twid;
    private int txid;
    private int isid;
    private int cdid;
    private Integer wdids;

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
    @Column(name = "udid")
    public int getUdid() {
        return udid;
    }

    public void setUdid(int udid) {
        this.udid = udid;
    }

    @Basic
    @Column(name = "twid")
    public int getTwid() {
        return twid;
    }

    public void setTwid(int twid) {
        this.twid = twid;
    }

    @Basic
    @Column(name = "txid")
    public int getTxid() {
        return txid;
    }

    public void setTxid(int txid) {
        this.txid = txid;
    }

    @Basic
    @Column(name = "isid")
    public int getIsid() {
        return isid;
    }

    public void setIsid(int isid) {
        this.isid = isid;
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
    @Column(name = "wdids")
    public Integer getWdids() {
        return wdids;
    }

    public void setWdids(Integer wdids) {
        this.wdids = wdids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Userobject that = (Userobject) o;
        return id == that.id &&
                udid == that.udid &&
                twid == that.twid &&
                txid == that.txid &&
                isid == that.isid &&
                cdid == that.cdid &&
                Objects.equals(wdids, that.wdids);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, udid, twid, txid, isid, cdid, wdids);
    }
}
