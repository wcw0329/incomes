package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "workday")
public class Workday {
    private int id;
    private int eid;
    private int cdid;
    private double paidLeave;
    private double absence;
    private double overtimen;
    private double overtimed;
    private int status;
    private double absenteeism;
    private double late;
    private double leaveral;
    private int lateTimes;
    private int leaveralTimes;

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
    @Column(name = "eid")
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
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
    @Column(name = "paid_leave")
    public double getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(double paidLeave) {
        this.paidLeave = paidLeave;
    }


    @Basic
    @Column(name = "absence")
    public double getAbsence() {
        return absence;
    }

    public void setAbsence(double absence) {
        this.absence = absence;
    }



    @Basic
    @Column(name = "overtimen")
    public double getOvertimen() {
        return overtimen;
    }

    public void setOvertimen(double overtimen) {
        this.overtimen = overtimen;
    }

    @Basic
    @Column(name = "overtimed")
    public double getOvertimed() {
        return overtimed;
    }

    public void setOvertimed(double overtimed) {
        this.overtimed = overtimed;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "absenteeism")
    public double getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(double absenteeism) {
        this.absenteeism = absenteeism;
    }

    @Basic
    @Column(name = "late")
    public double getLate() {
        return late;
    }

    public void setLate(double late) {
        this.late = late;
    }

    @Basic
    @Column(name = "leaveral")
    public double getLeaveral() {
        return leaveral;
    }

    public void setLeaveral(double leaveral) {
        this.leaveral = leaveral;
    }

    @Basic
    @Column(name = "late_times")
    public int getLateTimes() {
        return lateTimes;
    }

    public void setLateTimes(int lateTimes) {
        this.lateTimes = lateTimes;
    }

    @Basic
    @Column(name = "leaveral_times")
    public int getLeaveralTimes() {
        return leaveralTimes;
    }

    public void setLeaveralTimes(int leaveralTimes) {
        this.leaveralTimes = leaveralTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workday workday = (Workday) o;
        return id == workday.id &&
                eid == workday.eid &&
                cdid == workday.cdid &&
                absence == workday.absence &&
                Double.compare(workday.overtimen, overtimen) == 0 &&
                Double.compare(workday.overtimed, overtimed) == 0 &&
                status == workday.status &&
                Double.compare(workday.absenteeism, absenteeism) == 0 &&
                Double.compare(workday.late, late) == 0 &&
                Double.compare(workday.leaveral, leaveral) == 0 &&
                lateTimes == workday.lateTimes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eid, cdid, absence, overtimen, overtimed, status, absenteeism, late, leaveral, lateTimes);
    }
}
