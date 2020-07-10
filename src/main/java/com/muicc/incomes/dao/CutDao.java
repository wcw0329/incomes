package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Cut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CutDao extends JpaRepository<Cut,Integer> {

    @Query(value = "select * from cut where eid = ?1 and ccid =?2 and cdid =?3",nativeQuery = true)
    Cut getCutByEidAndCcidAndCdid(int eid, int ccid, int cdid);

    @Query(value = "select * from cut order by cdid desc,eid asc",nativeQuery = true)
    List<Cut> getAllCut();

    @Query(value = "select * from cut where eid = ?1 and cdid =?2",nativeQuery = true)
    Cut getByEidAndCdid(int eid,int cdid);

    @Query(value = "select sum(cut) from cut where eid = ?1 and cdid =?2",nativeQuery = true)
    double getCutByEidAndCdid(int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "insert into cut(ccid, cut, eid,cdid) values (?1,?2,?3,?4) ",nativeQuery = true)
    int addCut(int ccid,double cut,int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "update cut set ccid =?2, cut =?3,eid =?4,cdid =?5 where id = ?1 ",nativeQuery = true)
    int updateCut(int id,int ccid,double cut,int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "delete from cut where id = ?1 ",nativeQuery = true)
    int deleteCut(int id);
}
