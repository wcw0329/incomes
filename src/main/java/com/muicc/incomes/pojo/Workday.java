package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "workday")
public class Workday implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "eid")
    private int eid;
    @Column(name = "cdid")
    private int cdid;
    @Column(name = "absence")
    private int absence;
    @Column(name = "overtimen")
    private double overtimen;
    @Column(name = "overtimed")
    private double overtimed;
    @Column(name = "status")
    private int status;
    @Column(name = "absenteeism")
    private double absenteeism;
    @Column(name = "late")
    private double late;
    @Column(name = "leaveral")
    private double leaveral;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getCdid() {
        return cdid;
    }

    public void setCdid(int cdid) {
        this.cdid = cdid;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public double getOvertimen() {
        return overtimen;
    }

    public void setOvertimen(double overtimen) {
        this.overtimen = overtimen;
    }

    public double getOvertimed() {
        return overtimed;
    }

    public void setOvertimed(double overtimed) {
        this.overtimed = overtimed;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getAbsenteeism() {
        return absenteeism;
    }

    public void setAbsenteeism(double absenteeism) {
        this.absenteeism = absenteeism;
    }

    public double getLate() {
        return late;
    }

    public void setLate(double late) {
        this.late = late;
    }

    public double getLeaveral() {
        return leaveral;
    }

    public void setLeaveral(double leaveral) {
        this.leaveral = leaveral;
    }
}
