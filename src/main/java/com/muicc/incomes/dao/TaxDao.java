package com.muicc.incomes.dao;

import com.muicc.incomes.pojo.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface TaxDao extends JpaRepository<Tax,Integer> {

    @Transactional
    @Modifying
    @Query(value = "insert into tax(tax,eid,cdid) values (?1,?2,?3) ",nativeQuery = true)
    int addTax(double tax,int eid,int cdid);

    @Query(value = "select IFNULL(sum(tax),0) from tax where eid =?1 and cdid >= ?2 and cdid <= ?3 ",nativeQuery = true)
    int getTaxByCdid(int eid,int cdid1,int cdid2);

    @Transactional
    @Modifying
    @Query(value = "delete from tax where cdid = ?1",nativeQuery = true)
    int deleteTax(int cdid);
}
