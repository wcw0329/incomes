package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "taxrate")
public class Taxrate implements Serializable {
    private int id;
    private int updid;
    private double min;
    private double max;
    private Double taxrate;
    private int threshold;
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
    @Column(name = "updid")
    public int getUpdid() {
        return updid;
    }

    public void setUpdid(int updid) {
        this.updid = updid;
    }

    @Basic
    @Column(name = "min")
    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    @Basic
    @Column(name = "max")
    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @Basic
    @Column(name = "taxrate")
    public Double getTaxrate() {
        return taxrate;
    }

    public void setTaxrate(Double taxrate) {
        this.taxrate = taxrate;
    }

    @Basic
    @Column(name = "threshold")
    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxrate taxrate1 = (Taxrate) o;
        return id == taxrate1.id &&
                updid == taxrate1.updid &&
                Double.compare(taxrate1.min, min) == 0 &&
                Double.compare(taxrate1.max, max) == 0 &&
                threshold == taxrate1.threshold &&
                status == taxrate1.status &&
                Objects.equals(taxrate, taxrate1.taxrate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, updid, min, max, taxrate, threshold, status);
    }
}
