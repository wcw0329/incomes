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

    @Query(value = "select * from userobject where eid = ?1",nativeQuery = true)
    List<Userobject> getUserobjectByEid(int eid);

    @Query(value = "select id from userobject where eid = ?1 and cdid = ?2",nativeQuery = true)
    int getIdByEidAndCdid(int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "insert into userobject(cdid, eid, regularpay, work, holiday, allowanceids, awards, absence, initialsum, insurance, secondsum, cut, tax, sumpayable, qianzhang, note) " +
            "values (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16) ",nativeQuery = true)
    int addUserobject(int cdid,int eid,double regularpay,double work,double holiday,double allowanceids,double awards,
                      double absence,double initialsum,double insurance,double secondsum,double cut,double tax,
                      double sumpayable,String qianzhang,String note);

    @Transactional
    @Modifying
    @Query(value = "delete from userobject where cdid = ?1",nativeQuery = true)
    int deleteUserobject(int cdid);
}
