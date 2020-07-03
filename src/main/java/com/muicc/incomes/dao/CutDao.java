package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Cut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CutDao extends JpaRepository<Cut,Integer> {

    @Query(value = "select * from cut where ccid = ?1 and eid =?2 order by id asc",nativeQuery = true)
    List<Cut> getCutByEidAndCcid(int ccid, int eid);

    @Query(value = "select max(eid) from allowance ",nativeQuery = true)
    int getMaxId();

    @Query(value = "select sum(cut) from cut where eid =?1",nativeQuery = true)
    double getSumCut(int eid);

    @Transactional
    @Modifying
    @Query(value = "insert into cut(ccid, cut, eid) values (?1,?2,?3) ",nativeQuery = true)
    int addCut(int ccid,double cut,int eid);

    @Transactional
    @Modifying
    @Query(value = "delete from cut where eid = ?1 ",nativeQuery = true)
    int deleteCut(int eid);
}
