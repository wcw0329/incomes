package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface StayDao extends JpaRepository<Stay,Integer> {

    @Query(value = "select * from stay order by updatedate desc",nativeQuery = true)
    List<Stay> getAllStay();

    @Query(value = "select max(id) from stay",nativeQuery = true)
    Integer getLastId();

    @Transactional
    @Modifying
    @Query(value = "insert into stay(bei,time,updatedate) values (?1,?2,?3) ",nativeQuery = true)
    int addStay(double bei,double time,Date updatedate);

    @Transactional
    @Modifying
    @Query(value = "update stay set bei =?1 ,time =?2 ,updatedate =?3 where id = ?4 ",nativeQuery = true)
    int updateStay(double bei,double time,Date updatedate,int id);

    @Transactional
    @Modifying
    @Query(value = "delete from stay where id = ?1 ",nativeQuery = true)
    int deleteStay(int id);

}
