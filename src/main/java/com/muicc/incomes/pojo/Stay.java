package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "accountant")
public class Stay implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "updatedate")
    private Date updatedate;
    @Column(name = "bei")
    private Double bei;
    @Column(name = "time")
    private Double time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdatedate() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(updatedate);
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Double getBei() {
        return bei;
    }

    public void setBei(Double bei) {
        this.bei = bei;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
