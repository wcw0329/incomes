package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "insurancerate")
public class Insurancerate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "insurancerate")
    private Double insurancerate;
    @Column(name = "updatedate")
    private Date updatedate;
    @Column(name = "fixedfee")
    private Double fixedfee;
    @Column(name = "status")
    private int status;
    @Column(name = "eid")
    private int eid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getInsurancerate() {
        return insurancerate;
    }

    public void setInsurancerate(Double insurancerate) {
        this.insurancerate = insurancerate;
    }

    public String getUpdatedate() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(updatedate);
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Double getFixedfee() {
        return fixedfee;
    }

    public void setFixedfee(Double fixedfee) {
        this.fixedfee = fixedfee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }
}
