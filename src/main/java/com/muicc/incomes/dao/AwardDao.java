package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Award;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AwardDao extends JpaRepository<Award,Integer> {

    @Query(value = "select * from award where eid = ?1 and acid =?2 and cdid =?3",nativeQuery = true)
    Award getAwardByEidAndAcidAndCdid(int eid, int acid,int cdid);

    @Query(value = "select * from award order by cdid desc,eid asc",nativeQuery = true)
    List<Award> getAllAward();

    @Query(value = "select * from award where cdid =?1",nativeQuery = true)
    List<Award> getAwardByCdid(int cdid);

    @Query(value = "select * from award where eid = ?1 and cdid =?2",nativeQuery = true)
    List<Award> getByEidAndCdid(int eid,int cdid);

    @Query(value = "select sum(award) from award where eid = ?1 and cdid =?2",nativeQuery = true)
    double getAwardByEidAndCdid(int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "insert into award(acid,award,eid,cdid,status) values (?1,?2,?3,?4,1) ",nativeQuery = true)
    int addAward(int acid,double award,int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "update award set acid =?2, award =?3,eid =?4,cdid =?5 where id = ?1 ",nativeQuery = true)
    int updateAward(int id,int acid,double award,int eid,int cdid);

    @Transactional
    @Modifying
    @Query(value = "update award set status = ?1 where cdid = ?2 ",nativeQuery = true)
    int updateAwardStatus(int status,int cdid);

    @Transactional
    @Modifying
    @Query(value = "delete from award where id = ?1",nativeQuery = true)
    int deleteAward(int id);
}
