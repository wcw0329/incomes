package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Userdefault1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface Userdefault1Dao extends JpaRepository<Userdefault1,Integer> {


    @Query(value = "select * from userdefault1 where id = ?1 and status = 1",nativeQuery = true)
    Userdefault1 getUserdefault1ByUdid(int udid);

    @Query(value = "select * from userdefault1 where eid = ?1 and status = 1",nativeQuery = true)
    List<Userdefault1> getUserdefault1ByEid(int eid);

    @Query(value = "select max(id) from userdefault1 where eid = ?1 and status = 1",nativeQuery = true)
    Integer getLastId(int eid);

    @Transactional
    @Modifying
    @Query(value = "insert into userdefault1 (eid,allowanceids,cutids,regularpay,insurancebase,updatedate," +
            "status,note,qianzhang) values (?1,?2,?3,?4,?5,NOW(),1,?6,?7) ",nativeQuery = true)
    int addUserdefault1(int eid,double allowanceids,double cutids,double regularpay,double insurancebase,
                        String note,String qianzhang);
}
