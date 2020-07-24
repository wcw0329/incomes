package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Awardcategory;
import com.muicc.incomes.pojo.Cutcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CutcategoryDao extends JpaRepository<Cutcategory,Integer> {

    @Query(value = "select * from cutcategory where status = 1 order by id asc",nativeQuery = true)
    List<Cutcategory> getAllCutcategory();

    @Query(value = "select * from cutcategory where status = 1 and shown =?1 order by id asc",nativeQuery = true)
    List<Cutcategory> getCutcategoryByShown(int shown);

    @Query(value = "select id from cutcategory where name =?1 ",nativeQuery = true)
    int getIdByName(String name);

    @Query(value = "select name from cutcategory where id =?1 ",nativeQuery = true)
    String getNameById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into cutcategory(name,shown,status) values (?1,?2,1) ",nativeQuery = true)
    int addCutcategory(String name, int shown);

    @Transactional
    @Modifying
    @Query(value = "update cutcategory set name =?1 ,shown =?2 where id = ?3 ",nativeQuery = true)
    int updateCutcategory(String name, int shown,int id);

    @Transactional
    @Modifying
    @Query(value = "update cutcategory set status =0 where id = ?1",nativeQuery = true)
    int deleteCutcategory(int id);
}
