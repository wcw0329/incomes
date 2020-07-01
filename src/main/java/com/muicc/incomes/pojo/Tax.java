package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tax")
public class Tax implements Serializable {
    private int id;
    private double tax;
    private int trupdid;

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
    @Column(name = "trupdid")
    public int getTrupdid() {
        return trupdid;
    }

    public void setTrupdid(int trupdid) {
        this.trupdid = trupdid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tax tax1 = (Tax) o;
        return id == tax1.id &&
                Double.compare(tax1.tax, tax) == 0 &&
                trupdid == tax1.trupdid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tax, trupdid);
    }
}
