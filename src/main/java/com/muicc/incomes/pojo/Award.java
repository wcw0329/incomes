package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "award")
public class Award implements Serializable {
    private int id;
    private int acid;
    private double award;
    private int awardsid;

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
        Award award1 = (Award) o;
        return id == award1.id &&
                acid == award1.acid &&
                Double.compare(award1.award, award) == 0 &&
                awardsid == award1.awardsid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, acid, award, awardsid);
    }
}
