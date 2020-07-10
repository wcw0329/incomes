package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AttendanceDao extends JpaRepository<Attendance,Integer> {

    @Query(value = "select * from attendance where status =1",nativeQuery = true)
    List<Attendance> getAllAttendance();

    @Transactional
    @Modifying
    @Query(value = "update attendance set status =?1 ",nativeQuery = true)
    int updateAttendanceStatus(int status);

    @Transactional
    @Modifying
    @Query(value = "insert into attendance(name,time,times,rate, fixed_award,status) values (?1,?2,?3,?4,?5,1) ",nativeQuery = true)
    int addAttendance(String name,double time,int times,double rate,double fixed_award);

    @Transactional
    @Modifying
    @Query(value = "update attendance set time = ?2,times = ?3,rate = ?4,fixed_award = ?5 where id = ?1 ",nativeQuery = true)
    int updateAttendance(int id,double time,int times,double rate,double fixed_award);

    @Transactional
    @Modifying
    @Query(value = "delete from attendance  where id = ?1 ",nativeQuery = true)
    int deleteAttendance(int id);
}
