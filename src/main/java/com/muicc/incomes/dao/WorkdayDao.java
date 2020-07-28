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

    @Query(value = "select * from workday order by cdid desc, eid asc",nativeQuery = true)
    List<Workday> getAllWorkday();

    @Query(value = "select * from workday where cdid = ?1",nativeQuery = true)
    List<Workday> getWorkdayByCdid(int cdid);

    @Query(value = "select * from workday where id = ?1 order by cdid desc",nativeQuery = true)
    Workday getWorkdayById(int id);

    @Query(value = "select * from workday where eid = ?1 and cdid = ?2",nativeQuery = true)
    Workday getWorkdayByEidAndCdid(int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "insert into workday(eid,cdid,paid_leave,absence,overtimen,overtimed,status,absenteeism,late,late_times,leaveral,leaveral_times) " +
            "values (?1,?2,?3,?4,?5,?6,1,?7,?8,?9,?10,?11)",nativeQuery = true)
    int addWorkday(int eid, int cdid,double paidLeave,double absence,double overtimen,double overtimed,double absenteeism,double late,int lateTimes,double leaveral,int leaveralTimes);

    @Transactional
    @Modifying
    @Query(value = "update workday set paid_leave = ?2,absence = ?3,overtimen = ?4, overtimed = ?5,absenteeism = ?6,late = ?7,late_times = ?8,leaveral = ?9,leaveral_times = ?10," +
            "cdid = ?11 where id = ?1 ",nativeQuery = true)
    int updateWorkday(int id,double paidLeave, double absence,double overtimen,double overtimed,double absenteeism,double late,int lateTimes,double leaveral,int leaveralTimes,int cdid);

    @Transactional
    @Modifying
    @Query(value = "update workday set status = 0 where cdid = ?1 and eid=?2",nativeQuery = true)
    int updateWorkdayStatusTo0(int cdid,int eid);

    @Transactional
    @Modifying
    @Query(value = "update workday set status = 1 where cdid = ?1",nativeQuery = true)
    int updateWorkdayStatusTo1(int cdid);

    @Transactional
    @Modifying
    @Query(value = "delete from workday where id = ?1",nativeQuery = true)
    int deleteWorkday(int id);
}
