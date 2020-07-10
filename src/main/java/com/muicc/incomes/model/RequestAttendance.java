package com.muicc.incomes.model;

import javax.persistence.*;
import java.util.Objects;

public class RequestAttendance {
    private int id;
    private String name;
    private double time;
    private int times;
    private double rate;
    private double fixedAward;
    private int status;
    private boolean values;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }


    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }


    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }


    public double getFixedAward() {
        return fixedAward;
    }

    public void setFixedAward(double fixedAward) {
        this.fixedAward = fixedAward;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isValues() {
        return values;
    }

    public void setValues(boolean values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestAttendance that = (RequestAttendance) o;
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
