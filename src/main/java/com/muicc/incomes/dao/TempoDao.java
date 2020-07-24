package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Tempo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface TempoDao extends JpaRepository<Tempo,Integer> {

    @Query(value = "select * from tempo where cdid = ?1",nativeQuery = true)
    List<Tempo> getTempoByCdid(int cdid);

    @Query(value = "select sum(sumpayable) from tempo where cdid = ?1",nativeQuery = true)
    double getSumByCdid(int cdid);

    @Query(value = "select * from tempo where cdid = ?1 and name =?2",nativeQuery = true)
    Tempo getTempoByCdidAndName(int cdid,String name);

    @Transactional
    @Modifying
    @Query(value = "insert into tempo(cdid,name,secondsum,tax,sumpayable,note) values (?1,?2,?3,?4,?5,?6) ",nativeQuery = true)
    int addTempo(int cdid,String name,double secondsum, double tax,double sumpayable,String note);

    @Transactional
    @Modifying
    @Query(value = "delete from tempo where cdid = ?1 ",nativeQuery = true)
    int deleteTempo(int cdid);
}
