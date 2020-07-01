package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "total")
public class Total implements Serializable {
    private int id;
    private double totalsum;
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
    @Column(name = "totalsum")
    public double getTotalsum() {
        return totalsum;
    }

    public void setTotalsum(double totalsum) {
        this.totalsum = totalsum;
    }

    @Basic
    @Column(name = "cdid")
    public int getCdid() {
        return cdid;
    }

    public void setCdid(int cdid) {
        this.cdid = cdid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Total total = (Total) o;
        return id == total.id &&
                Double.compare(total.totalsum, totalsum) == 0 &&
                cdid == total.cdid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalsum, cdid);
    }
}
