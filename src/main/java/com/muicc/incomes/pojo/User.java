package com.muicc.incomes.pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements Serializable {
    private int id;
    private String username;
    private double usercount;
    private int status;
    private int userobjectid;

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
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "usercount")
    public double getUsercount() {
        return usercount;
    }

    public void setUsercount(double usercount) {
        this.usercount = usercount;
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
    @Column(name = "userobjectid")
    public int getUserobjectid() {
        return userobjectid;
    }

    public void setUserobjectid(int userobjectid) {
        this.userobjectid = userobjectid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Double.compare(user.usercount, usercount) == 0 &&
                status == user.status &&
                userobjectid == user.userobjectid &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, usercount, status, userobjectid);
    }
}
