package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Allowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AllowanceDao extends JpaRepository<Allowance,Integer> {
    @Query(value = "select * from allowance where alcid = ?1 and eid =?2 order by id asc",nativeQuery = true)
    List<Allowance> getAllowanceByEidAndAlcid(int alcid,int eid);

    @Query(value = "select max(eid) from allowance ",nativeQuery = true)
    int getMaxId();

    @Query(value = "select sum(allowance) from allowance where eid =?1",nativeQuery = true)
    double getSumAllowance(int eid);

    @Transactional
    @Modifying
    @Query(value = "insert into allowance(alcid, allowance, eid) values (?1,?2,?3) ",nativeQuery = true)
    int addAllowance(int alcid,double allowance,int eid);

    @Transactional
    @Modifying
    @Query(value = "delete from allowance where eid = ?1 ",nativeQuery = true)
    int deleteAllowance(int eid);
}
