package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Workday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WorkdayDao extends JpaRepository<Workday,Integer> {
    @Query(value = "select * from workday where eid = ?1 order by cdid desc",nativeQuery = true)
    List<Workday> getWorkdayByEid(int eid);

    @Query(value = "select * from workday order by cdid desc",nativeQuery = true)
    List<Workday> getAllWorkday();

    @Query(value = "select * from workday where id = ?1 order by cdid desc",nativeQuery = true)
    Workday getWorkdayById(int id);

    @Query(value = "select * from workday where eid = ?1 and cdid = ?2",nativeQuery = true)
    Workday getWorkdayByEidAndCdid(int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "insert into workday(eid,cdid,absence,overtimen,overtimed,status,absenteeism,late,leaveral) " +
            "values (?1,?2,?3,?4,?5,1,?6,?7,?8)",nativeQuery = true)
    int addWorkday(int eid, int cdid,int absence,double overtimen,double overtimed,double absenteeism,double late,double leaveral);

    @Transactional
    @Modifying
    @Query(value = "update workday set absence = ?2,overtimen = ?3, overtimed = ?4,absenteeism = ?5,late = ?6,leaveral = ?7," +
            "cdid = ?8 where id = ?1 ",nativeQuery = true)
    int updateWorkday(int id, int absence,double overtimen,double overtimed,double absenteeism,double late,double leaveral,int cdid);

    @Transactional
    @Modifying
    @Query(value = "delete from workday where id = ?1",nativeQuery = true)
    int deleteWorkday(int id);
}
