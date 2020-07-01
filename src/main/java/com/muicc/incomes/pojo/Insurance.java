package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "insurance")
public class Insurance implements Serializable {
    private int id;
    private double insurance;
    private int irid;

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
    @Column(name = "insurance")
    public double getInsurance() {
        return insurance;
    }

    public void setInsurance(double insurance) {
        this.insurance = insurance;
    }

    @Basic
    @Column(name = "irid")
    public int getIrid() {
        return irid;
    }

    public void setIrid(int irid) {
        this.irid = irid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Insurance insurance1 = (Insurance) o;
        return id == insurance1.id &&
                Double.compare(insurance1.insurance, insurance) == 0 &&
                irid == insurance1.irid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, insurance, irid);
    }
}
