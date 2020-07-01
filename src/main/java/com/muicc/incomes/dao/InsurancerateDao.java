package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Insurancerate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface InsurancerateDao extends JpaRepository<Insurancerate,Integer> {

    @Query(value = "select * from insurancerate where status = 1 order by id asc",nativeQuery = true)
    List<Insurancerate> getAllInsurancerate();

    @Query(value = "select * from insurancerate where status = 1 and eid= ?1",nativeQuery = true)
    Insurancerate getInsurancerateByEid(int eid);

    @Query(value = "select * from insurancerate where status = 1 and id= ?1",nativeQuery = true)
    Insurancerate getInsurancerateById(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into insurancerate(eid,insurancerate,fixedfee,updatedate,status) values (?1,?2,?3,?4,1) ",nativeQuery = true)
    int addInsurancerate(int eid, double insurancerate, double fixedfee, Date updatedate);

    @Transactional
    @Modifying
    @Query(value = "insert into insurancerate(eid,insurancerate,fixedfee,updatedate,status) values (?1,?2,?3,NOW(),1) ",nativeQuery = true)
    int addInsurancerate1(int eid, double insurancerate, double fixedfee);

    @Transactional
    @Modifying
    @Query(value = "update insurancerate set insurancerate = ?2 , fixedfee =?3 ,updatedate =?4 where eid = ?1 and status = 1",nativeQuery = true)
    int updateInsurancerate(int eid,double insurancerate, double fixedfee, Date updatedate);

    @Transactional
    @Modifying
    @Query(value = "update insurancerate set status = 0 where id = ?1 and status = 1",nativeQuery = true)
    int deleteInsurancerate(int id);
}
