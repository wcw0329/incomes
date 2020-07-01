package com.muicc.incomes.dao;


import com.muicc.incomes.pojo.Taxrate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaxrateDao extends JpaRepository<Taxrate,Integer> {

    @Query(value = "select * from taxrate where status = 1 order by min asc",nativeQuery = true)
    List<Taxrate> getAllTaxrate();

    @Query(value = "select min(id) from taxrate where status = 1",nativeQuery = true)
    int getFirstId();

    @Query(value = "select max(id) from taxrate where status = 1",nativeQuery = true)
    int getLastId();

    @Query(value = "select * from taxrate where id = ?1 ",nativeQuery = true)
    Taxrate getTaxrateById(int id);

    @Query(value = "select * from taxrate where updid =?1 and status = 1 order by min asc",nativeQuery = true)
    List<Taxrate> getTaxrateByUpdid(int updid);

    @Transactional
    @Modifying
    @Query(value = "insert into taxrate(updid,min,max,taxrate,threshold,status) values (?1,?2,?3,?4,?5,1) ",nativeQuery = true)
    int addTaxrate(int updid,double min,double max,double taxrate,int threshold);

    @Transactional
    @Modifying
    @Query(value = "update taxrate set updid =?1 ,min =?2,max =?3,,taxrate =?4,,threshold =?5 where id = ?6 ",nativeQuery = true)
    int updateTaxrate(int updid,double min,double max,double taxrate,int threshold,int id);

    @Transactional
    @Modifying
    @Query(value = "update taxrate set status =0 where status = 1",nativeQuery = true)
    int deleteTaxrate();

    @Transactional
    @Modifying
    @Query(value = "update taxrate set status =0 where id = ?1 and status = 1",nativeQuery = true)
    int deleteTaxrateById(int id);
}
