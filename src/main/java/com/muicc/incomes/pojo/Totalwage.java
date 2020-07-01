package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "totalwage")
public class Totalwage implements Serializable {
    private int id;
    private double initialsum;
    private double secondsum;
    private double sumpayable;

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
    @Column(name = "initialsum")
    public double getInitialsum() {
        return initialsum;
    }

    public void setInitialsum(double initialsum) {
        this.initialsum = initialsum;
    }

    @Basic
    @Column(name = "secondsum")
    public double getSecondsum() {
        return secondsum;
    }

    public void setSecondsum(double secondsum) {
        this.secondsum = secondsum;
    }

    @Basic
    @Column(name = "sumpayable")
    public double getSumpayable() {
        return sumpayable;
    }

    public void setSumpayable(double sumpayable) {
        this.sumpayable = sumpayable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Totalwage totalwage = (Totalwage) o;
        return id == totalwage.id &&
                Double.compare(totalwage.initialsum, initialsum) == 0 &&
                Double.compare(totalwage.secondsum, secondsum) == 0 &&
                Double.compare(totalwage.sumpayable, sumpayable) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, initialsum, secondsum, sumpayable);
    }
}
