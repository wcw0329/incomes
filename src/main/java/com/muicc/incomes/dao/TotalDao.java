package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Total;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TotalDao extends JpaRepository<Total,Integer> {

    @Query(value = "select * from total order by cdid desc",nativeQuery = true)
    List<Total> getAllTotal();

    @Query(value = "select * from total where id = ?1",nativeQuery = true)
    Total getTotalById(int id);

    @Query(value = "select * from total where cdid = ?1",nativeQuery = true)
    Total getTotal(int cdid);

    @Transactional
    @Modifying
    @Query(value = "insert into total(totalsum,cdid) values (?1,?2) ",nativeQuery = true)
    int addTotal(double totalsum,int cdid);

    @Transactional
    @Modifying
    @Query(value = "update total set totalsum =?1 where cdid = ?2 ",nativeQuery = true)
    int updateTotal(double totalsum,int cdid);

    @Transactional
    @Modifying
    @Query(value = "delete from total where cdid = ?1 ",nativeQuery = true)
    int deleteTotal(int cdid);
}
