package com.muicc.incomes.model;

import java.text.SimpleDateFormat;

public class RequestWorkday {

    private String name;

    private String time;

    private Integer id;

    private int eid;

    private int cdid;

    private int paidLeave;

    private int absence;

    private double overtimen;

    private double overtimed;

    private int status;

    private double absenteeism;

    private double late;

    private double leaveral;

    private int lateTimes;

    private int leaveralTimes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public int getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(int paidLeave) {
        this.paidLeave = paidLeave;
    }

    public int getLateTimes() {
        return lateTimes;
    }

    public void setLateTimes(int lateTimes) {
        this.lateTimes = lateTimes;
    }

    public int getLeaveralTimes() {
        return leaveralTimes;
    }

    public void setLeaveralTimes(int leaveralTimes) {
        this.leaveralTimes = leaveralTimes;
    }
}
