package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Table(name = "updatedate")
public class Updatedate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "updatedate")
    private Date updatedate;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Updatedate that = (Updatedate) o;
        return id == that.id &&
                Objects.equals(updatedate, that.updatedate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, updatedate);
    }
}
