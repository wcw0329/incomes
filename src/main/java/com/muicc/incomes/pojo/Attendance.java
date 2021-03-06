package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "attendance")
public class Attendance {
    private int id;
    private String name;
    private double time;
    private int times;
    private double rate;
    private double fixedAward;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "time")
    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Basic
    @Column(name = "times")
    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Basic
    @Column(name = "rate")
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Basic
    @Column(name = "fixed_award")
    public double getFixedAward() {
        return fixedAward;
    }

    public void setFixedAward(double fixedAward) {
        this.fixedAward = fixedAward;
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
        Attendance that = (Attendance) o;
        return id == that.id &&
                Double.compare(that.time, time) == 0 &&
                times == that.times &&
                Double.compare(that.rate, rate) == 0 &&
                Double.compare(that.fixedAward, fixedAward) == 0 &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, time, times, rate, fixedAward);
    }
}
