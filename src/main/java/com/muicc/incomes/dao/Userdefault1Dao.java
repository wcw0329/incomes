package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Userdefault1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface Userdefault1Dao extends JpaRepository<Userdefault1,Integer> {


    @Query(value = "select * from userdefault1 where status = 1",nativeQuery = true)
    List<Userdefault1> getAllUserdefault1();

    @Query(value = "select * from userdefault1 where eid = ?1 and status = 1",nativeQuery = true)
    Userdefault1 getUserdefault1ByEid(int eid);

    @Transactional
    @Modifying
    @Query(value = "insert into userdefault1 (eid,regularpay,insurancebase,updatedate," +
            "status,note,qianzhang) values (?1,?2,?3,NOW(),1,?4,?5) ",nativeQuery = true)
    int addUserdefault1(int eid,double regularpay,double insurancebase,
                        String note,String qianzhang);

    @Transactional
    @Modifying
    @Query(value = "insert into userdefault1 (eid,regularpay,insurancebase,updatedate," +
            "status,note,qianzhang) values (?1,?2,?3,?4,1,?5,null) ",nativeQuery = true)
    int addUserdefault2(int eid, double regularpay, double insurancebase, Date updatedate,
                        String note);

    @Transactional
    @Modifying
    @Query(value = "update userdefault1 set regularpay =?2 ,insurancebase = ?3 ,updatedate = ?4 ,note = ?5 where eid = ?1 and status = 1 ",nativeQuery = true)
    int updateUserdefault1(int eid,double regularpay, double insurancebase, Date updatedate,
                           String note);

    @Transactional
    @Modifying
    @Query(value = "update userdefault1 set status = 0 where id = ?1",nativeQuery = true)
    int deleteUserdefault1(int id);
}
