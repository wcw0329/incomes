package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sum")
public class Sum implements Serializable {
    private int id;
    private double sum1;
    private Integer cdid;

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
    @Column(name = "sum1")
    public double getSum1() {
        return sum1;
    }

    public void setSum1(double sum1) {
        this.sum1 = sum1;
    }

    @Basic
    @Column(name = "cdid")
    public Integer getCdid() {
        return cdid;
    }

    public void setCdid(Integer cdid) {
        this.cdid = cdid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sum sum = (Sum) o;
        return id == sum.id &&
                Double.compare(sum.sum1, sum1) == 0 &&
                Objects.equals(cdid, sum.cdid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sum1, cdid);
    }
}
