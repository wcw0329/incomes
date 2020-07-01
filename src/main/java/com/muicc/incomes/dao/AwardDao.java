package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Allowance;
import com.muicc.incomes.pojo.Award;
import com.muicc.incomes.pojo.Taxrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AwardDao extends JpaRepository<Award,Integer> {

    @Query(value = "select * from award where awardsid = ?1 and acid =?2 order by id asc",nativeQuery = true)
    Award getAwardByAcidAndAwardsid(int awardsid, int acid);

    @Query(value = "select * from award where awardsid = ?1 order by id asc",nativeQuery = true)
    List<Award> getAwardByAwardsid(int awardsid);

    @Transactional
    @Modifying
    @Query(value = "insert into award(acid,award,awardsid) values (?1,?2,?3) ",nativeQuery = true)
    int addAward(int acid,double award,int awardsid);

    @Query(value = "select sum(award) from award where awardsid = ?1",nativeQuery = true)
    int getSumMoney(int awardsid);

    @Transactional
    @Modifying
    @Query(value = "delete from award where awardsid = ?1",nativeQuery = true)
    int deleteAward(int awardsid);
}
