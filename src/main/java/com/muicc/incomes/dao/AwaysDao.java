package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Aways;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AwaysDao extends JpaRepository<Aways,Integer> {

    @Query(value = "select * from aways order by id asc",nativeQuery = true)
    List<Aways> getAllAways();


    @Query(value = "select * from aways where workdayid = ?1",nativeQuery = true)
    Aways getAwaysByWorkdayId(int workdayid);

    @Transactional
    @Modifying
    @Query(value = "insert into aways(shown,cutmoney,workdayid,stayid,awardsid) values (?1,?2,?3,?4,?5) ",nativeQuery = true)
    int addAways(int shown,int cutmoney,int workdayid,int stayid,int awardsid);
}
