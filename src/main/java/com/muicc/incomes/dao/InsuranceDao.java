package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InsuranceDao extends JpaRepository<Insurance,Integer> {

    @Query(value = "select * from insurance where id = ?1",nativeQuery = true)
    Insurance getInsuranceById(int id);

    @Query(value = "select * from insurance where irid = ?1",nativeQuery = true)
    List<Insurance> getInsuranceByIrid(int irid);

    @Transactional
    @Modifying
    @Query(value = "insert into insurance(insurance,irid) values (?1,?2) ",nativeQuery = true)
    int addInsurance(double insurance,int irid);

    @Transactional
    @Modifying
    @Query(value = "update insurance set insurance = ?1 where irid = ?2",nativeQuery = true)
    int updateInsurance(double insurance,int irid);

    @Transactional
    @Modifying
    @Query(value = "delete  from insurance  where irid = ?1 ",nativeQuery = true)
    int deleteInsurance(int irid);
}
