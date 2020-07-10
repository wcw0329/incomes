package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Createdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CreatedateDao  extends JpaRepository<Createdate,Integer> {
    @Query(value = "select * from createdate where id = ?1 ",nativeQuery = true)
    Createdate getCreatedateByCdid(int id);

    @Query(value = "select * from createdate where name = ?1 ",nativeQuery = true)
    Createdate getCreatedateByTime(String time);

    @Query(value = "select * from createdate order by id desc",nativeQuery = true)
    List<Createdate> getAllCreatedate();

    @Query(value = "select * from createdate where id = ?1 and status = ?2",nativeQuery = true)
    Createdate getCreatedateByIdAndStatus(int id,int status);

    @Transactional
    @Modifying
    @Query(value = "update createdate set status =?1 where name = ?2 ",nativeQuery = true)
    int updateCreatedate(int status,String time);
}
