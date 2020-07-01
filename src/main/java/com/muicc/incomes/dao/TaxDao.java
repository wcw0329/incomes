package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaxDao extends JpaRepository<Tax,Integer> {

    @Query(value = "select * from tax where id = ?1",nativeQuery = true)
    Tax getTaxByid(int id);

    @Transactional
    @Modifying
    @Query(value = "insert into tax(tax,trupdid) values (?1,?2) ",nativeQuery = true)
    int addTax(double tax,int trupdid);

    @Query(value = "select max(id) from tax where tax=?1 and trupdid=?2",nativeQuery = true)
    Integer getTxid(double tax,int trupdid);

    @Transactional
    @Modifying
    @Query(value = "delete from tax where id = ?1",nativeQuery = true)
    int deleteTax(int txid);
}
