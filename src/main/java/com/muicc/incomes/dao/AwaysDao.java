package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Aways;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AwaysDao extends JpaRepository<Aways,Integer> {

    @Query(value = "select * from aways order by cdid desc,eid asc",nativeQuery = true)
    List<Aways> getAllAways();

    @Query(value = "select * from aways where cdid =?1",nativeQuery = true)
    List<Aways> getAwaysByCdid(int cdid);

    @Query(value = "select * from aways where eid = ?1 and cdid =?2",nativeQuery = true)
    Aways getByEidAndCdid(int eid,int cdid);

    @Query(value = "select sum(cutmoney) from aways where eid = ?1 and cdid =?2",nativeQuery = true)
    double getAwaysByEidAndCdid(int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "insert into aways(shown,cutmoney,eid,cdid,status) values (?1,?2,?3,?4,?5) ",nativeQuery = true)
    int addAways(int shown, double cutmoney, int eid, int cdid, int status);

    @Transactional
    @Modifying
    @Query(value = "delete from aways where eid = ?1 and cdid =?2 ",nativeQuery = true)
    int deleteAways(int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "update aways set status = ?1 where cdid = ?2 ",nativeQuery = true)
    int updateAwaysStatus(int status,int cdid);
}
