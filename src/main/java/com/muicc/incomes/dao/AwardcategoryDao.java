package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Allowancecategory;
import com.muicc.incomes.pojo.Awardcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface AwardcategoryDao extends JpaRepository<Awardcategory,Integer> {

    @Query(value = "select * from awardcategory where status = 1 order by id asc",nativeQuery = true)
    List<Awardcategory> getAllAwardcategory();

    @Query(value = "select * from awardcategory where status = 1 and shown =?1 order by id asc",nativeQuery = true)
    List<Awardcategory> getAwardcategoryByShown(int shown);

    @Query(value = "select id from awardcategory where name =?1 ",nativeQuery = true)
    int getIdByName(String name);

    @Query(value = "select name from awardcategory where id =?1 ",nativeQuery = true)
    String getNameById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into awardcategory(name,shown,status) values (?1,?2,1) ",nativeQuery = true)
    int addAwardcategory(String name, int shown);

    @Transactional
    @Modifying
    @Query(value = "update awardcategory set name =?1 ,shown =?2 where id = ?3 ",nativeQuery = true)
    int updateAwardcategory(String name, int shown,int id);

    @Transactional
    @Modifying
    @Query(value = "update awardcategory set status =0 where id = ?1",nativeQuery = true)
    int deleteAwardcategory(int id);

}
