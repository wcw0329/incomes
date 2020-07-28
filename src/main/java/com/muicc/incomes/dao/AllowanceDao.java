package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AllowanceDao extends JpaRepository<Allowance,Integer> {
    @Query(value = "select * from allowance where eid = ?1 and alcid =?2 and cdid =?3",nativeQuery = true)
    Allowance getAllowanceByEidAndAlcidAndCdid(int eid, int alcid,int cdid);

    @Query(value = "select * from allowance order by cdid desc,eid asc",nativeQuery = true)
    List<Allowance> getAllAllowance();

    @Query(value = "select * from allowance where cdid =?1",nativeQuery = true)
    List<Allowance> getAllowanceByCdid(int cdid);

    @Query(value = "select * from allowance where eid = ?1 and cdid =?2",nativeQuery = true)
    List<Allowance> getByEidAndCdid(int eid,int cdid);

    @Query(value = "select sum(allowance) from allowance where eid = ?1 and cdid =?2",nativeQuery = true)
    double getAllowanceByEidAndCdid(int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "insert into allowance(alcid, allowance, eid,cdid ,status) values (?1,?2,?3,?4,1) ",nativeQuery = true)
    int addAllowance(int alcid,double allowance,int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "update allowance set alcid =?2, allowance =?3,eid =?4,cdid =?5 where id = ?1 ",nativeQuery = true)
    int updateAllowance(int id,int alcid,double allowance,int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "update allowance set status = 0 where cdid = ?1 and eid=?2 ",nativeQuery = true)
    int updateAllowanceStatusTo0(int cdid,int eid);

    @Transactional
    @Modifying
    @Query(value = "update allowance set status = 1 where cdid = ?1 ",nativeQuery = true)
    int updateAllowanceStatusTo1(int cdid);

    @Transactional
    @Modifying
    @Query(value = "delete from allowance where id = ?1 ",nativeQuery = true)
    int deleteAllowance(int id);
}
