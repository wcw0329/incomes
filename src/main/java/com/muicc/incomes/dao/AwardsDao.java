//package com.muicc.incomes.dao;
//
//
//import com.muicc.incomes.pojo.Allowance;
//import com.muicc.incomes.pojo.Awards;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.List;
//
//public interface AwardsDao extends JpaRepository<Awards,Integer> {
//
//    @Query(value = "select * from awards order by eid asc",nativeQuery = true)
//    List<Awards> getAllAwards();
//
//    @Query(value = "select * from awards where eid = ?1",nativeQuery = true)
//    List<Awards> getAwardsByEid(int eid);
//
//    @Transactional
//    @Modifying
//    @Query(value = "insert into awards(eid,time,money,updatedate) values (?1,?2,?3,?4) ",nativeQuery = true)
//    int addAwards(int eid, double time, double money, Date updatedate);
//
//
//    @Query(value = "select max(id) from awards",nativeQuery = true)
//    Integer getLastId();
//
//    @Transactional
//    @Modifying
//    @Query(value = "update awards set time =?2 ,money = ?3 , updatedate =?4 where eid = ?1 ",nativeQuery = true)
//    int updateAwards(int eid, double time, double money, Date updatedate);
//
//    @Transactional
//    @Modifying
//    @Query(value = "delete from awards where id = ?1",nativeQuery = true)
//    int deleteAwards(int id);
//
//}
