package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Sum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SumDao extends JpaRepository<Sum,Integer> {

    @Query(value = "select * from sum order by cdid desc",nativeQuery = true)
    List<Sum> getAllSum();

    @Query(value = "select * from sum where cdid =?1",nativeQuery = true)
    Sum getSumByCdid(int cdid);

    @Query(value = "select * from sum where id =?1",nativeQuery = true)
    Sum getSumById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into sum (sum1,cdid) values (?2,?1)",nativeQuery = true)
    int addSum(int cdid,double sum);

    @Transactional
    @Modifying
    @Query(value = "update sum set sum1 =?2 where cdid = ?1 ",nativeQuery = true)
    int updateSum(int cdid,double sum);

    @Transactional
    @Modifying
    @Query(value = "delete from sum where id = ?1 ",nativeQuery = true)
    int deleteSum(int id);

    @Transactional
    @Modifying
    @Query(value = "delete from sum where cdid = ?1 ",nativeQuery = true)
    int deleteSumByCdid(int cdid);

}
