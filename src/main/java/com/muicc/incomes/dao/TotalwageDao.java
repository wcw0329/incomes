package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Totalwage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TotalwageDao extends JpaRepository<Totalwage,Integer> {

    @Query(value = "select * from totalwage where id = ?1",nativeQuery = true)
    Totalwage getTotalwageByid(int id);

    @Query(value = "select max(id) from totalwage where initialsum=?1 and secondsum=?2 and sumpayable=?3",nativeQuery = true)
    Integer getTwid(double initialsum, double secondsum,double sumpayable);

    @Transactional
    @Modifying
    @Query(value = "insert into totalwage(initialsum,secondsum,sumpayable) values (?1,?2,?3) ",nativeQuery = true)
    int addTotalwage(double initialsum, double secondsum,double sumpayable);

    @Transactional
    @Modifying
    @Query(value = "delete from totalwage where id = ?1",nativeQuery = true)
    int deleteTotalwage(int twid);
}
