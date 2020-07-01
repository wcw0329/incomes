package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Updatedate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

public interface UpdatedateDao extends JpaRepository<Updatedate,Integer> {

    @Query(value = "select * from updatedate where id = ?1 ",nativeQuery = true)
    Updatedate getUpdatedateById(int id);

    @Query(value = "select * from updatedate where updatedate = ?1 ",nativeQuery = true)
    Updatedate getUpdatedateByDate(Date updatedate);

    @Query(value = "select max(id) from updatedate",nativeQuery = true)
    Integer getLastId();

    @Transactional
    @Modifying
    @Query(value = "insert into updatedate(updatedate) values (?1) ",nativeQuery = true)
    int addUpdatedate(Date updatedate);
}
