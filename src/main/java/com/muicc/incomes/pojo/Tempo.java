package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tempo")
public class Tempo implements Serializable {
    private int id;
    private int cdid;
    private String name;
    private double secondsum;
    private double tax;
    private double sumpayable;
    private String note;

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
    @Column(name = "cdid")
    public int getCdid() {
        return cdid;
    }

    public void setCdid(int cdid) {
        this.cdid = cdid;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "tax")
    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Basic
    @Column(name = "sumpayable")
    public double getSumpayable() {
        return sumpayable;
    }

    public void setSumpayable(double sumpayable) {
        this.sumpayable = sumpayable;
    }

    @Basic
    @Column(name = "note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tempo tempo = (Tempo) o;
        return id == tempo.id &&
                cdid == tempo.cdid &&
                Double.compare(tempo.secondsum, secondsum) == 0 &&
                Double.compare(tempo.tax, tax) == 0 &&
                Double.compare(tempo.sumpayable, sumpayable) == 0 &&
                Objects.equals(name, tempo.name) &&
                Objects.equals(note, tempo.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cdid, name, secondsum, tax, sumpayable, note);
    }
}
