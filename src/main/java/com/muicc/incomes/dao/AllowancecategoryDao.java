package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Allowancecategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AllowancecategoryDao extends JpaRepository<Allowancecategory,Integer> {

    @Query(value = "select * from allowancecategory where status = 1 order by id asc",nativeQuery = true)
    List<Allowancecategory> getAllAllowancecategory();

    @Query(value = "select * from allowancecategory where shown =?1 and status = 1 order by id asc",nativeQuery = true)
    List<Allowancecategory> getAllowancecategoryByShown(int shown);

    @Query(value = "select id from allowancecategory where name =?1",nativeQuery = true)
    int getIdByName(String name);

    @Query(value = "select name from allowancecategory where id =?1 ",nativeQuery = true)
    String getNameById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into allowancecategory(name,shown,status) values (?1,?2,1) ",nativeQuery = true)
    int addAllowancecategory(String name, int shown);

    @Transactional
    @Modifying
    @Query(value = "update allowancecategory set name =?1 ,shown =?2 where id = ?3 ",nativeQuery = true)
    int updateAllowancecategory(String name, int shown,int id);

    @Transactional
    @Modifying
    @Query(value = "update allowancecategory set status =0 where id = ?1",nativeQuery = true)
    int deleteAllowancecategory(int id);

}
