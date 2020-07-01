package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Userobject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserobjectDao extends JpaRepository<Userobject,Integer> {

    @Query(value = "select * from userobject where cdid = ?1",nativeQuery = true)
    List<Userobject> getUserobjectByCdid(int cdid);

    @Query(value = "select * from userobject where udid = ?1",nativeQuery = true)
    List<Userobject> getUserobjectByUdid(int udid);

    @Transactional
    @Modifying
    @Query(value = "insert into userobject(udid,twid,txid,isid,cdid,wdids) values (?1,?2,?3,?4,?5,?6) ",nativeQuery = true)
    int addUserobject(int udid,int twid,int txid,int isid,int cdid,int wdids);

    @Query(value = "select max(id) from userobject where udid=?1 and twid=?2 and txid=?3 and isid=?4 and cdid=?5 and wdids=?6",nativeQuery = true)
    Integer getUserobjectId(int udid,int twid,int txid,int isid,int cdid,int wdids);

    @Transactional
    @Modifying
    @Query(value = "delete from userobject where cdid = ?1",nativeQuery = true)
    int deleteUserobject(int cdid);
}
